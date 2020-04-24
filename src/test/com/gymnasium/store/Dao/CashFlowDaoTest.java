package com.gymnasium.store.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.SetOverrideType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author 边书恒
 * @Title: CashFlowDaoTest
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/16 11:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CashFlowDaoTest {
    @Autowired
    private CashFlowDao cashFlowDao;

    @Test
    public void countUserSales() {

        List list = new ArrayList<>();

        list.add(9);

        List<Map> list1 = cashFlowDao.countUserSales(list, 1,1);

        for (Map map:list1) {
            map.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
        }

    }
}