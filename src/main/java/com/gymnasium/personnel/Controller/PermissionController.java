package com.gymnasium.personnel.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.personnel.PO.Permission;
import com.gymnasium.personnel.Service.PermissionService;
import com.gymnasium.personnel.VO.PermissionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 边书恒
 * @Title: PermissionController
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 13:54
 */
@Api(tags = "功能权限 API")
@RestController
@RequestMapping(value = "/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "添加权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "菜单id"),
            @ApiImplicitParam(name = "name", value = "功能描述"),
            @ApiImplicitParam(name = "permission", value = "控制器名:方法名"),
            @ApiImplicitParam(name = "available", value = "是否可用"),
            @ApiImplicitParam(name = "permissionCol", value = "permissionCol")
    })
    @PostMapping("/insert")
    public Result<Boolean> addPermission(PermissionVO permissionVO) {

        return ResultUtil.success(permissionService.addPermission(permissionVO));
    }

    @ApiOperation(value = "修改权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "pid", required = true),
            @ApiImplicitParam(name = "mid", value = "菜单id"),
            @ApiImplicitParam(name = "name", value = "功能描述"),
            @ApiImplicitParam(name = "permission", value = "控制器名:方法名"),
            @ApiImplicitParam(name = "available", value = "是否可用"),
            @ApiImplicitParam(name = "permissionCol", value = "permissionCol")
    })
    @PutMapping("/update")
    @RequiresPermissions("permission:update")
    public Result<Boolean> updatePermission(PermissionVO permission) {

        return ResultUtil.success(permissionService.updatePermission(permission));
    }

    @ApiOperation(value = "查询权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "菜单id"),
            @ApiImplicitParam(name = "name", value = "功能描述")
    })
    @GetMapping("/get")
    public Result<List<Permission>> selectPermission(PermissionVO permission) {

        return ResultUtil.success(permissionService.selectPermission(permission));
    }

    @ApiOperation(value = "删除权限", notes = "permission")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键")
    })
    @DeleteMapping("/del")
    public Result<Boolean> delPermission(Integer id) {

        return ResultUtil.success(permissionService.delPermission(id));
    }

}
