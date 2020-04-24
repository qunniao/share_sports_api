package com.gymnasium.information.Dao;

import com.gymnasium.information.PO.GfriendPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GfriendDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/23 10:50
 */
public interface GfriendDao extends JpaRepository<GfriendPO, Integer> {

    List<GfriendPO> queryByAuserId(String auserId);

    Integer countByAuserIdOrBuserId(String aUserId, String bUserId);
}