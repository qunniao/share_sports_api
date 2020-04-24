package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:49
 **/
public interface PermissionDao extends JpaRepository<Permission, Integer>, JpaSpecificationExecutor<Permission> {

}
