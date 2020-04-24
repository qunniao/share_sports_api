package com.gymnasium.user.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.personnel.PO.Role;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.system.Service.MemberService;
import com.gymnasium.user.Service.AdminUserService;
import com.gymnasium.user.bean.RoleBean;
import com.gymnasium.user.util.CopyProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "后台相关")
@RequestMapping(value = "/adminUser")
public class AdminUserController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AdminUserService userService;


    /**
     * 查询所有的UserManage
     *
     * @return
     */
    @ApiOperation(value = "查询所有的后台用户")
    @RequestMapping(value = "/selectUserManage", method = RequestMethod.POST)
    public Result selectUserManage() {
        return userService.selectUserManage();
    }


    /**
     * 根据uid删除userManageByUid
     *
     * @param uid
     * @return
     */
    @ApiOperation(value = "根据uid删除后台用户")
    @RequestMapping(value = "/deleteUserManageByUid", method = RequestMethod.POST)
    public Result deleteUserManageByUid(int uid) {
        return userService.deleteUserManageByUid(uid);
    }

    /**
     * 添加UserRole的记录
     *
     * @param uId
     * @param rId
     * @return
     */
    @ApiOperation(value = "添加用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uId", value = "用户id", required = true),
            @ApiImplicitParam(name = "rId", value = "角色id", required = true),
            @ApiImplicitParam(name = "pIds", value = "多个权限id", example = "1,2,3")
    })
    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    public Result addUserRole(Integer uId, Integer rId, String pIds) {
        return userService.addUserRole(uId, rId, pIds);
    }

    /**
     * 修改userRole记录
     *
     * @param urid
     * @param uid
     * @param rid
     * @return
     */
    @ApiOperation(value = "修改用户角色")
    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
    public Result updateUserRole(int urid, int uid, int rid) {
        return userService.updateUserRole(urid, uid, rid);
    }

    /**
     * 查找所有的Role
     *
     * @return
     */
    @ApiOperation(value = "查询所有角色")
    @RequestMapping(value = "/findAllRole", method = RequestMethod.POST)
    public Result findAllRole() {
        return userService.findAllRole();
    }

    /**
     * 根据rid 删除role
     *
     * @param rid
     * @return
     */
    @ApiOperation(value = "根据rid 删除角色")
    @RequestMapping(value = "/deleteRoleByRid", method = RequestMethod.POST)
    public Result deleteRoleByRid(int rid) {
        return userService.deleteRoleByRid(rid);
    }

    /**
     * 添加一个role
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "添加角色")
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Result addRole(RoleBean role) throws Exception {
        Role temp = new Role();
        CopyProperty.Copy(role, temp);
        return userService.addRole(temp);
    }

    /**
     * 修改一个role
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "修改角色")
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public Result updateRole(RoleBean role) throws Exception {
        Role temp = new Role();
        CopyProperty.Copy(role, temp);
        if (role.getRid() == 0) {
            return ResultUtil.error(444, "rid错误");
        }
        return userService.addRole(temp);
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Result updatePassword(int uid, String newPassword) {
        return userService.updatePassword(uid, newPassword);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "user主键用户", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号"),
            @ApiImplicitParam(name = "userName", value = "用户真实姓名"),
            @ApiImplicitParam(name = "role", value = "角色")
    })
    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Result updateUser(UserPO userPO) {
        return userService.updateUser(userPO);
    }


}