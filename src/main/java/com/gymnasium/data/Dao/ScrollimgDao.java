package com.gymnasium.data.Dao;

import com.gymnasium.data.PO.ScrollimgPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 王志鹏
 * @title: ScrollimgDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/30 9:49
 */
public interface ScrollimgDao extends JpaRepository<ScrollimgPO,Integer>,
        JpaSpecificationExecutor<ScrollimgPO> {
    ScrollimgPO queryById(int id);
}
