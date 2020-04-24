package com.gymnasium.stadium.Dao;

import com.gymnasium.stadium.PO.GymBuildingPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 王志鹏
 * @Date 2019/4/6 12:24
 **/
public interface GymBuildingDao extends JpaRepository<GymBuildingPO, Integer> {
    
    List<GymBuildingPO> queryByGid(int gid);

    Integer deleteGymBuildingPOById(int gid);

}
