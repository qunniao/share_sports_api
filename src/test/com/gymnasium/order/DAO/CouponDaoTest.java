package com.gymnasium.order.DAO;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.gymnasium.order.PO.CouponPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/12 10:52
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponDaoTest {

    @Autowired
    private CouponDao couponDao;

    @Test
    public void test() {
        Snowflake snowflake = new Snowflake(1,1,true);

        for (int i = 0; i < 10; i++) {
            long dataCenterId = snowflake.getDataCenterId(1);
            long generateDateTime = snowflake.getGenerateDateTime(1);
            long workerId = snowflake.getWorkerId(1);
            System.out.println("dataCenterId" + dataCenterId);
            System.out.println("generateDateTime" + generateDateTime);
            System.out.println("workerId" + workerId);
            System.out.println("nextId()" + snowflake.nextId());
            System.out.println("nextIdStr()" + snowflake.nextIdStr());
        }
    }

    @Test
    public void queryIssueCoupon() {

        List<CouponPO> couponPOS = couponDao.queryIssueCoupon(2);

        System.out.println(couponPOS);

        Assert.notEmpty(couponPOS, "数据为空");
    }
}