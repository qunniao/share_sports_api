package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.store.VO.CommissionVO;
import com.gymnasium.store.service.CommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/29 20:19
 * @Description:
 */
@Api(tags = "佣金 API")
@RestController
@RequestMapping(value = "/commission")
public class CommissionController {

    @Autowired
    private CommissionService commissionService;

    @ApiOperation(value = "统计客户佣金和代理佣金",
        notes = "clientCommissions: 客户佣金; agentCommission: 代理佣金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "设置人id", required = true)
    })
    @RequestMapping(value = "/countCommission", method = RequestMethod.POST)
    public Result<Map<String, Object>> countCommission(Integer userId){

        return ResultUtil.success(commissionService.countCommission(userId));
    }

    @ApiOperation(value = "查询代理人成功提现佣金，可提现佣金",
        notes = "cashed: 已提现; canCarry: 可提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentId", value = "代理人id", required = true)
    })
    @RequestMapping(value = "/queryWithdraw", method = RequestMethod.POST)
    public Result<Map<String, Object>> queryWithdraw(Integer agentId){

        return ResultUtil.success(commissionService.queryWithdraw(agentId));
    }

    @ApiOperation("用户佣金数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "agentId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/queryCommission", method = RequestMethod.POST)
    public Result<CommissionVO> queryCommission(Integer agentId){

        return ResultUtil.success(commissionService.queryCommission(agentId));
    }
}
