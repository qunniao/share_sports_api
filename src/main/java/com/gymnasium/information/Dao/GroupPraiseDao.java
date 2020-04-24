package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.GroupPraisePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王志鹏
 * @title: GroupPraiseDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 14:29
 */
public interface GroupPraiseDao extends JpaRepository<GroupPraisePO, Integer> {

    GroupPraisePO queryByMidAndUserId(int mid, String userId);






}
