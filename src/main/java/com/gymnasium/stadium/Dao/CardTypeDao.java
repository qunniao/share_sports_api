package com.gymnasium.stadium.Dao;


import com.gymnasium.stadium.PO.CardTypePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 王志鹏
 * @title: CardDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/15 9:44
 */
public interface CardTypeDao extends JpaRepository<CardTypePO, Integer> {

    CardTypePO queryByIdAndStatus(int id, int status);

    List<CardTypePO> queryByGymShopIdAndStatus(String gymShopId, int status);

    CardTypePO queryByGymShopIdAndName(String gymShopId, String name);

    CardTypePO queryById(int id);

    List<CardTypePO> queryByGymShopId(String gymShopId);

    @Modifying
    @Query("update CardTypePO c set  c.status=0 where c.id = ?1")
    void updateStatusById(int id);
}
