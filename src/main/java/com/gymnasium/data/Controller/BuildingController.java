package com.gymnasium.data.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.data.Dao.BuildingDao;
import com.gymnasium.data.Dao.SubjectDao;
import com.gymnasium.data.PO.BuildingPO;
import com.gymnasium.data.PO.SubjectPO;
import com.gymnasium.data.Service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王志鹏
 * @title: BuildingDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 17:17
 */
@Api("通用数据,如类型等")
@RestController
@RequestMapping(value = "/datab")
public class BuildingController {

    @Autowired
    private DataService dataService;

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private SubjectDao subjectDao;

    @ApiOperation("查询全部场地信息")
    @RequestMapping(value = "/queryBuildingAll", method = RequestMethod.POST)
    public Result<List<BuildingPO>> queryBuildingAll() {

        return ResultUtil.success(buildingDao.findAll());
    }

    @ApiOperation("查询全部场地信息")
    @RequestMapping(value = "/querySubjectAll", method = RequestMethod.POST)
    public Result<List<SubjectPO>> querySubjectAll() {

        return ResultUtil.success(subjectDao.findAll());
    }

    @ApiOperation("添加场地设施")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "name", required = true),
            @ApiImplicitParam(name = "type", value = "type", required = true)
    })
    @RequestMapping(value = "/addBuilding", method = RequestMethod.POST)
    public Result<BuildingPO> addBuilding(BuildingPO buildingPO) {

        return ResultUtil.success(dataService.addBuilding(buildingPO));
    }

    @ApiOperation("添加科目类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "name", required = true)
    })
    @RequestMapping(value = "/addSubject", method = RequestMethod.POST)
    public Result<SubjectPO> addSubject(SubjectPO subjectPO) {
        return ResultUtil.success(dataService.addSubject(subjectPO));
    }


    @ApiOperation("编辑场地")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bid", value = "场地Id", required = true),
            @ApiImplicitParam(name = "name", value = "名称"),
            @ApiImplicitParam(name = "url", value = "图片路径"),
            @ApiImplicitParam(name = "urly", value = "选中图片路径"),
            @ApiImplicitParam(name = "type", value = "0场地设施,1服务设施")
    })
    @RequestMapping(value = "/updateDataBuilding", method = RequestMethod.POST)
    public Result<SubjectPO> updateDataBuilding(BuildingPO buildingPO) {
        return dataService.updateDataBuilding(buildingPO);
    }

    @ApiOperation("修改课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "主键", required = true),
            @ApiImplicitParam(name = "name", value = "课程名称"),
            @ApiImplicitParam(name = "url", value = "图片路径"),
            @ApiImplicitParam(name = "urly", value = "选中图片路径")
    })
    @RequestMapping(value = "/updateDataSubject", method = RequestMethod.POST)
    public Result updateDataSubject(SubjectPO subjectPO) {
        return dataService.updateDataSubject(subjectPO);
    }


}
