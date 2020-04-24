package com.gymnasium.login.Service;

import com.gymnasium.personnel.PO.*;
import com.gymnasium.personnel.VO.UserManageVO;

import java.util.List;
import java.util.Optional;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 12:54
 **/
public interface UserManageService {

    UserManagePO queryUserByUserId(String userId);

    /**
     * 根据管理员角色查询功能权限
     * @param rId
     * @return
     */
    Optional<Role> findAllByRid(Integer rId);

    List<Role> queryRoleByRid(int rid);

    Role addRole(Role role);

    UserRole addUserRole(UserRole userRole);

    Permission addPermission(Permission permission);

    RolePermission addRolePermission(RolePermission rolePermission);

    Boolean addUserManage(UserManageVO userManageVO);
}
