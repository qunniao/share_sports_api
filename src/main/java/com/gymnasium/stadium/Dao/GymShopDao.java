package com.gymnasium.stadium.Dao;

import com.gymnasium.stadium.PO.GymShopPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GymShopDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/2 15:12
 */

public interface GymShopDao extends JpaRepository<GymShopPO, Integer>, JpaSpecificationExecutor<GymShopPO> {

    GymShopPO findByGymShopId(String gymShopId);

    List<GymShopPO> findByMatching(Integer gymShopId);


    @Query(value = "SELECT COUNT(gid) FROM gym_shop WHERE IF (?1 != '', cityId = ?1, 1 = 1)\n" +
            "AND createTime BETWEEN ?2 AND ?3",nativeQuery = true)
    Integer statisticalGymShop(Integer cityId, String startTime, String endTime);


    @Query(value = "SELECT COUNT(gid) FROM gym_shop WHERE IF (?1 != '', cityId = ?1, 1 = 1)\n" +
            "AND createTime LIKE CONCAT (?2,'%')",nativeQuery = true)
    Integer countGymShop(Integer cityId, String times);

}