package com.gymnasium.order.Service;


import javax.servlet.http.HttpServletRequest;

/**
 * @author 边书恒
 * @Title: AliPayService
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/2 11:55
 */
public interface AliPayService {

    void wapPay(String userId, String orderNumber);

    void orderPay(String userId, String orderNumber);

    String notifyUrl(HttpServletRequest request);
}
