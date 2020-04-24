package com.gymnasium.store.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/5 16:51
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommissionDaoTest {

    @Autowired
    private CommissionDao commissionDao;

    @Test
    public void test(){

//        BigDecimal countAgentCommission = commissionDao.countAgentCommission(1, 0, "2019");
//
//        System.out.println(countAgentCommission);

//        List<Integer> list = commissionDao.groupBy("2019");
//
//        System.out.println(list);

    }

    @Test
    public void queryOrder() {

        List list = new ArrayList();

        list.add(7);
        list.add(8);
        list.add(9);
        List list1 = commissionDao.queryOrder(list);

        System.out.println(list1);
    }
}