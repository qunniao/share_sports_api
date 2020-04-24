package com.gymnasium.personnel.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.personnel.PO.CoachUserPO;
import com.gymnasium.personnel.Service.CoachUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王志鹏
 * @title: CoachUserController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/22 10:08
 */
@CrossOrigin(maxAge = 3600)
@Api(tags = "健身房教练")
@RestController
@RequestMapping(value = "/coachuser")
public class CoachUserController {

    @Autowired
    private CoachUserService coachUserService;

    @ApiOperation(value = "教练完善用户信息,更新用户信息", notes = "跳过更新以下重要字段uid,userId,energy,openid,level")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "expertise", value = "专长", required = true),
            @ApiImplicitParam(name = "resume", value = "履历", required = true)
    })
    @RequestMapping(value = "/addCoachUser", method = RequestMethod.POST)
    public Result<CoachUserPO> addCoachUser(CoachUserPO coachUserPO) {

        return ResultUtil.success(coachUserService.addCoachUser(coachUserPO));
    }


    @ApiOperation(value = "查询健身下房所有教练信息", notes = "无")
    @ApiImplicitParam(name = "gymShopId", value = "健身房编号", required = true)
    @RequestMapping(value = "/queryCoachUserByGymShopId", method = RequestMethod.POST)
    public Result<List<CoachUserPO>> queryCoachUserByGymShopId(@RequestParam("gymShopId") String gymShopId) {

        return ResultUtil.success(coachUserService.queryCoachUserByGymShopId(gymShopId));
    }

    @ApiOperation(value = "查询教练信息", notes = "无")
    @ApiImplicitParam(name = "userId", value = "教练编号", required = true)
    @RequestMapping(value = "/queryCoachUserByUserId", method = RequestMethod.POST)
    public Result<CoachUserPO> queryCoachUserByUserId(@RequestParam("userId") String userId) {

        return ResultUtil.success(coachUserService.queryCoachUserByUserId(userId));
    }


    @ApiOperation(value = "删除教练", notes = "无")
    @ApiImplicitParam(name = "id", value = "教练Id", required = true)
    @RequestMapping(value = "/deleteCoachUserById", method = RequestMethod.POST)
    public Result<Object> deleteCoachUserById(Integer coachUserId) {
        return coachUserService.deleteCoachUserById(coachUserId);
    }

    @ApiOperation(value = "修改教练信息", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "教练Id", required = true),
            @ApiImplicitParam(name = "userId", value = "编号"),
            @ApiImplicitParam(name = "gymShopId", value = "健身房编号"),
            @ApiImplicitParam(name = "title", value = "职业说明"),
            @ApiImplicitParam(name = "jobYear", value = "从业年限"),
            @ApiImplicitParam(name = "goodat", value = "擅长"),
            @ApiImplicitParam(name = "name", value = "教练名称"),
            @ApiImplicitParam(name = "resume", value = "履历"),
            @ApiImplicitParam(name = "expertise", value = "专长"),
            @ApiImplicitParam(name = "url", value = "头像链接")
    })
    @RequestMapping(value = "/updateCoachUserById", method = RequestMethod.POST)
    public Result<Boolean> updateCoachUserById(CoachUserPO coachUserPO) {
        return coachUserService.updateCoachUserById(coachUserPO);
    }

}
