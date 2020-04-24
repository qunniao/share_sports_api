package com.gymnasium.order.Controller;

import cn.hutool.core.util.ObjectUtil;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.config.AliPayConfig;
import com.gymnasium.order.Service.AliPayService;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.Dao.ShopOrderDao;
import com.gymnasium.store.PO.ShopOrderPO;
import com.ijpay.alipay.AliPayApi;
import com.lly835.bestpay.rest.type.Post;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 边书恒
 * @Title: AliPayController
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/2 10:33
 */
@RestController
@Api("Ali支付相关接口")
@RequestMapping("/aliPay")
public class AliPayController{

    @Autowired
    private AliPayService aliPayService;

    @PostMapping(value = "/wapPay")
    @ApiOperation(value = "商品手机网页支付", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "orderNumber", value = "订单编号", required = true)
    })
    public void wapPay(String userId, String orderNumber) {

       aliPayService.wapPay(userId,orderNumber);
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
    public void orderPay(String userId, String orderNumber){

         aliPayService.orderPay(userId, orderNumber);
    }

    /**
     * 单笔转账到支付宝账户
     * https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.54Ty29&
     * treeId=193&articleId=106236&docType=1
     */
    @PostMapping(value = "/transfer")
    @ResponseBody
    public boolean transfer(String orderNumber ) {
        boolean isSuccess = false;
        String total_amount = "66";
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setOutBizNo(orderNumber);
        model.setPayeeType("ALIPAY_LOGONID");
        model.setPayeeAccount("poiuvs1167@sandbox.com");
        model.setAmount(total_amount);
        model.setPayerShowName("测试退款");
        model.setPayerRealName("沙箱环境");
        model.setRemark("javen测试单笔转账到支付宝");

        try {
            isSuccess = AliPayApi.transfer(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


    @PostMapping(value = "/notify_url")
    public void wapPayUnifiedNotify(HttpServletRequest request) {
        aliPayService.notifyUrl(request);
    }

}