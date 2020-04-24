package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:21
 **/
public interface UserRoleDao extends JpaRepository<UserRole, Integer> {

    @Modifying
    @Query("update UserRole u set u.uid =?2,u.rid=?3 where u.urid= ?1")
    void updateUserRole(int urid, int uid, int rid);

    UserRole findByUid(Integer uid);

}