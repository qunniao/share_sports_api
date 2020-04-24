package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.UserManagePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 12:49
 **/
public interface UserManageDao extends JpaRepository<UserManagePO, Integer> {

    UserManagePO queryByUserId(String userId);

    UserManagePO queryByUserName(String username);

    @Query("select  u from UserManagePO u where u.uid=?1")
    UserManagePO findSaltByUid(int uid );

    @Modifying
    @Query("update UserManagePO u set u.passWord =?2 where u.uid =?1")
    void updatePassword(int uid ,String password);
}
