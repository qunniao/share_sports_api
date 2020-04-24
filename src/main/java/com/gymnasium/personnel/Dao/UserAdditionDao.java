package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.UserAdditionPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 王志鹏
 * @title: UserAdditionDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/17 14:47
 */
public interface UserAdditionDao extends JpaRepository<UserAdditionPO, Integer> {

    UserAdditionPO queryByUserId(String userId);
}