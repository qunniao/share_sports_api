package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.GroupPictorePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 王志鹏
 * @title: GroupPictoreDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 14:26
 */
public interface GroupPictoreDao extends JpaRepository<GroupPictorePO, Integer>, JpaSpecificationExecutor<GroupPictorePO> {

}