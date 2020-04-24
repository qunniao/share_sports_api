package com.gymnasium.file.Dao;

import com.gymnasium.file.PO.ObjFilePO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王志鹏
 * @title: ObjFileDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/10 9:28
 */
public interface ObjFileDao extends JpaRepository<ObjFilePO, Integer> {

    ObjFilePO queryByName(String name);
}
