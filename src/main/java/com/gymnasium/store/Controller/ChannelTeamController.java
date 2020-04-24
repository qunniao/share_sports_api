package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.ShopOrderPO;
import com.gymnasium.store.service.ChannelService;
import com.gymnasium.store.service.ChannelTeamService;
import com.gymnasium.store.service.CommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/3 15:52
 * @Description:
 */
@Api(tags = "渠道团队 API")
@RestController
@RequestMapping(value = "/channelTeam")
public class ChannelTeamController {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private ChannelTeamService channelTeamService;


    @ApiOperation(value = "二级团队人数，累计佣金，累计销售额统计接口",notes = "amount 总人数\n" +
            "totalCommission 总佣金 sales:总销售额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "设置人id", required = true)
    })
    @RequestMapping(value = "/channelStatistics", method = RequestMethod.POST)
    public Result<Map<String, Object>> channelStatistics(Integer userId){

        return ResultUtil.success(commissionService.channelStatistics(userId));
    }

    @ApiOperation("查询渠道订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "设置人id", required = true),
            @ApiImplicitParam(name = "role", value = "客户类型：0:全部、1:客户、2:代理"),
            @ApiImplicitParam(name = "orderState", value = "订单状态:不传参数代表所有 1.待付款 2. 已付款 4.已完成")
    })
    @RequestMapping(value = "/queryTowOrder", method = RequestMethod.POST)
    public Result<Page<ShopOrderPO>> queryTowOrder(Integer userId, Integer role, Integer orderState, Paging page){

        return ResultUtil.success(channelTeamService.queryTowOrder(userId,role,orderState,page));
    }

    @ApiOperation("查询所有订单的累计佣金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "设置人id", required = true)
    })
    @RequestMapping(value = "/queryTotalCommission", method = RequestMethod.POST)
    public Result<BigDecimal> queryTotalCommission(Integer userId){

        return ResultUtil.success(channelTeamService.queryTotalCommission(userId));
    }
}
