package com.gymnasium.user.Service;


import com.gymnasium.Util.PO.Result;
import com.gymnasium.personnel.PO.Role;
import com.gymnasium.personnel.PO.UserPO;

public interface AdminUserService {

    /**
     * 查询所有的UserManage
     *
     * @return
     */
    Result selectUserManage();

    Result deleteUserManageByUid(Integer uid);

    Result addUserRole(Integer uid, Integer rid, String pid);

    Result updateUserRole(Integer urid, Integer uid, Integer rid);

    Result findAllRole();

    Result deleteRoleByRid(Integer rid);

    Result addRole(Role role);

    Result updatePassword(Integer uid, String newPassword);

    Result updateUser(UserPO userPO);
}
