package com.gymnasium.stadium.Dao;

import com.gymnasium.stadium.PO.GymImagesPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GymImagesVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/10 15:38
 */


public interface GymImagesDao extends JpaRepository<GymImagesPO, Integer>, JpaSpecificationExecutor<GymImagesPO> {

    Page<GymImagesPO> findAllByGymShopIdAndType(Pageable pageable,String gymShopId, String type);

    GymImagesPO queryById(int id);

    List<GymImagesPO> queryByGymShopIdAndStatus(String gymShopId, int status);

    List<GymImagesPO> queryByGymShopIdAndTypeAndStatus(String gymShopId, int type, int status);

}
