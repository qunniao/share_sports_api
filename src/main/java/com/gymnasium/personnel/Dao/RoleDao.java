package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:11
 **/
public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findAllByRid(int rid);

}
