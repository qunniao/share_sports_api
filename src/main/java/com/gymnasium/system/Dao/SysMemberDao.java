package com.gymnasium.system.Dao;

import com.gymnasium.system.PO.SysMemberPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 王志鹏
 * @title: SysMemberDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/12 15:32
 */
public interface SysMemberDao extends JpaRepository<SysMemberPO,Integer> {

    SysMemberPO queryByLevel(Integer level);

    SysMemberPO findSysMemberPOById(Integer id);

    Integer deleteSysMemberPOById(Integer id);

}