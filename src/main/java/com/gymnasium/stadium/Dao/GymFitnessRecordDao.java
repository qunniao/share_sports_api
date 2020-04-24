package com.gymnasium.stadium.Dao;

import com.gymnasium.stadium.PO.GymFitnessRecordPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GymFitnessRecordDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/24 14:51
 */
public interface GymFitnessRecordDao extends JpaRepository<GymFitnessRecordPO, Integer> , JpaSpecificationExecutor<GymFitnessRecordPO>  {

    GymFitnessRecordPO queryBySerialId(String serialId);

    List<GymFitnessRecordPO> queryByGymShopIdAndType(String gymShopId, int type);

}