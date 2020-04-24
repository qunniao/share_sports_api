package com.gymnasium.card.Dao;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpUtil;
import com.gymnasium.card.PO.ActivationRecordPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * ActivationRecordDaoTest
 *
 * @Author: 边书恒
 * @Description: TODO
 * @menu
 * @Date 2019 -08-13 13:51:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivationRecordDaoTest {

    @Autowired
    private ActivationRecordDao activationRecordDao;

    @Test
    public void queryNewestRecord() {
        ActivationRecordPO activationRecordPO = activationRecordDao.queryNewestRecord("tmCMl15I190723");

        System.out.println(activationRecordPO);

        Assert.notNull(activationRecordPO, "数据是非空的");

    }

    @Test
    public void HttpUtilTest(){

        Map map = new HashMap<>();

        map.put("value", "hello");

        String s = HttpUtil.get("192.168.1.20:8080/log/loginUrl", map);

        Assert.notNull(s,"请求失败");
    }
}