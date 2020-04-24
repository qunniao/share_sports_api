package com.gymnasium.Util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.card.PO.ActivationPO;
import com.gymnasium.card.PO.ActivationRecordPO;
import com.gymnasium.order.DAO.CouponDao;
import com.gymnasium.order.DAO.CouponOperationDao;
import com.gymnasium.order.DAO.OrderDao;
import com.gymnasium.order.PO.CouponOperationPO;
import com.gymnasium.order.PO.CouponPO;
import com.gymnasium.order.PO.OrderPO;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 边书恒
 * @Title: OrderUtil
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/5 20:27
 */
@Component
public class OrderUtil {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivationUtil activationUtil;

    @Autowired
    private FlowRecordUtil flowRecordUtil;

    @Autowired
    private CommissionUtil commissionUtil;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponOperationDao couponOperationDao;

    public void editOrder(String type, OrderPO orderPO){

        UserPO userPO = userDao.findByUserId(orderPO.getUserId());

        if (ObjectUtil.isNull(userPO)){
            throw new SCException(ResultEnum.USER_REPEAT);
        }

        // 交易类型1.收入2.支出
        Integer transactionType = 0;

        // 流水类型:1.付款2.佣金3.提现
        Integer fundFlowType = 0;

        // 备注
        String content = "";

        // 能量值流水备注
        String remarks = "";

        // 项目类型:1.开会员2.充值能量值3.健身4.健身房转卡5.搭伙抵押6.购买商品
        Integer itemType = 0;

        // 交易类型:1支出,2收入,3冻结,4返还,5扣除
        Integer titleType = 0;

        // 能量值记录类型 0.支出1.收入
        Integer expensesRecordType = 0;

        if ("1".equals(type)) {

            transactionType = 2;
            fundFlowType = 1;
            content = "开通会员";
            remarks = "开通会员支付";
            itemType = 1;
            titleType = 2;
            expensesRecordType = 1;

            // 发放 优惠券
            issueCoupons(orderPO.getLevel(), userPO.getUserId());
        } else if ("2".equals(type)) {

            transactionType = 2;
            fundFlowType = 1;
            content = "充值能量值";
            remarks = "充值能量值支付";
            itemType = 2;
            titleType = 2;
            expensesRecordType = 1;

//            //  添加用户能量值
//            BigDecimal energy = orderPO.getEnergy().add(new BigDecimal(userPO.getEnergy()));
//            userPO.setEnergy(energy.doubleValue());
//
//            Integer level = orderPO.getLevel();
//
//            if (userPO.getLevel() < level) {
//                userPO.setLevel(level);
//            }
//            userDao.save(userPO);

        } else if ("3".equals(type)) {

            transactionType = 2;
            fundFlowType = 1;
            content = "购买会员卡";

            for (int i = 1; i <= orderPO.getNumber(); i++) {

                // 生成会员卡
                ActivationPO activationPO = activationUtil.addActivation(orderPO.getId(), orderPO.getLevel());

                // 生成会员卡订单
                ActivationRecordPO activationRecordPO = activationUtil.addActivationRecord(0,
                        orderPO.getUserId(), activationPO.getCardNum());
                System.err.println("购买会员卡回调");
                System.err.println(activationRecordPO);
            }
        }

        if ("1".equals(type) || "2".equals(type)) {

            // 更新用户信息
            //订单能量值加上用户本身的能量值
            BigDecimal energy = orderPO.getEnergy().add(new BigDecimal(userPO.getEnergy()));
            userPO.setEnergy(energy.doubleValue());
            if (userPO.getLevel() < orderPO.getLevel()) {
                userPO.setLevel(orderPO.getLevel());
            }
            userPO.setUserName(orderPO.getName());
            userDao.save(userPO);

            //添加能量值流水
            flowRecordUtil.addExpensesRecord(userPO.getUserId(), orderPO.getEnergy().doubleValue(),
                    userPO.getEnergy(), expensesRecordType, titleType, itemType, orderPO.getProductItem(), remarks,
                    null);
            System.err.println("开通会员和充值能量值回调");
        }



        //資金流水
        flowRecordUtil.addFundFlow(userPO.getUid(), transactionType, fundFlowType, (long) orderPO.getId(),
                orderPO.getProductItem(), content, orderPO.getPrice());

        //分配佣金
        commissionUtil.allotMemberCommission(userPO.getUid(), orderPO);
        // 修改状态值为2已付款

        orderPO.setPayTime(new Date());
        orderPO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        orderPO.setOrderState(2);
        orderDao.save(orderPO);
    }


    public void issueCoupons(Integer memberLevel, String userId){

        List<CouponPO> couponList = couponDao.queryIssueCoupon(memberLevel);

        if (ObjectUtil.isEmpty(couponList)){
            return;
        }

        List<CouponOperationPO> couponOperationList = new LinkedList<>();

        for (CouponPO coupon : couponList) {

            CouponOperationPO couponOperation = new CouponOperationPO();

            String couponCode = RandomUtil.getRandomStringByLength(10);

            couponOperation.setUserId(userId);
            couponOperation.setCouponId(coupon.getId());
            couponOperation.setCouponCode(couponCode);
            couponOperation.setType(coupon.getType());
            couponOperation.setVerifyWay(2);
            couponOperation.setVerifyState(2);
            couponOperation.setStatus(1);
            Timestamp newDate = new Timestamp(System.currentTimeMillis());
            couponOperation.setVerifyTime(newDate);
            couponOperation.setCreateTime(newDate);
            couponOperation.setUpdateTime(newDate);

            Date ExpirationTime = DateUtil.offsetDay(coupon.getCreateTime(), 365);

            System.out.println(ExpirationTime);

            // 设置过期时间
            couponOperation.setExpirationTime(new Timestamp(ExpirationTime.getTime()));

            couponOperationList.add(couponOperation);
        }

        couponOperationDao.saveAll(couponOperationList);
    }
}
