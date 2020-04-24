package com.gymnasium.order.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.wxpay.sdk.WXPayUtil;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.core.wxpay.IpUtils;
import com.gymnasium.core.wxpay.WXPayConfig;
import com.gymnasium.core.wxpay.Wxpays;
import com.gymnasium.order.DAO.OrderDao;
import com.gymnasium.order.PO.OrderPO;
import com.gymnasium.order.Service.PayService;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.Dao.ServicePersonnelDao;
import com.gymnasium.store.Dao.ServiceRewardDao;
import com.gymnasium.store.Dao.ServiceUserDao;
import com.gymnasium.store.Dao.ShopOrderDao;
import com.gymnasium.store.PO.ServicePersonnelPO;
import com.gymnasium.store.PO.ServiceRewardPO;
import com.gymnasium.store.PO.ServiceUser;
import com.gymnasium.store.PO.ShopOrderPO;
import com.gymnasium.store.VO.ServiceRewardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    private WXPayConfig wxPayConfig = new WXPayConfig();

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FlowRecordUtil flowRecordUtil;

    @Autowired
    private OrderUtil orderUtil;

    @Autowired
    private CommissionUtil commissionUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ServicePersonnelDao servicePersonnelDao;

    @Autowired
    private ServiceRewardDao serviceRewardDao;

    @Autowired
    private JgUtil jgUtil;

    @Autowired
    private ActivationUtil activationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result wxPay(String userId, String orderNumber) throws Exception {

        UserPO userPO = userDao.findByUserId(userId);
        //查询这个用户是否存在
        if (userPO == null) {
            return ResultUtil.error(444, "没有这个用户");
        }

        //查询这个订单是否存在
        ShopOrderPO shopOrderPO = shopOrderDao.findByUserIdAndOrderNumber(userPO.getUid(), orderNumber);

        if (shopOrderPO == null) {
            return ResultUtil.error(444, "找不到这个订单");
        }

        if (shopOrderPO.getOrderState().intValue() != 1) {
            return ResultUtil.error(444, "订单状态异常,可能已经支付");
        }

        if (shopOrderPO.getSettlementType() == 2) {
            // 用户能量值
            Double userEnergy = userPO.getEnergy();
            // 支付的价格
            BigDecimal payPrice = shopOrderPO.getPayPrice();

            if (userEnergy < payPrice.doubleValue()) {
                return ResultUtil.error(445, "用户能量值不足");
            }

            // 用户剩余能量值
            BigDecimal payEnergy = BigDecimal.valueOf(userEnergy).subtract(payPrice);

            shopOrderPO.setOrderState(2);
            shopOrderPO.setPayTime(new Date());
            shopOrderPO.setLastTime(new Date());
            shopOrderPO.setPayWay(1);
            shopOrderDao.save(shopOrderPO);
            // 扣除用户能量值
            userPO.setEnergy(payEnergy.doubleValue());
            userDao.save(userPO);

            flowRecordUtil.addExpensesRecord(userPO.getUserId(), shopOrderPO.getPayPrice().doubleValue(),
                    userPO.getEnergy(), 0, 1, 6, "购买商品扣除能量值", shopOrderPO.getRemark(),
                    null);

            return ResultUtil.success();
        }

        //获取客户端的Ip地址
        String remoteAddr = IpUtils.getIpAddr(request);
        String notify_url = "http://47.110.34.1:8080/gymnasium/pay/wxPayUnifiedNotify";

        String attach = "{\"type\":" + 4 + "}";

        // todo: 修改金额
//      String totalFee = orderPO.getPayPrice().multiply(new BigDecimal(100)).intValue()

        String totalFee = "1";

        // 用户openId
        String openId = userPO.getOpenid();

        if (StrUtil.isBlank(openId)) {
            throw new SCException(ResultEnum.OPENID_IS_NULL);
        }

        //请求微信服务端
        Map map = Wxpays.unifiedOrder("订单微信支付", attach, shopOrderPO.getOrderNumber(),
                totalFee, remoteAddr, notify_url, openId);

        return ResultUtil.success(map);
    }

    @Override
    public Result orderPay(String userId, String orderNumber) throws Exception {

        UserPO userPO = userDao.findByUserId(userId);

        //查询这个用户是否存在
        if (userPO == null) {
            return ResultUtil.error(444, "没有这个用户");
        }
        //查询这个订单是否存在
        OrderPO orderPO = orderDao.findByOrderNumber(orderNumber);

        if (orderPO == null) {
            return ResultUtil.error(444, "没有这个订单");
        }

        if (orderPO.getOrderState().intValue() != 1) {
            return ResultUtil.error(444, "订单状态异常,可能已经支付");
        }

        //生成支付订单号
//        String orderNum = RandomUtil.getShortU
//        uid() + SnowFlakeIdGenerator.nextId();

        // 总金额 （分）
//        String totalFee = orderPO.getPrice().multiply(new BigDecimal(100)).intValue() + "";

        String totalFee = "1";

        // 客户端ip
        String remoteIP = IpUtils.getIpAddr(request);

        // 回调地址
        String notify_url = "http://47.110.34.1:8080/gymnasium/pay/wxPayUnifiedNotify";

        String attach = "{\"type\":" + orderPO.getType() + "}";

        // 用户openId
        String openId = userPO.getOpenid();

        if (StrUtil.isBlank(openId)) {
            throw new SCException(ResultEnum.OPENID_IS_NULL);
        }
        //请求微信服务端
        Map map = Wxpays.unifiedOrder(orderPO.getProductItem(), attach, orderNumber, totalFee, remoteIP, notify_url, openId);

        return ResultUtil.success(map);
    }

    @Override
    public Result servicePay(ServiceRewardVO serviceRewardVO) throws Exception {

        UserPO userPO = userDao.findByUid(serviceRewardVO.getUserId());

        //查询这个用户是否存在
        if (userPO == null) {
            return ResultUtil.error(444, "没有这个用户");
        }
        //查询客服是否存在
        ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.getOne(serviceRewardVO.getServiceId());

        if (ObjectUtil.isNull(servicePersonnelPO)) {
            return ResultUtil.error(ResultEnum.SERVICE_IS_NULL);
        }
        String body = "客服打赏微信支付";

        // 回调地址
        String notify_url = "http://47.96.31.157:8080/gymnasium/pay/wxPayUnifiedNotify";

        JSONObject jsonObject = JSONUtil.parseObj(serviceRewardVO);
        jsonObject.append("type", 5);

        String attach = JSONUtil.toJsonStr(jsonObject);

        //生成支付订单号
        String outTradeNo = RandomUtil.getShortUuid() + SnowFlakeIdGenerator.nextId();

        String totalFee = serviceRewardVO.getMoney().multiply(new BigDecimal(100)).intValue() + "";

        // 客户端ip
        String remoteIP = IpUtils.getIpAddr(request);

        // 用户openId
        String openId = userPO.getOpenid();

        if (StrUtil.isBlank(openId)) {
            throw new SCException(ResultEnum.OPENID_IS_NULL);
        }

        Map map = Wxpays.unifiedOrder(body, attach, outTradeNo, totalFee, remoteIP, notify_url, openId);

        return ResultUtil.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String wxPayUnifiedNotify(String notifyXml) {

        System.err.println("notifyXml\t" + notifyXml);
        synchronized (new Object()) {
            try {
                Map<String, String> notify = WXPayUtil.xmlToMap(notifyXml);
                String orderNumber = notify.get("out_trade_no");
                String appid = notify.get("appid");
                String totalFee = notify.get("total_fee");
                String attach = notify.get("attach");
                // 交易编号
                String transactionId = notify.get("transaction_id");

                JSONObject json = JSONUtil.parseObj(attach);
                String type = json.getStr("type");

                switch (type) {

                    case "1":
                    case "2":
                    case "3": {
                        OrderPO orderPO = orderDao.findByOrderNumber(orderNumber);

                        //判断订单号是否正确
                        if (GeneralUtils.isEmpty(orderPO)) {
                            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[商户订单不存在]]></return_msg>" + "</xml>";
                        }
                        //判断订单是否是待付款状态(0.已取消1.待付款2.待发货3.待收货4.已完成)
                        if (orderPO.getOrderState().intValue() != 1) {
                            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单状态异常,可能已经支付]]></return_msg>" + "</xml>";
                        }
                        //判断金额是否一致
//                    if (!totalFee.equals(orderPO.getPrice().toString())) {
//                        return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单金额不一致]]></return_msg>" + "</xml>";
//                    }
                        //判断返回码是否是SUCCESS
                        if ("SUCCESS".equals(notify.get("return_code")) && "SUCCESS".equals(notify.get("result_code"))) {
                            //验证签名
                            if (WXPayUtil.isSignatureValid(notify, wxPayConfig.getKey())) {

                                orderUtil.editOrder(type, orderPO);

                                return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
                            }
                        }
                    }
                    break;
                    case "4": {
                        ShopOrderPO shopOrderPO = shopOrderDao.findByOrderNumber(orderNumber);

                        //判断订单号是否正确
                        if (shopOrderPO == null) {
                            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[商户订单不存在]]></return_msg>" + "</xml>";
                        }
                        //判断订单是否是待付款状态(0.已取消1.待付款2.待发货3.待收货4.已完成)
                        if (shopOrderPO.getOrderState().intValue() != 1) {
                            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单状态异常,可能已经支付]]></return_msg>" + "</xml>";
                        }

//                    //判断金额是否一致
//                    if (!totalFee.equals(shopOrderPO.getPayPrice().toString())) {
//                        return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[订单金额不一致]]></return_msg>" + "</xml>";
//                    }
                        //判断返回码是否是SUCCESS
                        if ("SUCCESS".equals(notify.get("return_code")) && "SUCCESS".equals(notify.get("result_code"))) {
                            //验证签名

                            if (WXPayUtil.isSignatureValid(notify, wxPayConfig.getKey())) {

                                shopOrderPO.setOrderState(2);
                                shopOrderPO.setPayTime(new Date());
                                shopOrderPO.setLastTime(new Date());
                                shopOrderPO.setPayWay(2);
                                shopOrderDao.save(shopOrderPO);

                                jgUtil.sendSingleTextByAdmin(shopOrderPO,"2");

                                return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
                            }
                        }

                    }
                    break;
                    case "5": {

                        // 打赏金额
                        String money = json.getStr("money");
                        // 判断金额是否一致
                        System.out.println(money);
                        System.out.println(totalFee);
                        if (!totalFee.equals(money)) {
                            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[打赏金额不一致]]></return_msg>" + "</xml>";
                        }

                        //判断返回码是否是SUCCESS
                        if ("SUCCESS".equals(notify.get("return_code")) && "SUCCESS".equals(notify.get("result_code"))) {
                            //验证签名

                            if (WXPayUtil.isSignatureValid(notify, wxPayConfig.getKey())) {
                                Integer userId = Integer.parseInt(json.getStr("userId"));

                                String content = json.getStr("content");
                                ServiceRewardPO serviceRewardPO = new ServiceRewardPO();
                                serviceRewardPO.setServiceId(Integer.parseInt(json.getStr("serviceId")));
                                serviceRewardPO.setUserId(userId);
                                serviceRewardPO.setContent(content);
                                serviceRewardPO.setStatus(1);

                                serviceRewardDao.save(serviceRewardPO);

                                flowRecordUtil.addFundFlow(userId, 2, 4, null, "打赏客服", content, new BigDecimal(money), transactionId);

                                return "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
                            }
                        }
                    }
                    break;
                    default:
                        break;
                }

                //判断AppId是否正确
                if (!appid.equals(wxPayConfig.getAppID())) {
                    return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[app_id不是该商户本身]]></return_msg>" + "</xml>";
                }

            } catch (Exception ex) {
                return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[产生异常.]]></return_msg>" + "</xml>";
            }
        }
        return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[支付失败.]]></return_msg>" + "</xml>";
    }
}
