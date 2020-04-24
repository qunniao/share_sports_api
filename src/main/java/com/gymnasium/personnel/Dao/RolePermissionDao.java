package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:59
 **/
public interface RolePermissionDao extends JpaRepository<RolePermission, Integer> {

    /**
     * 根据管理员角色查询功能权限
     * @param rId
     * @return
     */
    List<RolePermission> findAllByRid(Integer rId);

}