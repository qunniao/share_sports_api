package com.gymnasium.config;

import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 边书恒
 * @Title: AliPayConfig
 * @ProjectName wxpay
 * @Description: TODO
 * @date 2019/8/1 20:25
 */

@Data
@Configuration
@PropertySource("classpath:config/ali_pay.properties")
public class AliPayConfig {

    @Value("${APP_ID}")
    private String appId;

    @Value("${SERVER_URL}")
    public String serverUrl;

    @Value("${NOTIFY_URL}")
    public String notifyUrl;

    @Value("${RETURN_URL}")
    public String returnUrl;

    @Value("${CHARSET}")
    public String charset;

    @Value("${FORMAT}")
    public String format;

    @Value("${LOG_PATH}")
    public String logPath;

    @Value("${SIGN_TYPE}")
    public String signType;

    @Value("${PRIVATE_KEY}")
    public  String privateKey;

    @Value("${PUBLIC_KEY}")
    public String publicKey;


    @PostConstruct
    public void init(){

        System.out.println("aliPay初始化" + appId);

        AliPayApiConfig aliPayApiConfig = AliPayApiConfig.New()
                .setAppId(appId)
                .setAliPayPublicKey(publicKey)
                .setCharset(charset)
                .setPrivateKey(privateKey)
                .setServiceUrl(serverUrl)
                .setSignType(signType)
                .build();
        AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfig);
    }
}
