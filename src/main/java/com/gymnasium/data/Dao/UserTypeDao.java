package com.gymnasium.data.Dao;

import com.gymnasium.data.PO.UserTypePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 王志鹏
 * @title: UserTypeDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/17 17:04
 */
public interface UserTypeDao extends JpaRepository<UserTypePO, Integer> {

    List<UserTypePO> queryByType(int type);

    int countByNameAndType(String name, int type);
}
