package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ChartPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/9 14:48
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void test(){

        List<ChartPO> list = productDao.quarterProduct(1, 1,"2019");

        System.out.println(list);

    }

}