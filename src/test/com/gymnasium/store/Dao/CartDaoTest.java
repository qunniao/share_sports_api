package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.CartPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/27 18:28
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartDaoTest {

    @Autowired
    private CartDao cartDao;

    @Test
    public void test (){
        long[] cartIds = new long[]{1,2,3};

        List<CartPO> cartPOS = cartDao.queryAll(39, 1, null);

        System.out.println(cartPOS);
    }

}