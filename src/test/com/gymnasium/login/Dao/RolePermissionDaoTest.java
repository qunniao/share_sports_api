package com.gymnasium.login.Dao;

import com.gymnasium.personnel.Dao.RolePermissionDao;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.RolePermission;
import com.gymnasium.personnel.PO.UserPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 边书恒
 * @Title: RolePermissionDaoTest
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/12 18:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RolePermissionDaoTest {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void findAllByRid() {
        List<RolePermission> allByRid = rolePermissionDao.findAllByRid(1);
        System.out.println(allByRid);
    }

    @Test
    public void LoginTest(){

        UserPO userPO = userDao.findByUnionId("oIPqK1Nakqul2pULW8o_CytYjNnc");
        UserPO user = userDao.findUserPOByUnionId("oIPqK1Nakqul2pULW8o_CytYjNnc");

        System.out.println(userPO);
        System.out.println(user);
    }
}