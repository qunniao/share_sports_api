package com.gymnasium.data.Dao;

import com.gymnasium.data.PO.BuildingPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王志鹏
 * @title: BuildingDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 17:17
 */
public interface BuildingDao extends JpaRepository<BuildingPO, Integer> {

    BuildingPO findByBid(int bid);

}
