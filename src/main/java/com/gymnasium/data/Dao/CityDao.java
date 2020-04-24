package com.gymnasium.data.Dao;

import com.gymnasium.data.PO.CityPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王志鹏
 * @title: CityPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 10:36
 */
public interface CityDao extends JpaRepository<CityPO, Integer> {

    List<CityPO> queryByLevel(short level);

    List<CityPO> queryByMergerNameLikeAndLevel(String mergerName, short level);

    List<CityPO> queryByPid(int pid);

    CityPO queryById(int id);
}
