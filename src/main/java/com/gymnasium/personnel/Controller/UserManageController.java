package com.gymnasium.personnel.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.login.Service.UserManageService;
import com.gymnasium.personnel.PO.Role;
import com.gymnasium.personnel.PO.UserManagePO;
import com.gymnasium.personnel.VO.UserManageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author 王志鹏
 * @title: UserManageController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/5 17:42
 */
@Api(tags = "后台用户API")
@RestController
@RequestMapping(value = "/userManage")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    @ApiOperation(value = "查询用户信息", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @RequestMapping(value = "/queryManageUserByUserId", method = RequestMethod.POST)
    public Result<UserManagePO> queryManageUserByUserId(@RequestParam String userId) {

        return ResultUtil.success(userManageService.queryUserByUserId(userId));
    }

    @ApiOperation(value = "根据管理员角色查询功能权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "rId", value = "角色id", required = true)
    })
    @RequestMapping(value = "/findAllByRid", method = RequestMethod.POST)
    public Result<Optional<Role>> findAllByRid(Integer rId) {

        return ResultUtil.success(userManageService.findAllByRid(rId));
    }

    @ApiOperation(value = "后台管理员注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "userId", value = "账号", required = true),
            @ApiImplicitParam(name = "passWord", value = "密码", required = true),
            @ApiImplicitParam(name = "role", value = "用户身份", required = true),
            @ApiImplicitParam(name = "userName", value = "用户昵称")
    })
    @RequestMapping(value = "/addUserManage", method = RequestMethod.POST)
    public Result<Boolean> addUserManage(UserManageVO userManageVO) {
        return ResultUtil.success( userManageService.addUserManage(userManageVO));
    }
}
