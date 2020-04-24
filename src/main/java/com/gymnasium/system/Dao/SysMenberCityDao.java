package com.gymnasium.system.Dao;

import com.gymnasium.system.PO.SysMenberCityPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王志鹏
 * @title: SysMenberCityDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 17:06
 */
public interface SysMenberCityDao extends JpaRepository<SysMenberCityPO, Integer> {

    SysMenberCityPO queryByCityNameLikeAndMid(String cityName, int mid);
}