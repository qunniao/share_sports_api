package com.gymnasium.module.Dao;

import com.gymnasium.module.PO.ModuleRolePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 王志鹏
 * @title: ModuleRoleDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/9 15:02
 */
public interface ModuleRoleDao extends JpaRepository<ModuleRolePO, Integer> {

    List<ModuleRolePO> queryByRidIn(List<Integer> rids);

    ModuleRolePO findAllByRid(int rid);

    @Modifying
    @Query("delete from ModuleRolePO po where po.rid=?1")
    void deleteAllByRid(Integer rid);


}