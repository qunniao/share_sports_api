package com.gymnasium.card.controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.card.PO.ActivationPO;
import com.gymnasium.card.Service.ActivationService;
import com.gymnasium.card.vo.ActivationRecordVO;
import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.FriendGroupPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/28 17:33
 * @Description:
 */

@Api(tags = "会员卡 API")
@RestController
@RequestMapping(value = "/card")
public class ActivationController {

    @Autowired
    private ActivationService activationService;

    @ApiOperation(value = "激活会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "useUserId", value = "使用人Id", required = true),
            @ApiImplicitParam(name = "cardNum", value = "卡号", required = true),
            @ApiImplicitParam(name = "cardPassword", value = "密码", required = true),
            @ApiImplicitParam(name = "name", value = "使用人姓名", required = true),
            @ApiImplicitParam(name = "phone", value = "电话", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true)

    })
    @RequestMapping(value = "/activationCard", method = RequestMethod.POST)
    public Result<Page<FriendGroupPO>> activationCard(ActivationRecordVO activationRecordVO, String code){

        return ResultUtil.success(activationService.activationCard(activationRecordVO, code));
    }

    @ApiOperation(value = "验证卡号和密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardNum", value = "卡号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @RequestMapping(value = "/verifyCard", method = RequestMethod.POST)
    public Result<Boolean> verifyCard(String cardNum, String password){

        return ResultUtil.success(activationService.verifyCard(cardNum, password));
    }

    @ApiOperation(value = "分页查询会员卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单id")
    })
    @RequestMapping(value = "/queryMemberCardOrder", method = RequestMethod.POST)
    public Result<Page<ActivationPO>> queryMemberCardOrder(Paging page, Integer orderId){

        return ResultUtil.success(activationService.queryMemberCard(page, orderId));
    }

    @ApiOperation(value = "查询我的会员最新一条激活记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "useUserId", value = "使用人id", required = true)
    })
    @RequestMapping(value = "/queryNewestRecord", method = RequestMethod.POST)
    public Result<Page<ActivationPO>> queryNewestRecord(String useUserId){

        return ResultUtil.success(activationService.queryNewestRecord(useUserId));
    }

}
