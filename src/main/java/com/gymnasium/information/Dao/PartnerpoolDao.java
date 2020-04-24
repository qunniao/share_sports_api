package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.PartnerpoolPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 王志鹏
 * @title: PartnerpoolDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/18 10:12
 */
public interface PartnerpoolDao extends JpaRepository<PartnerpoolPO, Integer>, JpaSpecificationExecutor<PartnerpoolPO> {

    PartnerpoolPO queryByUserId(String userId);

}