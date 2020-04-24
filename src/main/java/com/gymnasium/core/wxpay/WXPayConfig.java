package com.gymnasium.core.wxpay;

import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;


public class WXPayConfig implements com.github.wxpay.sdk.WXPayConfig {


    private String domain = "";
    private String appID = "wxc2c9c1f68146c9a6";
    private String mchID = "1534111721";
    private String key = "OYOC2019aa1818182019OYOCaa181818";

    @Override
    public String getAppID() {
        return appID;
    }

    @Override
    public String getMchID() {
        return mchID;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 10 * 1000; // 连接超时时间
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10 * 1000; // 读取超时时间
    }

    /**
     * 微信购买商品回调地址
     */
    public String getUnifiedOrderNotifyURL() {
        return this.domain + "/order/wxPayUnifiedNotify";
    }

    /**
     * 微信充值回调地址
     */
    public String getRechargeNotifyURL() {
        return this.domain + "/order/wxPayRechargeNotify";
    }
}
