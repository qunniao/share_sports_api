package com.gymnasium.stadium.Dao;

import com.gymnasium.stadium.PO.GymCardLogPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GymCardLogDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/15 11:20
 */
public interface GymCardLogDao extends JpaRepository<GymCardLogPO, Integer> {

    List<GymCardLogPO> queryByUserId(String userId);
}