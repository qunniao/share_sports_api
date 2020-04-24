package com.gymnasium.login.Controller;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.personnel.Dao.UserManageDao;
import com.gymnasium.personnel.Dao.UserRoleDao;
import com.gymnasium.personnel.PO.UserManagePO;
import com.gymnasium.personnel.PO.UserRole;
import com.gymnasium.personnel.VO.UserManageVO;
import com.gymnasium.store.Dao.ServicePersonnelDao;
import com.gymnasium.store.PO.ServicePersonnelPO;
import com.gymnasium.store.dto.ServicePersonnelDto;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 16:29
 **/
@Api("后台管理登录")
@RestController
@RequestMapping(value = "/log")
public class loginController {

    @Autowired
    private UserManageDao userManageDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private ServicePersonnelDao servicePersonnelDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<UserManagePO> login(@RequestParam String userId, @RequestParam String password) {

        UserManagePO userManage = null;
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userId, password);

            SecurityUtils.getSubject().login(token);

            userManage = userManageDao.queryByUserId(userId);

            System.out.println("登陆成功");

        } catch (UnknownAccountException e) {
            throw new SCException(1111, "账号不存在");
        } catch (IncorrectCredentialsException e) {
            throw new SCException(2222, "密码不正确");
        } catch (Exception exception) {
            System.out.println("else -- >" + exception);
        }

        Integer uid = userManage.getUid();

        UserRole userRole = userRoleDao.findByUid(uid);

        if (ObjectUtil.isNull(userRole)){
            throw new SCException(11, "用户角色不存在着");
        }

        if (userRole.getRid() == 7){
            ServicePersonnelDto servicePersonnelDto = new ServicePersonnelDto();

            UserManageVO userManageVO = new UserManageVO();

            ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.findByManagerId(uid);

            BeanUtil.copyProperties(servicePersonnelPO, servicePersonnelDto);
            BeanUtil.copyProperties(userManage, userManageVO);

            servicePersonnelDto.setUserManageVO(userManageVO);

            return ResultUtil.success(servicePersonnelDto);
        }

        return ResultUtil.success(userManage);
    }

    @GetMapping("/loginUrl")
    public Result loginUrl(){

        return ResultUtil.error(1,"用户未登录");
    }

    @GetMapping("/index")
    public Result indexUrl(){

        return ResultUtil.success("登录成功");
    }

    @GetMapping("/unauthorizedUrl")
    public Result UnauthorizedUr(){

        return ResultUtil.error(2,"用户没有权限");
    }
}
