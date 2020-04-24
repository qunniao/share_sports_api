package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.GroupCommentPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 王志鹏
 * @title: GroupCommentDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 14:27
 */
public interface GroupCommentDao extends JpaRepository<GroupCommentPO, Integer>, JpaSpecificationExecutor<GroupCommentPO> {

}