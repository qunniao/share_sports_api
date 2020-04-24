package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.CoachUserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 王志鹏
 * @title: CoachUserDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/11 17:50
 */
public interface CoachUserDao extends JpaRepository<CoachUserPO, Integer> {

    CoachUserPO queryByUserId(String usreID);

    CoachUserPO queryById(int id);

    List<CoachUserPO> queryByGymShopId(String gymShopId);

    @Query("update CoachUserPO c set c.status=0 where c.id=?1")
    @Modifying
    void updateStatusById(Integer id);
}