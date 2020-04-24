package com.gymnasium.system.Controller;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.system.PO.SysMemberPO;
import com.gymnasium.system.PO.SysMenberCityPO;
import com.gymnasium.system.Service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 王志鹏
 * @title: MemberController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 17:05
 */

@Api(tags = "系统会员卡设置")
@RestController
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @ApiOperation("添加系统会员卡佣金设置")
    @ApiImplicitParam(name = "mid", value = "会员卡表主键", required = true)
    @RequestMapping(value = "/addSysMenberCity", method = RequestMethod.POST)
    public Result<Boolean> addSysMenberCity(SysMenberCityPO sysMenberCityPO) {


        return ResultUtil.success(memberService.addSysMenberCity(sysMenberCityPO));
    }

    @ApiOperation("根据城市名+会员卡主键 查询佣金设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "会员卡表主键", required = true)
    })
    @RequestMapping(value = "/queryByCityNameLikeAndMid", method = RequestMethod.POST)
    public Result<SysMenberCityPO> queryByCityNameLikeAndMid(@RequestParam String cityName, @RequestParam int mid) {


        return ResultUtil.success(memberService.queryByCityNameLikeAndMid(cityName, mid));
    }

    @ApiOperation("查询全部会员卡")
    @ApiImplicitParam(name = "mid", value = "会员卡表主键", required = true)
    @RequestMapping(value = "/querySysMemberAll", method = RequestMethod.POST)
    public Result<List<SysMemberPO>> querySysMemberAll() {

        return ResultUtil.success(memberService.querySysMemberAll());
    }

    @ApiOperation("查询全部会员卡佣金设置")
    @RequestMapping(value = "/querySysMenberCityAll", method = RequestMethod.POST)
    public Result<List<SysMenberCityPO>> querySysMenberCityAll() {

        return ResultUtil.success(memberService.querySysMenberCityAll());
    }

    @ApiOperation("更新会员卡佣金设置")
    @RequestMapping(value = "/updateSysMenberCity", method = RequestMethod.POST)
    public Result<Boolean> updateSysMenberCity(SysMenberCityPO sysMenberCityPO) {

        return ResultUtil.success(memberService.updateSysMenberCity(sysMenberCityPO));
    }

    @ApiOperation("添加会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "level", value = "会员等级,1体验,2大众,3精英,4皇家", required = true),
            @ApiImplicitParam(name = "price", value = "价格", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "energy", value = "能量值", required = true),
            @ApiImplicitParam(name = "peopleNumber", value = "可使用人数"),
            @ApiImplicitParam(name = "averageNumber", value = "平均健身房使用次数"),
            @ApiImplicitParam(name = "privilegeId", value = "多个特权券id：1,2,3"),
            @ApiImplicitParam(name = "giftId", value = "多个礼包券id"),
            @ApiImplicitParam(name = "content", value = "描述"),
            @ApiImplicitParam(name = "spare", value = "备用字段,功能权限")
    })
    @RequestMapping(value = "/insertMember", method = RequestMethod.POST)
    public Result<Boolean> insertMember(MultipartFile file, SysMemberPO sysMemberPO)  {

        return ResultUtil.success(memberService.insertMember(file, sysMemberPO));
    }

    @ApiOperation("修改会员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员主键id"),
            @ApiImplicitParam(name = "level", value = "会员等级,1体验,2大众,3精英,4皇家", required = true),
            @ApiImplicitParam(name = "price", value = "价格", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "energy", value = "能量值", required = true),
            @ApiImplicitParam(name = "peopleNumber", value = "可使用人数"),
            @ApiImplicitParam(name = "averageNumber", value = "平均健身房使用次数"),
            @ApiImplicitParam(name = "privilegeId", value = "多个特权券id：1,2,3"),
            @ApiImplicitParam(name = "giftId", value = "多个礼包券id"),
            @ApiImplicitParam(name = "content", value = "描述"),
            @ApiImplicitParam(name = "spare", value = "备用字段,功能权限")
    })
    @RequestMapping(value = "/updateMember", method = RequestMethod.POST)
    public Result<Boolean> updateMember(MultipartFile file, SysMemberPO sysMemberPO)  {

        return ResultUtil.success(memberService.updateMember(file, sysMemberPO));
    }
    @ApiOperation("删除会员信息")
    @RequestMapping(value = "/deleteMember", method = RequestMethod.POST)
    @ApiImplicitParam(name = "id", value = "会员主键id")
    public Result<Boolean> deleteMember(Integer id) {

        return ResultUtil.success(memberService.deleteMember(id));
    }

    @ApiOperation("查询会员信息")
    @RequestMapping(value = "/querySysMember", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    public Result querySysMember(Integer userId) {

        return ResultUtil.success(memberService.querySysMember(userId));
    }
}