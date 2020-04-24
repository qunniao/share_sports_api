package com.gymnasium.store.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/19 14:26
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AgentDaoTest {

    @Autowired
    private AgentDao agentDao;

    @Test
    public void test() {

        Integer integer = agentDao.quarterAgentNumber(1, "城区", 1, "2019-5-28", "2019-5-30");

        System.out.println(integer);
    }

}