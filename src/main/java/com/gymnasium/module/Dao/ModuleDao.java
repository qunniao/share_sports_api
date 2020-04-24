package com.gymnasium.module.Dao;

import com.gymnasium.module.PO.ModulePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 王志鹏
 * @title: moduleDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/9 10:01
 */
public interface ModuleDao extends JpaRepository<ModulePO, Integer> {

    //str为1,2,3,这种形式
    @Query(value = "SELECT DISTINCT b.mid, b.fatherModuleId, b.name,b.icon, b.url, b.level, b.sort  FROM gymnasium.sys_module_role AS a, gymnasium.sys_module AS b WHERE a.mid = b.mid AND a.rid IN (?1) ORDER BY b.fatherModuleId, b.level, b.sort ASC;", nativeQuery = true)
    List<ModulePO> queryModulePOByRid(String str);

    @Query(value = "SELECT * FROM gymnasium.sys_module AS b ORDER BY b.fatherModuleId , b.level , b.sort ASC;", nativeQuery = true)
    List<ModulePO> queryAllBySrot();

    @Query("select po  from ModulePO po where po.mid= ?1")
    ModulePO findByMid(Integer mid);

}
