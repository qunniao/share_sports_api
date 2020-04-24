package com.gymnasium.data.Dao;

import com.gymnasium.data.PO.SubjectPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王志鹏
 * @title: SubjectDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 17:19
 */

public interface SubjectDao extends JpaRepository<SubjectPO, Integer> {

}
