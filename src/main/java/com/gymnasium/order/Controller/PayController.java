package com.gymnasium.order.Controller;


import com.gymnasium.Util.PO.Result;
import com.gymnasium.order.Service.PayService;
import com.gymnasium.store.VO.ServiceRewardVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 支付相关的接口
 */
@RestController
@Api("支付相关接口")
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 微信支付统一下单API
     *
     * @param userId      用户的Id
     * @param orderNumber 订单号
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "商品支付", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "orderNumber", value = "订单编号", required = true)
    })

    @RequestMapping(value = "/wxPayOrder", method = RequestMethod.POST)
    public Result wxPay(String userId, String orderNumber) throws Exception {
        return payService.wxPay(userId, orderNumber);

    }

    /**
     * 开通会员和充值能量值支付 购买会员卡接口
     * @param userId 用户id
     * @param orderNumber 订单编号
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "开通会员和充值能量值支付 购买会员卡接口", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "orderNumber", value = "订单编号", required = true)
    })
    @RequestMapping(value = "/orderPay", method = RequestMethod.POST)
    public Result orderPay(String userId, String orderNumber)throws Exception{

        return payService.orderPay(userId, orderNumber);
    }

    @ApiOperation(value = "打赏客服", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true),
            @ApiImplicitParam(name = "money", value = "打赏金额", required = true),
            @ApiImplicitParam(name = "content", value = "评价内容", required = true)
    })
    @RequestMapping(value = "/servicePay", method = RequestMethod.POST)
    public Result servicePay(ServiceRewardVO serviceRewardVO)throws Exception {

        return payService.servicePay(serviceRewardVO);
    }

    /**
     * 微信支付异步通知函数（购买）
     */
    @RequestMapping(value = "/wxPayUnifiedNotify", method = RequestMethod.POST)
    public void wxPayUnifiedNotify(@RequestBody String notifyXml, HttpServletResponse response) {
        String result = payService.wxPayUnifiedNotify(notifyXml);
        System.out.println("回调result"+ result);

        response.setCharacterEncoding("utf-8");
        this.writeResp(response, result);
    }

    private void writeResp(HttpServletResponse response, String result) {
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(result);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
