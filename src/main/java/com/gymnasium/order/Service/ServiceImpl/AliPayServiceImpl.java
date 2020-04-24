package com.gymnasium.order.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.db.sql.Order;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.gymnasium.Util.OrderUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.config.AliPayConfig;
import com.gymnasium.order.DAO.OrderDao;
import com.gymnasium.order.PO.OrderPO;
import com.gymnasium.order.Service.AliPayService;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.Dao.ShopOrderDao;
import com.gymnasium.store.PO.ShopOrderPO;
import com.ijpay.alipay.AliPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author 边书恒
 * @Title: AliPayServiceImpl
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/2 11:56
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AliPayConfig aliPayConfig;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderUtil orderUtil;

    @Override
    public void wapPay(String userId, String orderNumber) {

        System.err.println("userId" + userId);
        System.err.println("orderNumber" + orderNumber);

        UserPO userPO = userDao.findByUserId(userId);

        if (ObjectUtil.isNull(userPO)) {
            throw new SCException(44410, "用户不存在");
        }

        ShopOrderPO shopOrderPO = shopOrderDao.findByUserIdAndOrderNumber(userPO.getUid(), orderNumber);

        if (ObjectUtil.isNull(shopOrderPO)) {
            throw new SCException(444, "订单不存在");
        }

        if (shopOrderPO.getOrderState() != 1) {
            throw new SCException(44411, "订单状态异常,可能已经支付");
        }

        // 可选 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        String body = "我是测试数据-By Javen";
        // subject 必选 商品的标题/交易标题/订单标题/订单关键字等。
        String subject = "Javen Wap支付测试";

        // 必选 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
//        String totalAmount = shopOrderPO.getPayPrice().toString();
        String totalAmount = "0.01";
        /**
         * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）
         * 和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。
         */
        String passBackParams = "{\"type\":" + 4 + "}";

        aliPayInfo(body, subject, totalAmount, passBackParams, orderNumber);

    }

    @Override
    public void orderPay(String userId, String orderNumber) {

        UserPO userPO = userDao.findByUserId(userId);

        if (ObjectUtil.isNull(userPO)) {
            throw new SCException(44410, "用户不存在");
        }

        OrderPO orderPO = orderDao.findByOrderNumber(orderNumber);

        if (ObjectUtil.isNull(orderPO)) {
            throw new SCException(444, "订单不存在");
        }

        if (orderPO.getOrderState() != 1) {
            throw new SCException(44411, "订单状态异常,可能已经支付");
        }

        // 可选 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        String body = "我是测试数据-By Javen";
        // subject 必选 商品的标题/交易标题/订单标题/订单关键字等。
        String subject = "购买会员卡 Wap支付测试";

        // 必选 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
//        String totalAmount = orderPO.getPrice().toString();
        String totalAmount = "0.01";
        /**
         * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）
         * 和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。
         */
        String passBackParams = "{\"type\":" + orderPO.getType() + "}";

        aliPayInfo(body, subject, totalAmount, passBackParams, orderNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String notifyUrl(HttpServletRequest request) {

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayApi.toMap(request);

        String passbackParams = params.get("passback_params");

        System.out.println("passback_params:\t" + passbackParams);

        JSONObject json = JSONObject.parseObject(passbackParams);
        String type = json.getString("type");


        // 如果是购买商品
        if ("4".equals(type)) {
            return shopOrderNotifyUrl(params, type);
        } else {

            return orderNotifyUrl(params, type);
        }

    }

    private String shopOrderNotifyUrl(Map<String, String> params, String type) {

        ShopOrderPO shopOrderPO = shopOrderDao.findByOrderNumber(params.get("out_trade_no"));

        try {
            // 验签
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.getPublicKey(),
                    aliPayConfig.getCharset(), aliPayConfig.getSignType());

            // 验证成功
            if (signVerified) {
                // 验证商户唯一订单号
                if (ObjectUtil.isNull(shopOrderPO)) {
                    throw new AlipayApiException("out_trade_no 错误");
                }

                if (shopOrderPO.getOrderState() != 1) {
                    throw new AlipayApiException("订单状态异常,可能已经支付");
                }

//                // 2、判断total_amount是否确实为该订单的实际金额(即商户订单创建时的金额)
//                long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
//                if (total_amount != shopOrderPO.getPayPrice().longValue()) {
//                    throw new AlipayApiException("error total_amount");
//                }
                // 3 todo:校验通知中的 seller_id（或者 seller_email ) 是否为 out_trade_no 这笔单据的对应的操作方

                // 4、验证app_id是否为该商户本身。
                if (!params.get("app_id").equals(aliPayConfig.getAppId())) {
                    throw new AlipayApiException("app_id不一致");
                }

                String tradeStatus = params.get("trade_status");

                // 5. 只有交易通知状态为 TRADE_SUCCESS 或 TRADE_FINISHED 时，支付宝才会认定为买家付款成功。
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {

                    shopOrderPO.setOrderState(2);
                    shopOrderPO.setPayTime(new Date());
                    shopOrderPO.setLastTime(new Date());
                    shopOrderPO.setPayWay(3);
                    shopOrderDao.save(shopOrderPO);

                    return "success";
                }
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    private String orderNotifyUrl(Map<String, String> params, String type) {

        OrderPO orderPO = orderDao.findByOrderNumber(params.get("out_trade_no"));

        try {
            // 验签
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.getPublicKey(),
                    aliPayConfig.getCharset(), aliPayConfig.getSignType());

            // 验证成功
            if (signVerified) {
                // 验证商户唯一订单号
                if (ObjectUtil.isNull(orderPO)) {
                    throw new AlipayApiException("out_trade_no 错误");
                }

                if (orderPO.getOrderState() != 1) {
                    throw new AlipayApiException("订单状态异常,可能已经支付");
                }

//                // 2、判断total_amount是否确实为该订单的实际金额(即商户订单创建时的金额)
//                long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
//                if (total_amount != orderPO.getPrice().longValue()) {
//                    throw new AlipayApiException("error total_amount");
//                }
                // 3 todo:校验通知中的 seller_id（或者 seller_email ) 是否为 out_trade_no 这笔单据的对应的操作方

                // 4、验证app_id是否为该商户本身。
                if (!params.get("app_id").equals(aliPayConfig.getAppId())) {
                    throw new AlipayApiException("app_id不一致");
                }

                String tradeStatus = params.get("trade_status");

                // 5. 只有交易通知状态为 TRADE_SUCCESS 或 TRADE_FINISHED 时，支付宝才会认定为买家付款成功。
                if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {

                    // 订单回调处理业务逻辑
                    orderUtil.editOrder(type, orderPO);

                    return "success";
                }
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    private void aliPayInfo(String body, String subject, String totalAmount, String passbackParams,
                            String orderNumber) {

        // 销售产品码，商家和支付宝签约的产品码
        String productCode = "QUICK_WAP_WAY";

        // 可选 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天
        // （1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeoutExpress = "30m";

        String returnUrl = aliPayConfig.getReturnUrl();
        String notifyUrl = aliPayConfig.getNotifyUrl();

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setPassbackParams(passbackParams);
        String outTradeNo = orderNumber;
        System.err.println("wap outTradeNo>" + outTradeNo);
        model.setOutTradeNo(outTradeNo);
        model.setProductCode(productCode);
        model.setTimeoutExpress(timeoutExpress);

        try {
            AliPayApi.wapPay(response, model, returnUrl, notifyUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
