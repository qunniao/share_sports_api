package com.gymnasium.store.Controller;

import cn.hutool.core.bean.BeanUtil;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.AgentSchemePO;
import com.gymnasium.store.VO.AgentSchemeVO;
import com.gymnasium.store.VO.ChannelSchemeVO;
import com.gymnasium.store.service.AgentSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:14
 * @Description:
 */
@Api(tags = "代理方案 API")
@RestController
@RequestMapping(value = "/agentScheme")
public class AgentSchemeController {

    @Autowired
    private AgentSchemeService agentSchemeService;

    @ApiOperation("代理方案编辑接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "设置人id"),
            @ApiImplicitParam(name = "operatorId", value = "操作人id", required = true),
            @ApiImplicitParam(name = "productId", value = "产品id"),
            @ApiImplicitParam(name = "memberLevel", value = "会员等级"),
            @ApiImplicitParam(name = "agentPrice", value = "代理价"),
            @ApiImplicitParam(name = "type", value = "类型，1:会员，2：产品", required = true),
            @ApiImplicitParam(name = "agentCommission", value = "代理人佣金"),
            @ApiImplicitParam(name = "agentDeveloperCommission", value = "代理人发展佣金")
    })
    @RequestMapping(value = "/editAgentScheme", method = RequestMethod.POST)
    public Result<Boolean> editAgentScheme(AgentSchemeVO agentSchemeVO){

        return ResultUtil.success(agentSchemeService.editAgentScheme(agentSchemeVO));
    }

    @ApiOperation("根据产品名称模糊查询产品代理方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "产品名称", required = true),
            @ApiImplicitParam(name = "type", value = "类型1：会员，2：产品"),
    })
    @RequestMapping(value = "/byProductName", method = RequestMethod.POST)
    public Result<List<AgentSchemePO>> queryAgentScheme(String name, Integer type){

        return ResultUtil.success(agentSchemeService.queryAgentScheme(name, type));
    }


    @ApiOperation("代理方案佣金修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "agentCommission", value = "代理人佣金", required = true),
            @ApiImplicitParam(name = "agentDeveloperCommission", value = "代理人发展佣金", required = true)
    })
    @RequestMapping(value = "/updateAgentScheme", method = RequestMethod.POST)
    public Result<Boolean> updateAgentScheme(AgentSchemeVO  agentSchemeVO){

        return ResultUtil.success(agentSchemeService.updateAgentScheme(agentSchemeVO));
    }

    @ApiOperation("代理方案分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "agentCode", value = "方案编码"),
            @ApiImplicitParam(name = "productId", value = "产品id"),
            @ApiImplicitParam(name = "type", value = "类型1，会员，2产品"),
            @ApiImplicitParam(name = "memberLevel", value = "会员等级： 1体验会员; 2:大众会员; 3: 精英会员 4:皇家会员"),
            @ApiImplicitParam(name = "agentCommission", value = "代理人佣金"),
            @ApiImplicitParam(name = "agentDeveloperCommission", value = "客户佣金")
    })
    @RequestMapping(value = "/pageAgentScheme", method = RequestMethod.POST)
    public Result<Page<AgentSchemePO>> pageAgentScheme(Paging page, AgentSchemeVO agentSchemeVO){

        return ResultUtil.success(agentSchemeService.pageAgentScheme(page, agentSchemeVO));
    }

    @ApiOperation(value = "后台代理方案编辑", notes = "需要加token, ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "id", value = "主键id"),
            @ApiImplicitParam(name = "userId", value = "用户id"),
            @ApiImplicitParam(name = "operatorId", value = "操作人id"),
            @ApiImplicitParam(name = "productId", value = "产品id"),
            @ApiImplicitParam(name = "agentPrice", value = "代理价格"),
            @ApiImplicitParam(name = "type", value = "类型类型1，会员，2产品"),
            @ApiImplicitParam(name = "memberLevel", value = "会员等级： 1体验会员; 2:大众会员; 3: 精英会员 4:皇家会员"),
            @ApiImplicitParam(name = "agentCommission", value = "代理人佣金"),
            @ApiImplicitParam(name = "agentDeveloperCommission", value = "客户佣金")
    })
    @RequestMapping(value = "/editScheme", method = RequestMethod.POST)
    public Result<Boolean> editScheme(AgentSchemeVO agentSchemeVO){

        return ResultUtil.success(agentSchemeService.editScheme(agentSchemeVO));
    }

    @ApiOperation("根据产品id查询代理方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品Id", required = true),
    })
    @RequestMapping(value = "/{productId}", method = RequestMethod.POST)
    public Result<AgentSchemeVO> queryByProductId(@PathVariable Integer productId){

        return ResultUtil.success(agentSchemeService.queryByProductId(productId));
    }

}
