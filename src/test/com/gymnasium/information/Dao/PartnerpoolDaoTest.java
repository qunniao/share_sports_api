package com.gymnasium.information.Dao;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/20 14:45
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartnerpoolDaoTest {

    @Autowired
    private PartnerpoolDao partnerpoolDao;

    @Test
    public void test(){

//        for (int i = 0; i < 10; i++) {
//
//            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
//
//            System.out.println("1: \t" + snowflake.nextId());
//        }
//        for (int i = 0; i < 10; i++) {
//            Snowflake snowflake = IdUtil.getSnowflake(2, 2);
//            System.out.println("2: \t" + snowflake.nextId());
//        }

        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();

        System.out.println(id);

    }
}