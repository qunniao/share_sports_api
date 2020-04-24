package com.gymnasium.information.Controller;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.vo.DateVo;
import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.MatchRecord;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.information.PO.PartnerpoolPO;
import com.gymnasium.information.Service.PartnerService;
import com.gymnasium.information.VO.PartnerUserVO;
import com.gymnasium.information.VO.PartnerpoolVO;
import com.gymnasium.personnel.PO.UserPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author 王志鹏
 * @title: PartnerController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/18 13:58
 */
@Api(tags = "搭伙API")
@RestController
@RequestMapping(value = "/partner")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;


    @ApiOperation(value = "查询好友列表")
    @ApiImplicitParam(name = "userId", value = "本人userId", required = true)
    @RequestMapping(value = "/queryGoodFriendList", method = RequestMethod.POST)
    public Result<List<UserPO>> queryGoodFriendList(@RequestParam String userId) throws SCException {

        return ResultUtil.success(partnerService.queryGoodFriendList(userId));
    }

    @ApiOperation(value = "查询搭伙记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aUserId", value = "本人userId", required = true),
            @ApiImplicitParam(name = "buserId", value = "本人userId", required = true)
    })
    @RequestMapping(value = "/queryByABuserId", method = RequestMethod.POST)
    public Result<List<PartnerRecordPO>> queryByAuserId(@RequestParam String auserId, @RequestParam String buserId) {


        return ResultUtil.success(partnerService.queryByABuserId(auserId, buserId));
    }

    @ApiOperation(value = "分页查询搭伙记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号")
    })
    @RequestMapping(value = "/pagePartnerRecord", method = RequestMethod.POST)
    public Result<Page<PartnerRecordPO>> pagePartnerRecord(Paging page, String userId) {

        return ResultUtil.success(partnerService.PagePartnerRecord(page, userId));
    }


    @ApiOperation(value = "根据查询条件,获得匹配到搭伙列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "请固定给5", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @RequestMapping(value = "/pagePartnerpool", method = RequestMethod.POST)
    public Result<List<PartnerUserVO>> pagePartnerpool(Paging paging, @ApiIgnore("PartnerpoolVO") PartnerpoolVO partnerpoolVO) {

        return ResultUtil.success(partnerService.pagePartnerpool(paging, partnerpoolVO));
    }

    @ApiOperation(value = "保存搭伙计划", notes = "如果人员已经存在池中将不会重复添加,然后配合查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime1", value = "开始时间1", required = true),
            @ApiImplicitParam(name = "startTime2", value = "开始时间2", required = true),
            @ApiImplicitParam(name = "startTime3", value = "开始时间3", required = true),
            @ApiImplicitParam(name = "startTime4", value = "开始时间4", required = true),
            @ApiImplicitParam(name = "endTime1", value = "结束时间1", required = true),
            @ApiImplicitParam(name = "endTime2", value = "结束时间2", required = true),
            @ApiImplicitParam(name = "endTime3", value = "结束时间3", required = true),
            @ApiImplicitParam(name = "endTime4", value = "结束时间4", required = true),
            @ApiImplicitParam(name = "regionId", value = "区域 城市表主键,要具体城市的就可以了", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "purpose", value = "健身目主键", required = true),
            @ApiImplicitParam(name = "body", value = "体型主键", required = true),
            @ApiImplicitParam(name = "gender", value = "性别0男1女", required = true),
            @ApiImplicitParam(name = "startAge", value = "最小年龄"),
            @ApiImplicitParam(name = "endAge", value = "最大年龄"),
            @ApiImplicitParam(name = "figure", value = "身材主键", required = true),
            @ApiImplicitParam(name = "level", value = "等级", required = true),
            @ApiImplicitParam(name = "job", value = "职业类型主键", required = true),
            @ApiImplicitParam(name = "income", value = "收入类型主键", required = true),
    })
    @RequestMapping(value = "/addPartnerpool", method = RequestMethod.POST)
    public Result<PartnerpoolPO> addPartnerpool(PartnerpoolPO partnerpoolPO) {

        return ResultUtil.success(partnerService.addPartnerpool(partnerpoolPO));
    }

    @ApiOperation(value = "添加好友")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auerId", value = "本人userId", required = true),
            @ApiImplicitParam(name = "buserId", value = "需要添加的人的userId", required = true)
    })
    @RequestMapping(value = "/addGoogFriend", method = RequestMethod.POST)
    public Result<Boolean> addGoogFriend(@RequestParam String auerId, @RequestParam String buserId) throws SCException {

        return ResultUtil.success(partnerService.addGoogFriend(auerId, buserId));
    }

    @ApiOperation(value = "添加搭伙记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auserId", value = "本人userId", required = true),
            @ApiImplicitParam(name = "buserId", value = "需要添加的人的userId", required = true),
            @ApiImplicitParam(name = "time", value = "开始时间", required = true),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true),
            @ApiImplicitParam(name = "payType", value = "支付类型", required = true),
            @ApiImplicitParam(name = "cityName", value = "地区名称", required = true),
            @ApiImplicitParam(name = "gymShopId", value = "健身房id", required = true),
            @ApiImplicitParam(name = "promiseTime", value = "约定时间", required = true),
            @ApiImplicitParam(name = "remark", value = "搭伙记录信息,前台拼接字符串如: a和b 在xxx天要去xxx")
    })
    @RequestMapping(value = "/addPartnerRecord", method = RequestMethod.POST)
    public Result<Integer> addPartnerRecord(PartnerRecordPO partnerRecordPO, String time) throws SCException {

        return ResultUtil.success(partnerService.addPartnerRecord(partnerRecordPO, time));
    }

    @ApiOperation(value = "查询用户搭伙计划")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    @RequestMapping(value = "/queryPartnerpoolByUserId", method = RequestMethod.POST)
    public Result<PartnerpoolPO> queryPartnerpoolByUserId(String userId) {

        System.err.println(userId);

        return ResultUtil.success(partnerService.queryPartnerpoolByUserId(userId));
    }

    @ApiOperation(value = "同意搭伙")
    @ApiImplicitParam(name = "id", value = "搭伙记录id", required = true)
    @RequestMapping(value = "/partnerYES", method = RequestMethod.POST)
    public Result<PartnerpoolPO> partnerYES(@RequestParam int id) {

        return ResultUtil.success(partnerService.partnerYES(id));
    }

    @ApiOperation(value = "拒绝搭伙")
    @ApiImplicitParam(name = "id", value = "搭伙记录id", required = true)
    @RequestMapping(value = "/partnerNO", method = RequestMethod.POST)
    public Result<PartnerpoolPO> partnerNO(@RequestParam int id) {

        return ResultUtil.success(partnerService.partnerNO(id));
    }

    @ApiOperation(value = "停止搭伙")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    @RequestMapping(value = "/partnerSTOP", method = RequestMethod.POST)
    public Result<PartnerpoolPO> partnerSTOP(@RequestParam String userId) {

        return ResultUtil.success(partnerService.partnerSTOP(userId));
    }

    @ApiOperation(value = "查询搭伙匹配记录")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    @PostMapping("/queryMatchRecord")
    public Result<MatchRecord> queryMatchRecord(@RequestParam Integer userId) {

        if (ObjectUtil.isNull(userId)){
            return ResultUtil.error(ResultEnum.INCOMPLETE_PARAMETER);
        }

        return ResultUtil.success(partnerService.queryMatchRecord(userId));
    }

    @ApiOperation(value = "后台查询搭伙记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "auserId", value = "用户编号"),
            @ApiImplicitParam(name = "buserId", value = "用户编号"),
            @ApiImplicitParam(name = "gymShopId", value = "用户编号"),
            @ApiImplicitParam(name = "year", value = "年"),
            @ApiImplicitParam(name = "month", value = "月"),
            @ApiImplicitParam(name = "day", value = "日"),
    })
    @RequestMapping(value = "/queryPartnerRecord", method = RequestMethod.POST)
    public Result<Page<PartnerRecordPO>> queryPartnerRecord(Paging page, PartnerRecordPO partnerRecordPO, @Validated DateVo dateVo) {

        return ResultUtil.success(partnerService.queryPartnerRecord(page, partnerRecordPO, dateVo));
    }
}