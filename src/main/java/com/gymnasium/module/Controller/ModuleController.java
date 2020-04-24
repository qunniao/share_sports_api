package com.gymnasium.module.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.module.PO.ModulePO;
import com.gymnasium.module.Service.ModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王志鹏
 * @title: ModuleController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/9 10:12
 */

@Api(tags = "后台管理页面列表渲染用户API")
@RestController
@RequestMapping(value = "/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @ApiOperation("获得页面列表数据")
    @RequestMapping(value = "/findAllModule", method = RequestMethod.POST)
    public List<ModulePO> findAllModule() {

        return moduleService.findAll();
    }

    @ApiOperation("根据用户和角色获得页面列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "userPhone", value = "用户电话", required = true)
    })
    @RequestMapping(value = "/findMoudelByUsreId", method = RequestMethod.POST)
    public List<ModulePO> findMoudelByUsreId(String userId) {
        return moduleService.findMoudelByUsreId(userId);
    }

    /**
     * 修改module_role表,
     *
     * @param rid,mid
     * @return
     */
    @ApiOperation("修改菜单角色")
    @RequestMapping(value = "/updateModuleByRid", method = RequestMethod.POST)
    public Result updateModuleByRid(Integer rid, @RequestParam("mid") List<Integer> mid) {
        return moduleService.updateModuleByRid(rid, mid);
    }

    @ApiOperation("添加菜单")
    @RequestMapping(value = "/addModule", method = RequestMethod.POST)
    public Result addModule(@RequestBody ModulePO modulePO) {
        return moduleService.addModule(modulePO);
    }

    @ApiOperation("删除菜单")
    @RequestMapping(value = "/deleteModule", method = RequestMethod.POST)
    public Result deleteModule(int mid) {
        return moduleService.deleteModule(mid);
    }

    @ApiOperation("修改菜单")
    @RequestMapping(value = "/updateModule", method = RequestMethod.POST)
    public Result updateModule(@RequestBody ModulePO modulePO) {
        return moduleService.updateModule(modulePO);
    }

    @ApiOperation("查询所有菜单的功能权限")
    @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
    public List<ModulePO> queryAll() {

        return moduleService.queryAll();
    }
}