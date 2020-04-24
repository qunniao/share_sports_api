package com.gymnasium.order.Controller;

import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.order.PO.WithdrawalPO;
import com.gymnasium.order.Service.WithdrawalService;
import com.gymnasium.store.PO.FundFlowPO;
import com.gymnasium.store.VO.FundFlowVO;
import com.gymnasium.store.service.FundFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/8 09:23
 * @Description:
 */
@Api(tags = "佣金提现 API")
@RestController
@RequestMapping(value = "/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private FundFlowService fundFlowService;

    @ApiOperation(value = "申请佣金提现接口", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "amount", value = "提现金额", required = true),
            @ApiImplicitParam(name = "trueName", value = "真实姓名", required = true),
            @ApiImplicitParam(name = "account", value = "账号", required = true),
//            @ApiImplicitParam(name = "type", value = "提现方式 1.微信，2.支付宝，3银行卡", required = true)
    })
    @RequestMapping(value = "/requestWithdrawal", method = RequestMethod.POST)
    public Result requestWithdrawal(WithdrawalPO withdrawalPO){

        return ResultUtil.success(withdrawalService.requestWithdrawal(withdrawalPO));
    }

    @ApiOperation(value = "审核提现接口", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true),
            @ApiImplicitParam(name = "reviewState", value = "审核状态0.待审核1.同意转账2.拒绝", required = true),
            @ApiImplicitParam(name = "refuse", value = "拒绝原因")
    })
    @RequestMapping(value = "/commissionWithdraw", method = RequestMethod.POST)
    public Result auditWithdraw(Integer id, Integer reviewState, String refuse){

        if (!ObjectUtils.allNotNull(id, reviewState)){
            return ResultUtil.error(ResultEnum.INCOMPLETE_PARAMETER);
        }

        return ResultUtil.success(withdrawalService.auditWithdraw(id,reviewState,refuse));
    }

    @ApiOperation(value = "分页查询用户的提现记录", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/pageUserWithdraw", method = RequestMethod.POST)
    public Result<Page<FundFlowPO>> pageUserWithdraw(Paging page, FundFlowVO fundFlowVO){

        if (!ObjectUtils.allNotNull(page, fundFlowVO.getUserId())){
            return ResultUtil.error(ResultEnum.INCOMPLETE_PARAMETER);
        }

        return ResultUtil.success(fundFlowService.pageWithdrawalRecord(page,fundFlowVO));
    }
    @ApiOperation(value = "分页查询所有提现记录", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true)
    })
    @RequestMapping(value = "/pageWithdraw", method = RequestMethod.POST)
    public Result<Page<FundFlowPO>> pageWithdraw(Paging page, FundFlowVO fundFlowVO){

        if (!ObjectUtils.allNotNull(page)){
            return ResultUtil.error(ResultEnum.INCOMPLETE_PARAMETER);
        }

        return ResultUtil.success(fundFlowService.pageWithdrawalRecord(page,fundFlowVO));
    }

}
