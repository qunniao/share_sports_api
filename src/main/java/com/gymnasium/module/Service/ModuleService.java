package com.gymnasium.module.Service;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.module.PO.ModulePO;

import java.util.List;

/**
 * @author 王志鹏
 * @title: ModuleService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/9 10:02
 */
public interface ModuleService {

    /**
     * Find all list
     *
     * @return the list
     */
    List<ModulePO> findAll();

    /**
     * Find moudel by usre id list
     *
     * @param userId user id
     * @return the list
     */
    List<ModulePO> findMoudelByUsreId(String userId);

    /**
     * moule service *
     *
     * @param modulePO module po
     * @return moule service
     */
    boolean addMouleService(ModulePO modulePO);

    /**
     * Update module by rid result
     *
     * @param rid rid
     * @param mid mid
     * @return the result
     */
    Result updateModuleByRid(Integer rid, List<Integer> mid);

    /**
     * Add module result
     *
     * @param modulePO module po
     * @return the result
     */
    Result addModule(ModulePO modulePO);

    /**
     * Update module result
     *
     * @param modulePO module po
     * @return the result
     */
    Result updateModule(ModulePO modulePO);

    /**
     * Delete module result
     *
     * @param mid mid
     * @return the result
     */
    Result deleteModule(int mid);

    /**
     * 查询菜单的功能权限
     * @return
     */
    List<ModulePO> queryAll();

}
