package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.store.VO.AgentRecommendVO;
import com.gymnasium.store.service.ChartService;
import io.swagger.annotations.*;
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
 * @Date: 2019/6/5 16:29
 * @Description:
 */

@Api(tags = "数据报表 API")
@RestController
@RequestMapping(value = "/chart")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @ApiOperation(value = "一级代理历史推荐图表",
            notes = "date:日期; teamSize: 团队人数; totalCommission:团队总佣金; recommendNumber :总推荐人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "userId", value = "用户id" ,required = true),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/queryAgentChart", method = RequestMethod.POST)
    public Result<List<AgentRecommendVO>> queryAgentChart(Integer userId, String year, String month, Integer type){

        return ResultUtil.success(chartService.queryAgentChart(userId, year, month, type));
    }

    @ApiOperation(value = "商品历史图表",
            notes ="date: 日期; productNum:商品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "productType", value = "商品类型"),
            @ApiImplicitParam(name = "status", value = "商品状态"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/productNumberChart", method = RequestMethod.POST)
    public Result<List<Integer>> productNumberChart(Integer productType, Integer status, String year,String month,
                                            Integer type){

        return ResultUtil.success(chartService.productNumberChart(productType,status,year, month, type));
    }

    @ApiOperation(value = "历史报表信息：一级代理人数/二级人数",
        notes = "oneAgentNum: 一级代理人数; towAgentNum: 二级代理人数;")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "region", value = "地区"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/queryAgentNumber", method = RequestMethod.POST)
    public Result<List> queryAgentNumber(String region,String year,String month, Integer type){

        return ResultUtil.success(chartService.queryAgentNumber(region,year, month,type));
    }

    @ApiOperation(value = "统计搭伙成功人数",
        notes = "date: 日期; partnerRecordNum:搭伙成功人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "promiseTime", value = "约见时段"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/queryBoardRecord", method = RequestMethod.POST)
    public Result<List> queryBoardRecord(String promiseTime,String year,String month, Integer type){

        return ResultUtil.success(chartService.queryBoardRecord(promiseTime,year, month,type));
    }

    @ApiOperation(value = "会员历史消费统计",
        notes = "date: 日期; totalEnergy: 总能量值; freezingEnergy: 冻结能量值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编码", required = true),
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/queryMemberConsumption", method = RequestMethod.POST)
    public Result<List> queryMemberConsumption(String userId, String year, String month,Integer type){

        return ResultUtil.success(chartService.queryMemberConsumption(userId,year, month,type));
    }

    @ApiOperation(value = "门店数统计",notes = "date: 日期; serverPoint: 门店数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityId", value = "地区id"),
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/countGym", method = RequestMethod.POST)
    public Result<List> countGym(Integer cityId, String year, String month,Integer type){

        return ResultUtil.success(chartService.countGymShop(cityId,year, month,type));
    }

    @ApiOperation(value = "总服务点统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityId", value = "地区id"),
    })
    @RequestMapping(value = "/countGymShopNum", method = RequestMethod.POST)
    public Result<Integer> countGymShopNum(Integer cityId){

        return ResultUtil.success(chartService.countGymShopNum(cityId));
    }
    @ApiOperation(value = "按年月日统计转卡次数",notes = "date: 日期; turnCardNum: 转卡次数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编码", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/countTurnCardNumber", method = RequestMethod.POST)
    public Result<List> countTurnCardNumber(String gymShopId, String year, String month,Integer type){

        return ResultUtil.success(chartService.countTurnCardNumber(gymShopId,year, month,type));
    }

    @ApiOperation(value = "按年月日统计健身房转卡人数", notes = "date: 日期; turnCardNumber: 转卡人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编码", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/countGymTurnCard", method = RequestMethod.POST)
    public Result<List> countGymTurnCard(String gymShopId, String year, String month,Integer type){

        return ResultUtil.success(chartService.countGymTurnCard(gymShopId,year, month,type));
    }

    @ApiOperation(value = "总消费使用次数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房编码", required = true)
    })
    @RequestMapping(value = "/countConsumption", method = RequestMethod.POST)
    public Result<Integer> countConsumption(String gymShopId){

        return ResultUtil.success(chartService.countConsumption(gymShopId));
    }

    @ApiOperation(value = "统计转卡总数，转卡总人数",
            notes = "date: 日期; turnCardNumber: 转卡总数, headcount: 转卡总人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房编码", required = true)
    })
    @RequestMapping(value = "/countGymTurnCardNumber", method = RequestMethod.POST)
    public Result<Map<String,Object>> countGymTurnCardNumber(String gymShopId){

        return ResultUtil.success(chartService.countGymTurnCardNumber(gymShopId));
    }

    @ApiOperation(value = "搭伙信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页"),
            @ApiImplicitParam(name = "pageSize", value = "页容量"),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编码"),
            @ApiImplicitParam(name = "userId", value = "本人编号 或者 搭伙人编号")
    })
    @RequestMapping(value = "/pagePartnerRecord", method = RequestMethod.POST)
    public Result<Page<PartnerRecordPO>> pagePartnerRecord(String gymShopId, String userId, Paging paging){

        return ResultUtil.success(chartService.pagePartnerRecord(gymShopId,userId, paging));
    }

    @ApiOperation(value = "搭伙服务点匹配总数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gymShopId", value = "健身房编码")
    })
    @RequestMapping(value = "/countPartnerRecord", method = RequestMethod.POST)
    public Result<Integer> countPartnerRecord(String gymShopId){

        return ResultUtil.success(chartService.countPartnerRecord(gymShopId));
    }

    @ApiOperation(value = "搭伙服务点匹配数年月日分组查询",notes = "date: 日期; matchingNumber: 匹配数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编码", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/countServicePoint", method = RequestMethod.POST)
    public Result<List> countServicePoint(String gymShopId, String year, String month,Integer type){

        return ResultUtil.success(chartService.countServicePoint(gymShopId,year,month,type));
    }

    @ApiOperation(value = "用户搭伙数据统计(总匹配数/成功数)",
            notes = "matchingNumber: 总匹配数; partnerRecordNum: 搭伙成功数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "本人编号 或者 搭伙人编号")
    })
    @RequestMapping(value = "/countUserPartnerRecord", method = RequestMethod.POST)
    public Result<Map<String,Object>> countUserPartnerRecord(String userId){

       return ResultUtil.success(chartService.countUserPartnerRecord(userId));
    }

    @ApiOperation(value = "用户搭伙数据按年月日统计",
            notes = "date: 日期; matchingNumber: 总匹配数; partnerRecordNum: 搭伙成功数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年", required = true),
            @ApiImplicitParam(name = "userId", value = "本人编号 或者 搭伙人编号", required = true),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "type", value = "类型:1.日/2.周/3.月/4.季/5.年", required = true)
    })
    @RequestMapping(value = "/countPartnerRecordChart", method = RequestMethod.POST)
    public Result<List> countPartnerRecordChart(String userId, String year, String month,Integer type){

        return ResultUtil.success(chartService.countPartnerRecordChart(userId, year, month, type));
    }

    @ApiOperation(value = "查询客服历史评论总数")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/countServiceComment", method = RequestMethod.POST)
    public Result<Integer> countServiceComment(Integer serviceId){

        return ResultUtil.success(chartService.countServiceComment(serviceId));
    }
}
