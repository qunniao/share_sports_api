package com.gymnasium.record.Dao;

import com.gymnasium.record.PO.CollectPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王志鹏
 * @title: CollectPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/20 14:13
 */
public interface CollectDao extends JpaRepository<CollectPO, Integer> {

    List<CollectPO> queryByUserId(String userId);

    List<CollectPO> queryByUserIdAndType(String userId, int type);

    CollectPO queryByUserIdAndCoachId(String userId, int coachid);

    CollectPO queryByUserIdAndGymShopId(String userId, String gymShopId);

    void deleteByUserIdAndCoachIdIn(String userId,Integer[] coachId);
}
