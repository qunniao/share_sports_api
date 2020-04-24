package com.gymnasium.data.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.data.PO.CityPO;
import com.gymnasium.data.PO.UserTypePO;
import com.gymnasium.data.Service.CityService;
import com.gymnasium.data.Service.DataService;
import com.gymnasium.stadium.VO.GymShopVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 王志鹏
 * @title: DataController
 * @projectName baoge
 * @description: TODO
 * @date 2019/3/21 15:08
 */

@Api(tags = "获取系统通用数据:如全国地址信息/人员类型")
@RestController
@RequestMapping(value = "/data")
public class DataController {


    @Autowired
    private CityService cityService;

    @Autowired
    private DataService dataService;


    @ApiOperation("查询全部中国地址信息")
    @RequestMapping(value = "/queryAllCity", method = RequestMethod.POST)
    public Result<List<CityPO>> queryAllCity() {

        return ResultUtil.success(cityService.queryAllCity());
    }

    @ApiOperation("根据等级查询中国地址信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "level", value = "等级 1省,2市,3区", required = true)
    })
    @RequestMapping(value = "/queryCityByLevel", method = RequestMethod.POST)
    public Result<List<CityPO>> queryCityByLevel(@RequestParam("level") Short level) {

        return ResultUtil.success(cityService.queryCityByLevel(level));
    }

    @ApiOperation("根据分级名称模糊查询中国地址信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mergerName", value = "全称", required = true),
            @ApiImplicitParam(name = "level", value = "等级 1省,2市,3区", required = true)
    })
    @RequestMapping(value = "/queryCityByLikeMergerName", method = RequestMethod.POST)
    public Result<List<CityPO>> queryCityByLikeMergerName(@RequestParam("mergerName") String mergerName, @RequestParam("level") Short level) {

        return ResultUtil.success(cityService.queryCityByLikeMergerName(mergerName, level));
    }

    @ApiOperation("根据pid查询地址信息")
    @ApiImplicitParam(name = "pid", value = "父级id", required = true)
    @RequestMapping(value = "/queryCityByPid", method = RequestMethod.POST)
    public Result<List<CityPO>> queryCityByPid(@RequestParam("pid") Integer pid) {

        return ResultUtil.success(cityService.queryCityByPid(pid));
    }

    @ApiOperation(value = "根据id查询地址信息和上级的id",notes ="cityPO：地址信息, superior:上级id; secondLevel: 上上级id" )
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @RequestMapping(value = "/queryCityById", method = RequestMethod.POST)
    public Result<Map<String, Object>> queryCityById(Integer id) {

        return ResultUtil.success(cityService.queryCityById(id));
    }

    @ApiOperation("根据type添加用户选项数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", required = true),
            @ApiImplicitParam(name = "name", value = "名称", required = true)
    })
    @RequestMapping(value = "/addUserType", method = RequestMethod.POST)
    public Result<UserTypePO> addUserType(UserTypePO userTypePO) {

        return ResultUtil.success(dataService.addUserType(userTypePO));
    }

    @ApiOperation("根据typec查询用户选项数据")
    @RequestMapping(value = "/queryUserTypeALL", method = RequestMethod.POST)
    public Result<List<UserTypePO>> queryUserTypeALL() {

        return ResultUtil.success(dataService.findAll());
    }

    @ApiOperation("解密二维码,获得店家数据")
    @ApiImplicitParam(name = "sign", value = "二维码code", required = true)
    @RequestMapping(value = "/decryptQRCode", method = RequestMethod.POST)
    public Result<GymShopVO> decryptQRCode(@RequestParam String sign) {

        return ResultUtil.success(dataService.decryptQRCode(sign));
    }
}

