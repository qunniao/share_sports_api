package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.*;
import com.gymnasium.core.page.Paging;
import com.gymnasium.order.DAO.CouponOperationDao;
import com.gymnasium.order.PO.CouponOperationPO;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.stadium.Dao.GymImagesDao;
import com.gymnasium.store.Dao.*;
import com.gymnasium.store.PO.*;
import com.gymnasium.store.VO.ProductInfoVO;
import com.gymnasium.store.VO.ShopOrderVO;
import com.gymnasium.store.dto.ShopOrderQuery;
import com.gymnasium.store.service.ShopOrderService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 19:47
 * @Description:
 */
@Service
public class ShopOrderServiceImpl implements ShopOrderService {

    @Autowired
    private GymImagesDao gymImagesDao;

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderProductDao orderProductDao;

    @Autowired
    private AgentSchemeDao agentSchemeDao;

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private FlowRecordUtil flowRecordUtil;

    @Autowired
    private CommissionUtil commissionUtil;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CouponOperationDao couponOperationDao;

    @Autowired
    private JgUtil jgUtil;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String insertShopOrder(ShopOrderVO shopOrderVO) {

        return addOrder(shopOrderVO);
    }

    @Override
    public Boolean updateShopOrder(ShopOrderVO shopOrderVO) {

        ShopOrderPO shopOrderPO = shopOrderDao.findShopOrderPOById(shopOrderVO.getId());

        if (shopOrderPO == null) {
            throw new SCException(453, "订单不存在");
        }

        BeanUtil.copyProperties(shopOrderVO, shopOrderPO);

        shopOrderDao.save(shopOrderPO);

        return true;
    }

    @Override
    public Boolean shipments(ShopOrderVO shopOrderVO) {

        ShopOrderPO shopOrderPO = shopOrderDao.findShopOrderPOById(shopOrderVO.getId());

        if (shopOrderPO == null) {
            throw new SCException(453, "订单不存在");
        }

        if (shopOrderPO.getOrderState() >= 3) {
            throw new SCException(4517, "订单已经发货，请勿重复提交");
        }
        // 更新时间修改
        shopOrderPO.setLastTime(new Date());

        //发货时间修改
        shopOrderPO.setDeliveryTime(new Date());

        shopOrderPO.setCourierNumber(shopOrderVO.getCourierNumber());

        shopOrderPO.setDeliveryWay(shopOrderVO.getDeliveryWay());

        // 修改状态值为 3 （待收货）
        shopOrderPO.setOrderState(SysConstant.ORDER_STATUS_RECEIVING.getConstant());

        shopOrderDao.save(shopOrderPO);

        jgUtil.sendSingleTextByAdmin(shopOrderPO, "3");

        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean receiving(Long orderId, Integer userId) {

        ShopOrderPO shopOrderPO = shopOrderDao.findShopOrderPOById(orderId);

        if (shopOrderPO == null) {
            throw new SCException(453, "订单不存在");
        }

        // 修改状态值为4 （已完成）
        shopOrderPO.setOrderState(SysConstant.ORDER_STATUS_DONE.getConstant());

        shopOrderPO.setLastTime(new Date());

        shopOrderPO.setReceivingTime(new Date());

        ShopOrderPO save = shopOrderDao.save(shopOrderPO);

        if (save == null) {
            throw new SCException(446, "收货失败,请稍后再试");
        }

        UserPO userPO = userDao.findByUid(userId);

        if (userPO == null) {
            throw new SCException(447, "收货失败,用户不存在");
        }

        if (shopOrderPO.getSettlementType() == 2) {

            //添加购买商品能量值流水
            flowRecordUtil.addExpensesRecord(userPO.getUserId(), shopOrderPO.getPayPrice().doubleValue(), userPO.getEnergy(),
                    0, 1, 6, "购买商品", shopOrderPO.getRemark(), null);

            return true;
        }

        // 分配佣金
        Map<String, Object> map = commissionUtil.allotCommission(userId, orderId);

        Integer superior = (Integer) map.get("superior");
        Integer secondLevel = (Integer) map.get("secondLevel");
        Integer role = (Integer) map.get("role");
        BigDecimal oneCommission = (BigDecimal) map.get("oneCommission");
        BigDecimal towCommission = (BigDecimal) map.get("towCommission");

        //订单号
        String orderNumber = save.getOrderNumber();
        String title = "收货";
        String content = "订单收货后的佣金详情";
        BigDecimal money = save.getPayPrice();

        // 添加佣金流水
        flowRecordUtil.addCashFlow(userId, orderNumber, title, content, money, 1, role, orderId, superior, secondLevel, 1,
                oneCommission, towCommission);

        // 添加资金流水
        flowRecordUtil.addFundFlow(userId, 2, 1, orderId, title, content, money);

        String titles = "客户佣金";
        String contents = "客户佣金分配";

        if (!oneCommission.equals(0)) {

            flowRecordUtil.addFundFlow(userId, 1, 2, orderId, titles, contents, money);
        }
        if (!towCommission.equals(0)) {

            flowRecordUtil.addFundFlow(userId, 1, 2, orderId, titles, contents, money);
        }

        return true;
    }

    @Override
    public Page<ShopOrderPO> pageCustomerOrder(Integer role, Integer userId, Integer orderState, Paging page) {

        List idList = new ArrayList();
        List<CommissionPO> commissionList = new ArrayList<>();
        if (role == 1) {
            commissionList = commissionDao.findAllBySuperiorAndRole(userId, 0);
        } else if (role == 2) {
            commissionList = commissionDao.findBySecondLevel(userId);
        } else if (role == 0) {
            commissionList = commissionDao.findAllBySuperiorAndRole(userId, 0);
            List<CommissionPO> twoList = commissionDao.findBySecondLevel(userId);
            for (CommissionPO commissionPO : twoList) {
                commissionList.add(commissionPO);
            }
        }

        for (CommissionPO commissionPO : commissionList) {

            idList.add(commissionPO.getUserId());

        }

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<ShopOrderPO> shopOrderPO = shopOrderDao.findAll((Specification<ShopOrderPO>)
                (root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> list = new ArrayList<>();

                    if (ObjectUtil.isNotNull(orderState)) {
                        list.add(criteriaBuilder.equal(root.get("orderState").as(Integer.class), orderState));
                    }

                    if (idList.size() != 0) {

                        Expression<String> exp = root.get("userId");

                        list.add(exp.in(idList));
                    }

                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }, pageable);

        return shopOrderPO;
    }

    @Override
    public Page<ShopOrderPO> queryShopOrder(Paging page, ShopOrderVO shopOrderVO, Integer year, Integer month) {
        System.out.println("shopOrderVO" + shopOrderVO);
        System.out.println(year);
        System.out.println(shopOrderVO);

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.DESC, "createTime");

        Page<ShopOrderPO> shopOrderPO = shopOrderDao.findAll((Specification<ShopOrderPO>) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            if (GeneralUtils.notEmpty(shopOrderVO.getUserId())) {
                list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), shopOrderVO.getUserId()));
            }

            if (GeneralUtils.notEmpty(shopOrderVO.getOrderState())) {
                list.add(criteriaBuilder.equal(root.get("orderState").as(Integer.class), shopOrderVO.getOrderState()));
            }

            if (GeneralUtils.notEmpty(shopOrderVO.getSettlementType())) {
                list.add(criteriaBuilder.equal(root.get("settlementType").as(Integer.class), shopOrderVO.getSettlementType()));
            }

            if (GeneralUtils.notEmpty(shopOrderVO.getOrderNumber())) {
                list.add(criteriaBuilder.like(root.get("orderNumber"), "%" + shopOrderVO.getOrderNumber() + "%"));
            }

            if (ObjectUtil.isNotEmpty(year) && ObjectUtil.isEmpty(month)) {

                list.add(criteriaBuilder.like(root.get("createTime").as(String.class), year + "%"));
            }

            if (ObjectUtil.isNotEmpty(year) && ObjectUtil.isNotEmpty(month)) {

                String str;

                if (month < 10) {
                    str = "-0";
                } else {
                    str = "-";
                }

                System.out.println(str);
                list.add(criteriaBuilder.like(root.get("createTime").as(String.class), year + str + month + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);

        return shopOrderPO;
    }

    @Override
    public Page<ShopOrderPO> queryOrder(ShopOrderQuery shopOrderQuery) {

        String keyword = shopOrderQuery.getKeyword();

        Pageable pageable = PageRequest.of(shopOrderQuery.getPageNum() - 1, shopOrderQuery.getPageSize(), Sort.Direction.DESC, "createTime");

        Page<ShopOrderPO> shopOrderPO = shopOrderDao.findAll((Specification<ShopOrderPO>) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            if (GeneralUtils.notEmpty(shopOrderQuery.getOrderState())) {
                list.add(criteriaBuilder.equal(root.get("orderState").as(Integer.class), shopOrderQuery.getOrderState()));
            }

            if (GeneralUtils.notEmpty(shopOrderQuery.getSettlementType())) {
                list.add(criteriaBuilder.equal(root.get("settlementType").as(Integer.class), shopOrderQuery.getSettlementType()));
            }

            if (GeneralUtils.notEmpty(shopOrderQuery.getOrderNumber())) {
                list.add(criteriaBuilder.like(root.get("orderNumber"), shopOrderQuery.getOrderNumber() + "%"));
            }

            if (GeneralUtils.notEmpty(shopOrderQuery.getCreateTime())) {
                list.add(criteriaBuilder.like(root.get("createTime"), shopOrderQuery.getCreateTime() + "%"));
            }

            if (GeneralUtils.notEmpty(shopOrderQuery.getDeliveryTime())) {
                list.add(criteriaBuilder.like(root.get("deliveryTime"), shopOrderQuery.getDeliveryTime() + "%"));
            }


            if (StrUtil.isNotBlank(keyword)) {

                list.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("contactName").as(String.class), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("phone").as(String.class), "%" + keyword + "%")
                ));
            }

            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);

        return shopOrderPO;
    }

    @Override
    public BigDecimal calculateTotalCommission(Long orderId) {

        List<OrderProductPO> orderProductList = orderProductDao.findByOrderId(orderId);

        if (ObjectUtil.isEmpty(orderProductList)) {
            return null;
        }

        BigDecimal totalCommission = new BigDecimal("0");

        for (OrderProductPO orderProductPO : orderProductList) {

            AgentSchemePO agentSchemePO = agentSchemeDao.findByProductId(orderProductPO.getProductId());

            BigDecimal agentCommission = agentSchemePO.getAgentCommission();

            BigDecimal number = new BigDecimal(orderProductPO.getNumber());

            BigDecimal oneCommission = agentCommission.multiply(number);

            totalCommission = totalCommission.add(oneCommission);
        }

        return totalCommission;
    }

    @Override
    public String submitCart(ShopOrderVO shopOrderVO, String str) {

        String[] split = StringUtils.split(str, ",");

        long[] cartIds = (long[]) ConvertUtils.convert(split, long.class);

        List<CartPO> cartList = cartDao.findAllByIdIn(cartIds);

        List<ProductInfoVO> productInfoVOList = new ArrayList<>();

        if (CollectionUtils.isEmpty(cartList)) {
            throw new SCException(10010, "商品不存在");
        }

        for (CartPO cartPO : cartList) {

            ProductInfoVO productInfoVO = new ProductInfoVO();

            productInfoVO.setProductId(cartPO.getProductId());
            productInfoVO.setNumber(cartPO.getNumber());
            productInfoVO.setStyle(cartPO.getStyle());

            productInfoVOList.add(productInfoVO);
        }

        System.out.println(cartList);

        shopOrderVO.setProductInfo(productInfoVOList);

        String orderNumber = addOrder(shopOrderVO);

        cartDao.deleteCartPO(cartIds);

        return orderNumber;
    }

    @Override
    public ShopOrderPO queryShopOrderDetails(Long id) {

        ShopOrderPO shopOrderPO = shopOrderDao.findShopOrderPOById(id);

        return shopOrderPO;
    }

    @Override
    public Boolean cancelShopOrder(Long id) {

        ShopOrderPO shopOrderPO = shopOrderDao.findShopOrderPOById(id);

        if (ObjectUtils.anyNotNull(shopOrderPO)) {

            // 修改订单状态为0 取消订单
            shopOrderPO.setOrderState(SysConstant.ORDER_STATUS_CANCEL.getConstant());
            shopOrderDao.save(shopOrderPO);

            return true;
        }

        return false;
    }

    @Override
    public String queryExpress(Long id) {

//        ShopOrderPO shopOrder = shopOrderDao.getOne(id);
//
//        // 快递公司标识
//        String com = shopOrder.getCourierCode();
//
//        // 快递单号
//        String num = shopOrder.getCourierNumber();
//        //com 快递公司标识，num:快递单号
//        String param = "{\"com\":\"" + com + "\",\"num\":\"" + num + "\"}";
//
//        //这是申请下来的公钥
//        String customer = FinalEnum.EXPRESS_CUSTOMER.getContent();
//        //这是申请下来的私钥
//        String key = FinalEnum.EXPRESS_KEY.getContent();
//
//        //md5加密
//        String sign = SecureUtil.md5(param + key + customer).toUpperCase();
//
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("param", param);
//        params.put("sign", sign);
//        params.put("customer", customer);
//        String resp = "";
//        try {
//            resp = HttpUtil.post("http://poll.kuaidi100.com/poll/query.do", params);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return resp;
        return null;
    }

    private String addOrder(ShopOrderVO shopOrderVO) {

        System.err.println(shopOrderVO);

        // 总金额
        BigDecimal totalPrice = new BigDecimal("0");

        ShopOrderPO shopOrderPO = new ShopOrderPO();

        ShopOrderPO shopOrderPO1 = new ShopOrderPO();

        int count = 0;

        if (CollectionUtils.isEmpty(shopOrderVO.getProductInfo())) {

            throw new SCException(100010, "产品信息不能为空");
        }

        for (ProductInfoVO productInfoVO : shopOrderVO.getProductInfo()) {

            OrderProductPO orderProductPO = new OrderProductPO();

            ProductPO product = productDao.findProductPOById(productInfoVO.getProductId());

            //商品单价
            BigDecimal price = product.getRealPrice();

            // 商品数量
            Integer number = productInfoVO.getNumber();

            // 商品总价等于 商品单价 * 数量 + 运费
            price = price.multiply(new BigDecimal(number));

            // 单个商品的运费和单价总和
            totalPrice = price.add(totalPrice);

            if (count == 0) {

                // 优惠价
                BigDecimal discountPrice;
                // 优惠价
                BigDecimal payPrice = totalPrice;
                //生成订单号
                String orderNumber = "SO" + RandomUtil.randomNumbers(8);

                if (ObjectUtils.anyNotNull(shopOrderVO.getCouponId())) {

                    // 根据优惠券id 查询优惠券是否可用
                    CouponOperationPO couponOperationPO = couponOperationDao.queryCouPon(shopOrderVO.getCouponId());

                    if (ObjectUtils.anyNotNull(couponOperationPO)) {

                        System.err.println(couponOperationPO);

                        discountPrice = couponOperationPO.getCouponPO().getPrice();

                        System.err.println(discountPrice);

                        payPrice = totalPrice.subtract(discountPrice);

                        // 使用 优惠券后将状态修改为0
                        couponOperationPO.setStatus(0);

                        couponOperationDao.save(couponOperationPO);
                    }
                }

                BeanUtils.copyProperties(shopOrderVO, shopOrderPO);

                // 初始化能量值
                BigDecimal freight = new BigDecimal("0");

                if (shopOrderVO.getSettlementType() == 1) {

                    // 判断运费是否大于100
                    if (totalPrice.compareTo(new BigDecimal("100")) == -1) {

                        // 设置总运费
                        freight = new BigDecimal("10");
                        totalPrice = totalPrice.add(freight);
                        payPrice = payPrice.add(freight);
                    }

                } else {
                    if (totalPrice.compareTo(new BigDecimal("20")) == -1) {
                        freight = new BigDecimal("2");
                        totalPrice = totalPrice.add(freight);
                        payPrice = payPrice.add(freight);
                    }
                }

                shopOrderPO.setFreight(freight);
                shopOrderPO.setTotalPrice(totalPrice);
                shopOrderPO.setPayPrice(payPrice);

                CommissionPO commissionPO = commissionDao.findByUserId(shopOrderVO.getUserId());

                // 上级id
                Integer superior = commissionPO.getSuperior();

                // 添加上级id
                shopOrderPO.setSuperior(superior);

                // 设置生成的订单号
                shopOrderPO.setOrderNumber(orderNumber);

                // 修改状态值为 1（待付款）
                shopOrderPO.setOrderState(SysConstant.ORDER_STATUS_PAY.getConstant());

                shopOrderPO.setCreateTime(new Date());
                shopOrderPO.setStatus(1);

                shopOrderPO1 = shopOrderDao.save(shopOrderPO);

                count++;
            }

            orderProductPO.setNumber(number);
            orderProductPO.setPrice(product.getRealPrice());
            orderProductPO.setProductId(product.getId());
            orderProductPO.setStyle(productInfoVO.getStyle());
            orderProductPO.setCreateTime(new Date());
            orderProductPO.setOrderId(shopOrderPO1.getId());

            orderProductDao.saveAndFlush(orderProductPO);

            // 订单添加成功后减少库存数量
            product.setInventory(product.getInventory() - number);
            productDao.save(product);
        }

        return shopOrderPO.getOrderNumber();
    }
}