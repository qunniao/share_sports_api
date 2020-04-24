package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.store.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/3 15:52
 * @Description:
 */
@Api(tags = "代理团队 API")
@RestController
@RequestMapping(value = "/agentTeam")
public class AgentTeamController {

    @Autowired
    private AgentService agentService;

    @ApiOperation(value = "查询客户团队接口", notes = "totalNumber:订单数; totalCommission:总佣金；sales：销售额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/queryClientTeam", method = RequestMethod.POST)
    public Result<List<Map>> queryClientTeam(Integer userId, Paging page){

        return ResultUtil.success(agentService.queryClientTeam(userId, page));
    }

    @ApiOperation(value = "查询代理团队接口", notes = "totalNumber:订单数; totalCommission:总佣金；sales：销售额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "status", value = "审核状态(auditStatus):0.删除1.未审核2.通过-1.拒绝")
    })
    @RequestMapping(value = "/queryAgentTeam", method = RequestMethod.POST)
    public Result<List<Map>> queryAgentTeam(Integer userId, Integer status, Paging page){

        return ResultUtil.success(agentService.queryAgentTeam(userId, status, page));
    }

    @ApiOperation(value = "查询订单总数接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true)
    })
    @RequestMapping(value = "/countOrderNumber", method = RequestMethod.POST)
    public Result<Integer> countOrderNumber(Integer userId){

        return ResultUtil.success(agentService.countOrderNumber(userId));
    }

    @ApiOperation(value = "统计我的团队总人数接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/countTeamSize", method = RequestMethod.POST)
    public Result<Integer> countTeamSize(Integer userId){

        return ResultUtil.success(agentService.countTeamSize(userId));
    }

    @ApiOperation(value = "统计代理团队数, 销售额, 佣金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/countAgentTeam", method = RequestMethod.POST)
    public Result<Map<String, Object>> countAgentTeam(Integer userId){

        return ResultUtil.success(agentService.countAgentTeam(userId));
    }

    @ApiOperation(value = "统计客户团队数, 销售额, 佣金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/countClientTeam", method = RequestMethod.POST)
    public Result<Map<String, Object>> countClientTeam(Integer userId){

        return ResultUtil.success(agentService.countClientTeam(userId));
    }

}
