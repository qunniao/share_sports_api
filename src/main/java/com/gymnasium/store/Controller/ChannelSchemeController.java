package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.ChannelSchemePO;
import com.gymnasium.store.VO.AgentSchemeVO;
import com.gymnasium.store.VO.ChannelSchemeVO;
import com.gymnasium.store.service.ChannelSchemeService;
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

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:15
 * @Description:
 */

@Api(tags = "渠道方案 API")
@RestController
@RequestMapping(value = "/channelScheme")
public class ChannelSchemeController {

    @Autowired
    private ChannelSchemeService channelSchemeService;

    @ApiOperation("一级渠道(level:1)方案编辑接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "operatorId", value = "操作人id", required = true),
            @ApiImplicitParam(name = "productId", value = "产品id"),
            @ApiImplicitParam(name = "channelPrice", value = "渠道价", required = true),
            @ApiImplicitParam(name = "profit", value = "利润", required = true),
            @ApiImplicitParam(name = "type", value = "类型1: 会员; 2:产品", required = true),
            @ApiImplicitParam(name = "memberLevel", value = "会员等级： 1体验会员; 2:大众会员; 3: 精英会员 4:皇家会员")
    })
    @RequestMapping(value = "/editOneLevel", method = RequestMethod.POST)
    public Result<Boolean> editOneLevel(ChannelSchemeVO channelSchemeVO){

        return ResultUtil.success(channelSchemeService.editOneLevel(channelSchemeVO));
    }

    @ApiOperation("二级渠道(level:2)方案编辑接口")
            @ApiImplicitParams({
                    @ApiImplicitParam(name = "userId", value = "设置人id", required = true),
                    @ApiImplicitParam(name = "productId", value = "产品id", required = true),
                    @ApiImplicitParam(name = "channelPrice", value = "渠道价格", required = true),
                    @ApiImplicitParam(name = "profit", value = "利润", required = true),
                    @ApiImplicitParam(name = "type", value = "类型1: 会员; 2:产品", required = true),
                    @ApiImplicitParam(name = "memberLevel", value = "会员等级： 1体验会员; 2:大众会员; 3: 精英会员 4:皇家会员")
            })
    @RequestMapping(value = "/editSecondLevel", method = RequestMethod.POST)
    public Result<Boolean> editSecondLevel(ChannelSchemeVO channelSchemeVO){

        return ResultUtil.success(channelSchemeService.editSecondLevel(channelSchemeVO));
    }

    @ApiOperation("根据用户id和产品id查询二级渠道方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productId", value = "产品id", required = true)
    })
    @RequestMapping(value = "/findByUserIdAndProductId", method = RequestMethod.POST)
    public Result<ChannelSchemeVO> findByUserIdAndProductId(Integer userId, Integer productId){

        return ResultUtil.success(channelSchemeService.findByUserIdAndProductId(userId, productId));
    }

    @ApiOperation("根据渠道编码查询渠道方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "channelCode", value = "渠道编码", required = true)
    })
    @RequestMapping(value = "/findByChannelCode", method = RequestMethod.POST)
    public Result<ChannelSchemeVO> findByChannelCode(String channelCode){

        return ResultUtil.success(channelSchemeService.findByChannelCode(channelCode));
    }

    @ApiOperation("一级渠道方案分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "type", value = "无：全部、2：会员、1：商品")
    })
    @RequestMapping(value = "/pageOneChannelScheme", method = RequestMethod.POST)
    public Result<Page<ChannelSchemePO>> pageOneChannelScheme(Paging page, Integer type){

        return ResultUtil.success(channelSchemeService.pageOneChannelScheme(page, type));
    }

    @ApiOperation("二级渠道方案分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "userId", value = "一级渠道人id", required = true),
            @ApiImplicitParam(name = "type", value = "无：全部、2：会员、1：商品")
    })
    @RequestMapping(value = "/pageTowChannelScheme", method = RequestMethod.POST)
    public Result<Page<ChannelSchemePO>>pageTowChannelScheme(Paging page, Integer userId, Integer type){

        return ResultUtil.success(channelSchemeService.pageTowChannelScheme(page,userId, type));
    }

    @ApiOperation("根据操作人id和产品id查询一级渠道产品方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operatorId", value = "操作人id", required = true),
            @ApiImplicitParam(name = "productId", value = "产品id", required = true)
    })
    @RequestMapping(value = "/findTowProductScheme", method = RequestMethod.POST)
    public Result<ChannelSchemePO>findTowProductScheme(Integer userId, Integer productId){

        return ResultUtil.success(channelSchemeService.findTowProductScheme(userId, productId));
    }

    @ApiOperation("渠道方案佣金修改接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "profit", value = "利润",required = true)
    })
    @RequestMapping(value = "/updateChannelScheme", method = RequestMethod.POST)
    public Result<Boolean> updateChannelScheme(ChannelSchemeVO channelSchemeVO){

        return ResultUtil.success(channelSchemeService.updateChannelScheme(channelSchemeVO));
    }

    @ApiOperation("根据产品名称模糊查询渠道方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "产品名称", required = true),
            @ApiImplicitParam(name = "type", value = "类型1：会员，2：产品"),
            @ApiImplicitParam(name = "level", value = "渠道等级：1.一级渠道2.二级渠道", required = true),
            @ApiImplicitParam(name = "operatorId", value = "操作人id")
    })
    @RequestMapping(value = "/byProductName", method = RequestMethod.POST)
    public Result<ChannelSchemeVO> queryAgentScheme(ChannelSchemeVO channelSchemeVO, String name){

        return ResultUtil.success(channelSchemeService.queryChannelScheme(channelSchemeVO, name));
    }

    @ApiOperation("渠道方案查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "level", value = "渠道等级"),
            @ApiImplicitParam(name = "userId", value = "用户id")
    })
    @RequestMapping(value = "/queryAgentScheme", method = RequestMethod.POST)
    public Result<ChannelSchemeVO> queryAgentScheme(Integer type, Integer level, Integer userId){

        return ResultUtil.success(channelSchemeService.queryAllChannelScheme(type, level, userId));
    }

    @ApiOperation("后台渠道方案编辑接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "userId", value = "设置人id"),
            @ApiImplicitParam(name = "channelPrice", value = "渠道价"),
            @ApiImplicitParam(name = "profit", value = "利润"),
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "level", value = "渠道等级"),
            @ApiImplicitParam(name = "memberLevel", value = "会员等级")
    })
    @RequestMapping(value = "/editChannelScheme", method = RequestMethod.POST)
    public Result<ChannelSchemeVO> editChannelScheme(ChannelSchemeVO channelSchemeVO){

        return ResultUtil.success(channelSchemeService.editChannelScheme(channelSchemeVO));
    }

    @ApiOperation("根据产品id查询一级渠道方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品Id", required = true),
    })
    @RequestMapping(value = "/{productId}", method = RequestMethod.POST)
    public Result<ChannelSchemeVO> queryByProductId(@PathVariable Integer productId){

        return ResultUtil.success(channelSchemeService.queryByProductId(productId));
    }

}
