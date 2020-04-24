package com.gymnasium.core.wxpay;

import com.github.wxpay.sdk.WXPayUtil;
import com.gymnasium.user.util.DateUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Wxpays {


    private static final WXPayConfig config = new WXPayConfig();

    /**
     * 统一下单方法
     *
     * @param body       商品描述
     * @param attach     回传参数
     * @param outTradeNo 商户系统内部订单号
     * @param totalFee   订单总金额，单位为分
     * @param remoteIP   用户端实际ip
     * @param notify_url   回调地址
     * @param openId   用户openId
     * @return APP 发起支付需要的参数
     * @throws Exception ex
     */
    public static Map<String, String> unifiedOrder(final String body, final String attach, final String outTradeNo,
                                                   final String totalFee, final String remoteIP, final String notify_url,
                                                   final String openId) throws Exception {
        Map<String, String> data = new HashMap<>(12);
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        //随机字符串
        data.put("nonce_str", com.github.wxpay.sdk.WXPayUtil.generateNonceStr());
        //商品描述
        data.put("body", body);
        // 该字段主要用于商户携带订单的自定义数据
        data.put("attach", attach);
        //订单号
        data.put("out_trade_no", outTradeNo);
        //标价金额
        data.put("total_fee", totalFee);
        data.put("openid", openId);
        //客户端IP
        data.put("spbill_create_ip", remoteIP);
        //回调地址
        data.put("notify_url", notify_url);
        //交易类型JSAPI
        data.put("trade_type", "JSAPI");
        data.put("sign", WXPayUtil.generateSignature(data, config.getKey()));
        // 请求API返回的数据
        com.github.wxpay.sdk.WXPay wxPay = new com.github.wxpay.sdk.WXPay(config);
        Map<String, String> resp = wxPay.unifiedOrder(data);
        System.out.println(resp.toString());
        Map<String, String> result = new HashMap<>();
        if (resp.get("return_code").equals("SUCCESS") && resp.get("result_code").equals("SUCCESS")) {
            if (WXPayUtil.isSignatureValid(resp, config.getKey())) {
                result.put("appId", resp.get("appid"));
                result.put("nonceStr", WXPayUtil.generateNonceStr());
                result.put("package", "prepay_id=" + resp.get("prepay_id"));
                result.put("signType", "MD5");
                result.put("timeStamp", DateUtil.getTimestamp() + "");
                String sign = WXPayUtil.generateSignature(result, config.getKey());
                System.err.println("---------------------------------------------------");
                System.err.println(sign);
                result.put("status", "SUCCESS");
                result.put("sign", sign);
                return result;
            } else {
                throw new RuntimeException("统一下单校验签名失败！");
            }
        } else {
            result.put("status", "FAIL");
            result.put("message", resp.get("return_msg"));
            return result;
        }
    }


//    /**
//     * APP统一下单方法
//     *
//     * @param body       商品描述
//     * @param attach     回传参数
//     * @param outTradeNo 商户系统内部订单号
//     * @param totalFee   订单总金额，单位为分
//     * @param remoteIP   用户端实际ip
//     * @return APP 发起支付需要的参数
//     * @throws Exception ex
//     */
//    public static Map<String, String> unifiedOrder(final String body, final String attach, final String outTradeNo, final String totalFee, final String remoteIP, final String notify_url) throws Exception {
//
//        String post = httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", "<xml>\n" +
//                "\t<appid>wxc2c9c1f68146c9a6</appid>\n" +
//                "\t<body>腾讯充值中心-QQ会员充值</body>\n" +
//                "\t<mch_id>1534111721</mch_id>\n" +
//                "\t<nonce_str>5K8264ILTKCH16CQ2502SI8ZNMTM67VS</nonce_str>\n" +
//                "\t<notify_url>http://www.weixin.qq.com/wxpay/pay.php</notify_url>\n" +
//                "\t<out_trade_no>1</out_trade_no>\n" +
//                "\t<spbill_create_ip>123.12.12.123</spbill_create_ip>\n" +
//                "\t<total_fee>3</total_fee>\n" +
//                "\t<trade_type>JSAPI</trade_type>\n" +
//                "\t<sign>2DD98434150F938193B3F2BA36E5E799</sign>\n" +
//                "</xml>");
//        System.out.println(post);
//        return null;
//
//    }

    private static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuilder buffer = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}" + ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}" + e);
        }
        return null;
    }

}
