package com.gymnasium.store.Controller;

import com.gymnasium.store.VO.FundFlowRequestVO;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.FundFlowPO;
import com.gymnasium.store.VO.FundFlowVO;
import com.gymnasium.store.service.FundFlowService;
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
 * @ClassName: oyoc_interface
 * @Date: 2019/6/1 17:25
 * @Description:
 */
@Api(tags = "资金流水 API")
@RestController
@RequestMapping(value = "/fundFlow")
public class FundFlowController {

    @Autowired
    private FundFlowService fundFlowService;

    @ApiOperation(value = "资金流水列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "flowNumber", value = "流水号"),
            @ApiImplicitParam(name = "transactionType", value = "交易类型"),
            @ApiImplicitParam(name = "type", value = "流水类型"),
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "minMoney", value = "最小金钱"),
            @ApiImplicitParam(name = "maxMoney", value = "最大金钱"),
            @ApiImplicitParam(name = "title", value = "项目名称")
    })
    @RequestMapping(value = "/pageAllByStatus", method = RequestMethod.POST)
    public Result<Page<FundFlowPO>> pageAllByStatus(Paging page, FundFlowVO fundFlowVO, FundFlowRequestVO fundFlowRequestVO){

        return ResultUtil.success(fundFlowService.pageAllByStatus(page, fundFlowVO,fundFlowRequestVO));
    }
    @ApiOperation(value = "查询提现明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/queryFlowRecord", method = RequestMethod.POST)
    public Result<Page<FundFlowPO>> queryFlowRecord(Paging page, Integer userId){

        return ResultUtil.success(fundFlowService.queryFlowRecord(page, userId));
    }
}
