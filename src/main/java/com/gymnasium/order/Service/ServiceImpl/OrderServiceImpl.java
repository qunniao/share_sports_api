package com.gymnasium.order.Service.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.PO.Validation;
import com.gymnasium.core.page.Paging;
import com.gymnasium.data.Service.CodelogService;
import com.gymnasium.order.DAO.ActivationCodeDao;
import com.gymnasium.order.DAO.ExpensesRecordDao;
import com.gymnasium.order.DAO.OrderDao;
import com.gymnasium.order.PO.ActivationCodePO;
import com.gymnasium.order.PO.ExpensesRecordPO;
import com.gymnasium.order.PO.OrderPO;
import com.gymnasium.order.Service.OrderService;
import com.gymnasium.order.VO.EnergyVO;
import com.gymnasium.order.VO.OrderVO;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.stadium.Dao.CardTypeDao;
import com.gymnasium.stadium.PO.CardTypePO;
import com.gymnasium.store.Dao.AgentSchemeDao;
import com.gymnasium.store.Dao.ChannelSchemeDao;
import com.gymnasium.store.Dao.CommissionDao;
import com.gymnasium.store.PO.AgentSchemePO;
import com.gymnasium.store.PO.ChannelSchemePO;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.system.Dao.SysMemberDao;
import com.gymnasium.system.PO.SysMemberPO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: OrderServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 15:28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CodelogService codelogService;

    @Autowired
    private SysMemberDao sysMemberDao;

    @Autowired
    private ExpensesRecordDao expensesRecordDao;

    @Autowired
    private ActivationCodeDao activationCodeDao;

    @Autowired
    private CardTypeDao cardTypeDao;

    @Autowired
    private AgentSchemeDao agentSchemeDao;

    @Autowired
    private ChannelSchemeDao channelSchemeDao;

    @Autowired
    private FlowRecordUtil flowRecordUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CommissionDao commissionDao;


    @Override
    public List<CardTypePO> queryCardTypeByGymShopId(String gymShopId) throws SCException {

        List<CardTypePO> cardTypePOS = cardTypeDao.queryByGymShopIdAndStatus(gymShopId, SysConstant.STATUS_Enable_INT.getConstant());

        return cardTypePOS;
    }

    @Override
    public Page<ExpensesRecordPO> pageExpensesRecord(Paging page, ExpensesRecordPO expensesRecordPO,
                                                     Double minEnergy, Double maxEnergy) {
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.ASC, "id");

        Page<ExpensesRecordPO> gymShopPage = expensesRecordDao.findAll(new Specification<ExpensesRecordPO>() {
            @Override
            public Predicate toPredicate(Root<ExpensesRecordPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (ObjectUtil.isNotNull(expensesRecordPO.getTitleType()) && expensesRecordPO.getTitleType() != 0) {

                    list.add(criteriaBuilder.equal(root.get("titleType").as(Integer.class), expensesRecordPO.getTitleType()));
                }

                    list.add(criteriaBuilder.equal(root.get("userId").as(String.class), expensesRecordPO.getUserId()));

                if (ObjectUtil.isNotNull(expensesRecordPO.getItemType())) {
                    list.add(criteriaBuilder.equal(root.get("itemType").as(Integer.class), expensesRecordPO.getItemType()));
                }

                if (ObjectUtil.isNotNull(minEnergy)) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("energy").as(Double.class), minEnergy));
                }

                if (ObjectUtil.isNotNull(maxEnergy)) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("energy").as(Double.class), maxEnergy));
                }

                if(ObjectUtil.isNotNull(expensesRecordPO.getShopId())){
                    list.add(criteriaBuilder.equal(root.get("shopId").as(String.class), expensesRecordPO.getShopId()));
                }
                if(ObjectUtil.isNotNull(expensesRecordPO.getType())){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), expensesRecordPO.getType()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return gymShopPage;
    }

    @Override
    public EnergyVO queryUserEnergyItem(String userId, int type) {
        EnergyVO energyVO = new EnergyVO();

        List<ExpensesRecordPO> list = expensesRecordDao.queryByUserIdAndType(userId, type);
        double sumEnergy = 0;
        for (ExpensesRecordPO exp : list) {
            sumEnergy += exp.getEnergy();
        }
        energyVO.setSumEnergy(sumEnergy);
        UserPO userPO = userDao.findByUserId(userId);
        if (ObjectUtil.isNull(userPO)){
            throw new SCException(ResultEnum.USER_REPEAT);
        }
        energyVO.setUserEnergy(userPO.getEnergy());
        return energyVO;
    }

    @Override
    public Page<ActivationCodePO> pageEActivationCode(Paging page, ActivationCodePO activationCodePO,
                                                      String createDate, String handleDate) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.ASC, "id");

        Page<ActivationCodePO> actPageList = activationCodeDao.findAll(new Specification<ActivationCodePO>() {
            @Override
            public Predicate toPredicate(Root<ActivationCodePO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (ObjectUtils.anyNotNull(activationCodePO.getType()) && activationCodePO.getType() != 0){

                    list.add(criteriaBuilder.equal(root.get("type"), activationCodePO.getType()));
                }

                if (GeneralUtils.notEmpty(activationCodePO.getReservedName())) {
                    list.add(criteriaBuilder.equal(root.get("reservedName"), activationCodePO.getReservedName()));
                }

                if (GeneralUtils.notEmpty(activationCodePO.getReservedPhone())) {
                    list.add(criteriaBuilder.equal(root.get("reservedPhone"), activationCodePO.getReservedPhone()));
                }

                if (GeneralUtils.notEmpty(activationCodePO.getActivationCode())) {
                    list.add(criteriaBuilder.equal(root.get("activationCode"), activationCodePO.getActivationCode()));
                }

                if (GeneralUtils.notEmpty(activationCodePO.getGymShopId())) {
                    list.add(criteriaBuilder.equal(root.get("gymShopId"), activationCodePO.getGymShopId()));
                }

                if (GeneralUtils.notEmpty(activationCodePO.getUserId())) {
                    list.add(criteriaBuilder.equal(root.get("userId"), activationCodePO.getUserId()));
                }

                if (ObjectUtil.isNotNull(createDate)) {
                    list.add(criteriaBuilder.like(root.get("createTime").as(String.class), createDate + "%"));
                }

                if (GeneralUtils.notEmpty(handleDate)) {
                    list.add(criteriaBuilder.like(root.get("handleTime").as(String.class), handleDate + "%"));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return actPageList;
    }

    @Override
    public List<ActivationCodePO> searchActivationCode(Paging page, ActivationCodePO activationCodePO) {

        List<ActivationCodePO> activationCodeList = activationCodeDao.findAll(new Specification<ActivationCodePO>() {
            @Override
            public Predicate toPredicate(Root<ActivationCodePO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();


                if (GeneralUtils.notEmpty(activationCodePO.getType())) {
                    list.add(criteriaBuilder.equal(root.get("type"), activationCodePO.getType()));
                }
                if (GeneralUtils.notEmpty(activationCodePO.getCardAccount())) {
                    list.add(criteriaBuilder.like(root.get("cardAccount"),
                            "%" + activationCodePO.getCardAccount() + "%"));
                }
                if (GeneralUtils.notEmpty(activationCodePO.getReservedName())) {
                    list.add(criteriaBuilder.like(root.get("reservedName"),
                            "%" + activationCodePO.getReservedName() + "%"));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
        return activationCodeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createMember(OrderVO orderVO, String code) throws SCException {

        if(ObjectUtil.isEmpty(orderVO)){
            throw new SCException(ResultEnum.INCOMPLETE_PARAMETER);
        }

        UserPO userPO = userDao.findByUserId(orderVO.getUserId());

        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER);
        }

        if (!codelogService.validCode(orderVO.getPhone(), code)) {
            throw new SCException(ResultEnum.CODE_TIME_OUT);
        }

        String productItem = "注册前已绑定手机号";

        //用户电话为空时,验证后直接保存手机号  否则判断手机号是否和原绑定相同
        if (GeneralUtils.isNull(userPO.getUserPhone()) || "".equals(userPO.getUserPhone())) {

            // 验证手机号是否合格
            if (!RegexUtil.checkByRegular(Validation.MOBILE, orderVO.getPhone())) {
                throw new SCException(ResultEnum.PHONE_NUMBER_ERRROR);
            }

            userPO.setUserPhone(orderVO.getPhone());
            productItem = "购买会员同时,默认绑定手机号";

        }
//        else {
//            //手机号是否和绑定手机号相同
//            if (!(userPO.getUserPhone()).equals(orderVO.getPhone())) {
//                throw new SCException(ResultEnum.USER_MISMATCH_PRESENCE);
//            }
//        }

        //验证价格是否相等
        SysMemberPO sysMemberPO = sysMemberDao.queryByLevel(orderVO.getLevel());

        OrderPO orderPO = new OrderPO();

        BigDecimal price;

        // 调用封装的方法获取会员价格
        BigDecimal memberPrice = getMemberPrice(userPO.getUid(),orderVO.getLevel());

        // 判断是否是购买会员卡
        if (orderVO.getType() == 3 && orderVO.getNumber() != null){

            BigDecimal number = new BigDecimal(orderVO.getNumber());

            // 会员卡总价
            price = memberPrice.multiply(number);

            orderPO.setEnergy(new BigDecimal(sysMemberPO.getEnergy()).multiply(number));
        }else {
            price = sysMemberPO.getPrice();

            orderPO.setEnergy(new BigDecimal(sysMemberPO.getEnergy()));
        }

        if (price.compareTo(price) != 0) {
            throw new SCException(ResultEnum.PRICE_MISMATCH);
        }

        BigDecimal bigDecimal = new BigDecimal(sysMemberPO.getPrice().doubleValue());


        orderPO.setUserId(orderVO.getUserId());
        orderPO.setName(orderVO.getName());
        orderPO.setPhone(orderVO.getPhone());

        // TODO 需要需要对接支付功能
        orderPO.setNumber(orderVO.getNumber());
        orderPO.setPrice(bigDecimal);
        orderPO.setProductItem(productItem);
        orderPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        orderPO.setLevel(orderVO.getLevel());

        String orderNum = "V" +RandomUtil.getRandomStringByLength(6)+ SnowFlakeIdGenerator.nextId();

        orderPO.setOrderNumber(orderNum);
        orderPO.setMemberPrice(memberPrice);
        orderPO.setOrderState(1);
        orderPO.setType(orderVO.getType());
        orderPO.setAddress(orderVO.getAddress());
        orderDao.save(orderPO);

        String remarks;
        double energy;

        switch (orderVO.getLevel()) {

            case 1: {
                remarks = "体检会员";
                energy = 99;
            }
            break;
            case 2: {
                remarks = "大众会员";
                energy = 1980;
            }
            break;
            case 3: {
                remarks = "精英会员";
                energy = 4980;

            }
            break;
            case 4: {
                remarks = "皇家会员";
                energy = 9980;
            }
            break;

            default:
                remarks = "普通用户";
                energy = 0;
                break;
        }

        // 如果 用户不是 购买会员卡，就添加能量值
        if (orderVO.getType() > 3 && orderVO.getNumber() != null){

            // 用户的能量值
            Double energy1 = userPO.getEnergy();

            userPO.setEnergy(DecimalCalcUtil.add(energy, energy1));
        }

        userDao.save(userPO);

//        flowRecordUtil.addExpensesRecord(userPO.getUserId(), energy, userPO.getEnergy(),1,2,1,
//                "开通会员",remarks,null);

        return orderNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String energyRecharge(String money, String userId) throws SCException {

        UserPO userPO = userDao.findByUserId(userId);

        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER);
        }

        System.out.println(!RegexUtil.checkByRegular(Validation.INTEGER_NEGATIVE, money) == false);

        if ((Integer.parseInt(money) % 5) != 0 || Integer.parseInt(money) <= 0) {
            throw new SCException(ResultEnum.MAONY_ERROR);
        }

        if (ObjectUtil.isEmpty(userPO.getUserName()) || ObjectUtil.isEmpty(userPO.getUserPhone())){
            throw new SCException(411010, "请完善用户姓名或手机号");
        }

        Integer moneys = Integer.parseInt(money);

        Double energy = Double.valueOf(moneys / 5);

        int level = getLevel(moneys);

//        userPO.setEnergy(userPO.getEnergy() + energy);
//
//        if (userPO.getLevel() < level) {
//            userPO.setLevel(level);
//        }

//        userDao.save(userPO);

        System.out.println("userPO\t" + userPO);

        OrderPO orderPO = new OrderPO();
        orderPO.setUserId(userPO.getUserId());
        orderPO.setName(userPO.getUserName());
        orderPO.setPhone(userPO.getUserPhone());

        // TODO 需要需要对接支付功能

        // 添加充值能量值订单
        orderPO.setPrice(new BigDecimal(money));
        orderPO.setProductItem("充值能量值");
        orderPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        orderPO.setLevel(level);
        orderPO.setEnergy(new BigDecimal(energy));
        orderPO.setType(2);

        String orderNum = "V" + RandomUtil.getRandomStringByLength(6) + SnowFlakeIdGenerator.nextId();

        orderPO.setOrderNumber(orderNum);
        orderPO.setOrderState(1);

        orderDao.save(orderPO);

//        flowRecordUtil.addExpensesRecord(userPO.getUserId(),energy,userPO.getEnergy(),1,2,
//                2,"充值能量值","充值能量值",null);

        return orderNum;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateActivationCodeType(String activationCode, int type) throws SCException {

        ActivationCodePO activationCodePO = activationCodeDao.queryByActivationCode(activationCode);

        if (activationCodePO == null) {
            throw new SCException(ResultEnum.CARD_ORDER_NULL);
        }

        if (activationCodePO.getType() == SysConstant.TYPE_CARD_ZERO.getConstant()) {
            throw new SCException(ResultEnum.CARD_TYPE_IS_ZERO);
        }

        if (activationCodePO.getType() == SysConstant.TYPE_CARD_TWO.getConstant()) {
            throw new SCException(ResultEnum.CARD_TYPE_IS_ONE);
        }

        //如果是退卡,店家操作退回用户能量值
        if (type == SysConstant.TYPE_CARD_ZERO.getConstant()) {

            UserPO userPO = userDao.findByUserId(activationCodePO.getUserId());
            userPO.setEnergy(userPO.getEnergy() + activationCodePO.getEnergy());
            activationCodePO.setType(SysConstant.TYPE_CARD_ZERO.getConstant());
            // 添加退卡时间
            activationCodePO.setRefundTime(new Date());
        } else {

            if (type != SysConstant.TYPE_CARD_TWO.getConstant()) {
                throw new SCException(ResultEnum.CARD_TYPE_IS_ONE);
            }

            activationCodePO.setType(type);

        }
        activationCodeDao.save(activationCodePO);

        return true;
    }


    //TYPE_CARD_TWO
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateActivationCodeTypeNew(String activationCode, String dockingName, String dockingPhone) {

        ActivationCodePO activationCodePO = activationCodeDao.queryByActivationCode(activationCode);

        if (activationCodePO == null) {
            throw new SCException(ResultEnum.CARD_ORDER_NULL);
        }

        if (activationCodePO.getType() == SysConstant.TYPE_CARD_ZERO.getConstant()) {
            throw new SCException(ResultEnum.CARD_TYPE_IS_ZERO);
        }


        if (activationCodePO.getType() == SysConstant.TYPE_CARD_TWO.getConstant()) {
            throw new SCException(ResultEnum.CARD_TYPE_IS_ONE);
        }


        activationCodePO.setType(SysConstant.TYPE_CARD_TWO.getConstant());

        activationCodePO.setDockingName(dockingName);

        activationCodePO.setDockingPhone(dockingPhone);

        // 生成卡号
        String cardNumber = RandomUtil.getRandomStringByLength(1) + SnowFlakeIdGenerator.nextId();

        // 生成密码
        String cardPwd= RandomUtil.getShortUuid();

        activationCodePO.setCardAccount(cardNumber);
        activationCodePO.setCardPassword(cardPwd);

        activationCodePO.setHandleTime(new Timestamp(System.currentTimeMillis()));

        activationCodeDao.save(activationCodePO);

        return true;
    }

    @Override
    public boolean reviewCard(ActivationCodePO activationCodePO) {

        // 审核状态
        Integer reviewState = activationCodePO.getReviewState();

        ActivationCodePO activationCodePO1 = activationCodeDao.findByOrderNumber(activationCodePO.getOrderNumber());

        if (ObjectUtil.isNull(activationCodePO1)){
            throw new SCException(400784, "转卡订单不存在");
        }

        // 审核通过后type改为1，拒绝后还是0
        if (reviewState == 1){
            activationCodePO1.setType(SysConstant.TYPE_CARD_ONE.getConstant());
        }else {
            activationCodePO1.setType(SysConstant.TYPE_CARD.getConstant());
        }

        // 添加审核状态
        activationCodePO1.setReviewState(reviewState);

        // 添加审核时间
        activationCodePO1.setReviewTime(new Date());

        return ObjectUtil.isNotNull(activationCodeDao.saveAndFlush(activationCodePO1));
    }

    @Override
    public Result addOrderCardType(CardTypePO cardTypePO) {
        cardTypePO.setStatus(1);
        cardTypeDao.save(cardTypePO);
        return ResultUtil.success();
    }

    @Override
    @Transactional
    public Result deleteOrderCardType(int id) {
        cardTypeDao.updateStatusById(id);
        return ResultUtil.success();
    }

    @Override
    public Result updateOrderCardType(CardTypePO cardTypePO) {
        CardTypePO card = cardTypeDao.queryById(cardTypePO.getId());
        if (cardTypePO.getGymShopId() != null) {
            card.setGymShopId(cardTypePO.getGymShopId());
        }
        if (cardTypePO.getName() != null) {
            card.setName(cardTypePO.getName());
        }
//        if (cardTypePO.getEnergy()!=null)
        card.setEnergy(cardTypePO.getEnergy());
        if (cardTypePO.getUrl() != null) {
            card.setUrl(cardTypePO.getUrl());
        }
        if (cardTypePO.getMarketPrice() != null) {
            card.setMarketPrice(cardTypePO.getMarketPrice());
        }
        if (cardTypePO.getRemakes() != null) {
            card.setRemakes(cardTypePO.getRemakes());
        }
        if (cardTypePO.getServicePhone() != null) {
            card.setServicePhone(cardTypePO.getServicePhone());
        }
        if (cardTypePO.getWorkNumber() != null) {
            card.setWorkNumber(cardTypePO.getWorkNumber());
        }
        cardTypeDao.save(card);
        return ResultUtil.success();
    }

    @Override
    public Result findAllOrderCardByGymShop(String gymShopId) {
        List<CardTypePO> cardTypePOS = cardTypeDao.queryByGymShopId(gymShopId);
        return ResultUtil.success(cardTypePOS);
    }

    @Override
    public Page<OrderPO> queryUserOrder(Paging page, OrderVO orderVO, String year, String month) {
        System.out.println(page);
        System.out.println("orderVO" + orderVO);
        System.out.println("year" + year);
        System.out.println("month" + month);

        Pageable pageable = PageRequest.of(page.getPageNum()-1, page.getPageSize(), Sort.Direction.DESC, "createTime" );

        Page<OrderPO> orderPOList = orderDao.findAll(new Specification<OrderPO>() {

            @Override
            public Predicate toPredicate(Root<OrderPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (ObjectUtil.isNotEmpty(orderVO.getUserId())) {
                    list.add(criteriaBuilder.equal(root.get("userId").as(String.class), orderVO.getUserId()));
                }

                if (ObjectUtil.isNotEmpty(orderVO.getOrderState())) {
                    list.add(criteriaBuilder.equal(root.get("orderState").as(Integer.class), orderVO.getOrderState()));
                }

                if (ObjectUtil.isNotEmpty(year) && ObjectUtil.isNull(month)) {
                    list.add(criteriaBuilder.like(root.get("createTime").as(String.class), year + "%"));
                }

                if (ObjectUtil.isNotEmpty(year) && ObjectUtil.isNotEmpty(month)) {

                    String str;
                    if(Integer.valueOf(month) < 10){
                        str = "-0";
                    }else {
                        str = "-";
                    }

                    System.err.println(str);

                    list.add(criteriaBuilder.like(root.get("createTime").as(String.class), year + str + month + "%"));
                }

                list.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("type").as(Integer.class), 1),
                        criteriaBuilder.equal(root.get("type").as(Integer.class), 3)
                ));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);

        return orderPOList;
    }

    @Override
    public Boolean shipments(OrderVO orderVO) {

        OrderPO orderPO = orderDao.findOrderPOById(orderVO.getId());

        if (orderPO == null) {
            throw new SCException(453, "订单不存在");
        }

        // 更新时间修改
        orderPO.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        //发货时间修改
        orderPO.setDeliveryTime(new Date());

        orderPO.setCourierNumber(orderVO.getCourierNumber());

        orderPO.setDeliveryWay(orderVO.getDeliveryWay());

        // 修改状态值为 3 （待收货）
        orderPO.setOrderState(SysConstant.ORDER_STATUS_RECEIVING.getConstant());

        orderDao.save(orderPO);

        return true;
    }

    @Override
    public Boolean receiving(Integer id, String userId) {

        OrderPO orderPO = orderDao.findOrderPOById(id);

        if (orderPO == null) {
            throw new SCException(453, "订单不存在");
        }

        orderPO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        orderPO.setReceivingTime(new Timestamp(System.currentTimeMillis()));
        orderPO.setOrderState(4);

        OrderPO save = orderDao.save(orderPO);

        return ObjectUtil.isNotEmpty(save);
    }

    @Override
    public Page<OrderPO> pageMemberCardOrder(Paging page, OrderPO orderPO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1 , page.getPageSize());

        Page<OrderPO> orderPOPage = orderDao.findAll(new Specification<OrderPO>() {

            @Override
            public Predicate toPredicate(Root<OrderPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                list.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("type").as(Integer.class), 1),
                        criteriaBuilder.equal(root.get("type").as(Integer.class), 3)
                ));

                if (ObjectUtil.isNotEmpty(orderPO.getMemberPrice())) {
                    list.add(criteriaBuilder.equal(root.get("memberPrice").as(Integer.class), orderPO.getMemberPrice()));
                }

                if (ObjectUtil.isNotEmpty(orderPO.getLevel())) {
                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), orderPO.getLevel()));
                }

                if (ObjectUtil.isNotEmpty(orderPO.getOrderState())) {
                    list.add(criteriaBuilder.equal(root.get("orderState").as(Integer.class), orderPO.getOrderState()));
                }

                if (ObjectUtil.isNotEmpty(orderPO.getMemberPrice())) {
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), orderPO.getType()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return orderPOPage;
    }

    @Override
    public OrderVO queryDetails(Integer orderId, Integer type) {

        OrderPO orderPO = orderDao.findByIdAndType(orderId, type);

        if (ObjectUtil.isNull(orderPO)){
            throw new SCException(400127,"订单数据不存在");
        }

        OrderVO orderVO = new OrderVO();

        BeanUtil.copyProperties(orderPO, orderVO);

        return orderVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String redemptionCard(ActivationCodePO activationCodePO1) throws SCException {

        CardTypePO cardTypePO1 = cardTypeDao.queryByIdAndStatus(activationCodePO1.getCardTypeId(), SysConstant.STATUS_Enable_INT.getConstant());
        UserPO userPO = userDao.findByUserId(activationCodePO1.getUserId());

        double energy = userPO.getEnergy() - cardTypePO1.getEnergy();
        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER);
        }

        if (cardTypePO1 == null) {
            throw new SCException(ResultEnum.CARD_TYPE_IS_NULL);
        }

        if (energy < 0) {
            throw new SCException(ResultEnum.CARD_INSUFFICIENT_BALANCE);
        }

        ActivationCodePO activationCodePO = new ActivationCodePO();

        //生成兑换码
        String code = RandomUtil.getRandomStringByLength(4) + cardTypePO1.getEnergy() + DateUtil.current("yyMMdd");

        // 生成 订单号
        String orderNumber = RandomUtil.getRandomStringByLength(10);

        activationCodePO.setOrderNumber(orderNumber);
        activationCodePO.setType(SysConstant.TYPE_CARD.getConstant());
        activationCodePO.setUserId(activationCodePO1.getUserId());
        activationCodePO.setGymShopId(cardTypePO1.getGymShopId());
        activationCodePO.setActivationCode(code);
        activationCodePO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        activationCodePO.setEnergy(cardTypePO1.getEnergy());
        activationCodePO.setReservedName(activationCodePO1.getReservedName());
        activationCodePO.setReservedPhone(activationCodePO1.getReservedPhone());
        activationCodePO.setCardTypeId(activationCodePO1.getCardTypeId());

        activationCodeDao.save(activationCodePO);
        //更新用户能量值
        userPO.setEnergy(energy);
        userDao.save(userPO);

        //生成记录
        flowRecordUtil.addExpensesRecord(activationCodePO1.getUserId(),cardTypePO1.getEnergy(),energy,0,
                5,4,"转卡兑换","转卡兑换",cardTypePO1.getGymShopId());
        return code;
    }

    @Override
    public boolean addCardType(CardTypePO cardTypePO) throws SCException {
        CardTypePO cardTypePO1 = cardTypeDao.queryByGymShopIdAndName(cardTypePO.getGymShopId(), cardTypePO.getName());

        if (cardTypePO1 != null) {
            throw new SCException(ResultEnum.CARD_NAME_REPEAT);
        }

        cardTypePO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        cardTypePO.setStatus(SysConstant.STATUS_Enable_INT.getConstant());

        cardTypeDao.save(cardTypePO);

        return true;
    }

    @Override
    public boolean updateCardType(int id, int type) {

        if (type != 0 && type != 1) {
            throw new SCException(ResultEnum.TYPE_ERROR);
        }

        CardTypePO cardType = cardTypeDao.queryById(id);
        cardType.setStatus(type);
        cardTypeDao.save(cardType);

        return true;
    }

    private BigDecimal getMemberPrice(Integer userId, Integer memberLevel){

        CommissionPO commissionPO = commissionDao.findByUserId(userId);

        if (ObjectUtil.isEmpty(commissionPO)){
            throw new SCException(40018,"佣金数据不存在");
        }

        CommissionPO superiorPO = commissionDao.findByUserId(commissionPO.getSuperior());

        SysMemberPO sysMemberPO = sysMemberDao.queryByLevel(memberLevel);

        System.out.println(sysMemberPO);

        if (ObjectUtil.isEmpty(sysMemberPO)){
            throw new SCException(40019, "会员信息不存在");
        }

        BigDecimal memberPrice = sysMemberPO.getPrice();

        if (ObjectUtil.isEmpty(superiorPO)){

            return memberPrice;
        }

        Integer role = superiorPO.getRole();

        if(role == 1 || role == 0){
            AgentSchemePO agentSchemePO = agentSchemeDao.findByTypeAndMemberLevel(1, memberLevel);

            if (ObjectUtil.isEmpty(agentSchemePO)){
                return memberPrice;
            }

            return agentSchemePO.getAgentPrice();
        }

        ChannelSchemePO channelSchemePO;

        if(role == 2){

            channelSchemePO = channelSchemeDao.findByMemberLevelAndLevelAndType(memberLevel, 1,1);
        }else {
            channelSchemePO = channelSchemeDao.findByUserIdAndMemberLevelAndLevelAndType(superiorPO.getUserId(),memberLevel,2,1);
        }

        if(ObjectUtil.isEmpty(channelSchemePO)){
            return memberPrice;
        }

        return channelSchemePO.getChannelPrice();
    }

    private int getLevel(Integer money) {


        if (0 < money && money < 500) {

            return 0;//  return "普通用户";
        } else if (500 <= money && money < 2000) {

            return 1;//            return "体验会员";
        }else if (2000 <= money && money < 5000) {

            return 2;//            return "大众会员";
        } else if (5000 <= money && money < 10000) {

            return 3;//            return "精英会员";
        } else if (10000 <= money) {

            return 4;//            return "皇家会员";
        } else {
            throw new SCException(ResultEnum.LEVER_ERROR);
        }
    }
}
