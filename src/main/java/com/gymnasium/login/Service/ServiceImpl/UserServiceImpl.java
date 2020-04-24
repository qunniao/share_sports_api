package com.gymnasium.login.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.SCException;
import com.gymnasium.login.Service.UserManageService;
import com.gymnasium.personnel.Dao.*;
import com.gymnasium.personnel.PO.*;
import com.gymnasium.personnel.VO.UserManageVO;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 12:56
 **/

@Service
public class UserServiceImpl implements UserManageService {

    @Autowired
    private UserManageDao userManageDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;


    @Override
    public UserManagePO queryUserByUserId(String userId) {

        return userManageDao.queryByUserId(userId);
    }

    @Override
    public Optional<Role> findAllByRid(Integer rId) {

        return roleDao.findById(rId);
    }

    @Override
    public List<Role> queryRoleByRid(int rid) {
//        Role role = new Role();
//        role.setRid(rid);
//        Example<Role> example = Example.of(role);
//        return roleDao.findOne(example);
        return roleDao.findAll();
    }

    @Override
    public Role addRole(Role role) {

        return roleDao.save(role);
    }

    @Override
    public UserRole addUserRole(UserRole userRole) {

        return userRoleDao.save(userRole);
    }

    @Override
    public Permission addPermission(Permission permission) {

        return permissionDao.save(permission);
    }

    @Override
    public RolePermission addRolePermission(RolePermission rolePermission) {

        return rolePermissionDao.save(rolePermission);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUserManage(UserManageVO userManageVO) {

        String userId = userManageVO.getUserId();

        UserManagePO userManagePO1 = userManageDao.queryByUserId(userId);

        if (ObjectUtil.isNotNull(userManagePO1)){
            throw new SCException(4711, "用户已存在");
        }

        // 加密的盐
        String salt = RandomUtil.randomString(4);

        // 加密之后的密码
        String password = new SimpleHash("md5", userManageVO.getPassWord(), salt, 1).toString();

        UserManagePO userManagePO = userManageDao.queryByUserId(userManageVO.getUserId());

        if (ObjectUtil.isNotNull(userManagePO)){
            throw new SCException(ResultEnum.USER_NOT_NULL);
        }

        userManagePO = new UserManagePO();

        BeanUtil.copyProperties(userManageVO, userManagePO);

        userManagePO.setPassWord(password);
        userManagePO.setSalt(salt);
        userManagePO.setState(1);

        userManageDao.save(userManagePO);

        UserRole userRole = new UserRole();
        userRole.setRid(userManageVO.getRole());
        userRole.setUid(userManagePO.getUid());

        return ObjectUtil.isNotNull(userRoleDao.save(userRole));
    }
}
