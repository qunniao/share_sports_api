package com.gymnasium.personnel.Dao;

import com.gymnasium.personnel.PO.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 王志鹏
 * @title: UserDao
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/2 9:18
 */

public interface UserDao extends JpaRepository<UserPO, Integer>, JpaSpecificationExecutor<UserPO> {

    UserPO queryByUserPhone(String userPhone);

    UserPO findByUserId(String userId);

    UserPO findByLevel(int level);

    UserPO findByOpenid(String openid);

    void deleteAllByUserId(String userId);

    UserPO findByUid(Integer uId);

    UserPO findByUnionId(String unionId);


    UserPO findUserPOByUnionId(String unionId);

    UserPO findByJiguangUsername(String jPushId);
}