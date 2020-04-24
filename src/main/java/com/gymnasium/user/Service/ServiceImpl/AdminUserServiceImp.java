package com.gymnasium.user.Service.ServiceImpl;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.personnel.Dao.*;
import com.gymnasium.personnel.PO.*;
import com.gymnasium.user.Service.AdminUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminUserServiceImp implements AdminUserService {

    @Autowired
    private UserManageDao userManageDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Result selectUserManage() {
        List<UserManagePO> all = userManageDao.findAll();
        return ResultUtil.success(all);
    }

    @Override
    public Result deleteUserManageByUid(Integer uid) {
        userManageDao.deleteById(uid);
        return ResultUtil.success();
    }

    @Override
    public Result addUserRole(Integer uid, Integer rid, String pid) {
        Optional<UserManagePO> byId = userManageDao.findById(uid);
        if (byId.isPresent()) {
            Role allByRid = roleDao.findAllByRid(rid);
            if (allByRid != null) {
                UserRole userRole = new UserRole();
                userRole.setRid(rid);
                userRole.setUid(uid);
                userRoleDao.save(userRole);

                // 添加权限
                if (StrUtil.isNotBlank(pid)){

                    String[] split = StrUtil.split(pid, ",");
                    if (ArrayUtil.isNotEmpty(split)){

                        List<RolePermission> list = new LinkedList<>();
                        for (String str : split) {
                            RolePermission rolePermission = new RolePermission();
                            rolePermission.setPid(Integer.parseInt(str));
                            rolePermission.setRid(rid);
                            list.add(rolePermission);
                        }
                        rolePermissionDao.saveAll(list);
                    }

                }

                return ResultUtil.success();
            }
        }
        return ResultUtil.error(444, "数据库不存在这条数据");
    }

    @Override
    @Transactional
    public Result updateUserRole(Integer urid, Integer uid, Integer rid) {
        userRoleDao.updateUserRole(urid, uid, rid);
        return ResultUtil.success();
    }

    @Override
    public Result findAllRole() {
        List<Role> all = roleDao.findAll();
        return ResultUtil.success(all);
    }

    @Override
    public Result deleteRoleByRid(Integer rid) {
        roleDao.deleteById(rid);
        return ResultUtil.success();
    }

    @Override
    public Result addRole(Role role) {
        roleDao.save(role);
        return ResultUtil.success();
    }

    @Override
    @Transactional
    public Result updatePassword(Integer uid, String newPassword) {
        UserManagePO saltByUid = userManageDao.findSaltByUid(uid);
        String salt = saltByUid.getSalt();
        if (salt != null) {
            String newMd5 = new SimpleHash("md5", newPassword, ByteSource.Util.bytes(salt), 1).toHex();
            userManageDao.updatePassword(uid, newMd5);
            return ResultUtil.success();
        }
        return ResultUtil.error(444, "修改失败");
    }

    @Override
    public Result updateUser(UserPO userPO) {

        UserPO user = userDao.getOne(userPO.getUid());

        if (user == null) {
            ResultUtil.error(444, "用户不存在");
        }

        if (StrUtil.isNotBlank(userPO.getUserId())) {

            UserPO byUserId = userDao.findByUserId(userPO.getUserId());

            if (ObjectUtil.isNotNull(byUserId)) {
                ResultUtil.error(ResultEnum.USER_NOT_NULL);
            }
        }

        BeanUtil.copyProperties(userPO, user);

        userDao.save(user);

        return ResultUtil.success();
    }

}
