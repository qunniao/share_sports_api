package com.gymnasium.store.Dao;

import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.PO.ServicePersonnelPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 边书恒
 * @Title: ServicePersonnelDaoTest
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/14 11:38
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ServicePersonnelDaoTest {

    @Autowired
    private ServicePersonnelDao servicePersonnelDao;

    @Test
    public void findByServiceNumber() {

//        ServicePersonnelPO servicePersonnelPO = servicePersonnelDao.findByServiceNumber();
//
//        System.out.println(servicePersonnelPO);


    }
}