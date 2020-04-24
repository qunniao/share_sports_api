package com.gymnasium.personnel.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.personnel.PO.Permission;
import com.gymnasium.personnel.PO.RolePermission;
import com.gymnasium.personnel.Service.PermissionService;
import com.gymnasium.personnel.Service.RolePermissionService;
import com.gymnasium.personnel.VO.RolePermissionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 边书恒
 * @Title: RolePermissionController
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 16:24
 */

@Api(tags = "角色和权限 API")
@RestController
@RequestMapping(value = "/rolePermissions")
public class RolePermissionController {


    @Autowired
    private RolePermissionService rolePermissionService;

    @ApiOperation(value = "添加角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "角色id", required = true),
            @ApiImplicitParam(name = "pid", value = "权限id",  required = true)
    })
    @PostMapping("/insert")
    public Result<Boolean> addPermission(RolePermissionVO rolePermission) {

        return ResultUtil.success(rolePermissionService.addRolePermission(rolePermission));
    }

    @ApiOperation(value = "删除角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @DeleteMapping("/del")
    public Result<Boolean> delPermission(Integer id) {

        return ResultUtil.success(rolePermissionService.delRolePermission(id));
    }


}
