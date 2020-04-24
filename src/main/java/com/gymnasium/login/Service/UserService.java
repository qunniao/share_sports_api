package com.gymnasium.login.Service;

import com.gymnasium.personnel.PO.*;

import java.util.List;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 12:54
 **/
public interface UserService {

    UserManagePO queryUserByUserId(String userId);

    List<Role> queryRoleByRid(int rid);

    Role addRole(Role role);

    UserRole addUserRole(UserRole userRole);

    Permission addPermission(Permission permission);

    RolePermission addRolePermission(RolePermission rolePermission);
}
