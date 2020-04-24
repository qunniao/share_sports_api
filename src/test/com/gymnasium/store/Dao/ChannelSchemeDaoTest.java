package com.gymnasium.store.Dao;

import com.gymnasium.store.PO.ChannelSchemePO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 边书恒
 * @Title: ChannelSchemeDaoTest
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/7/29 17:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelSchemeDaoTest {

    @Autowired
    private ChannelSchemeDao channelSchemeDao;

    @Test
    public void queryChannelScheme() {

        List<ChannelSchemePO> channelSchemePOS = channelSchemeDao.queryChannelScheme("鸡胸", null, 1, null);

        System.out.println(channelSchemePOS);
    }
}