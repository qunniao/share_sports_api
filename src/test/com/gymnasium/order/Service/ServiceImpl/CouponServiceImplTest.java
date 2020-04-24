package com.gymnasium.order.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.order.PO.CouponPO;
import com.gymnasium.order.Service.CouponService;
import com.gymnasium.system.PO.SysMemberPO;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/9 17:08
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceImplTest {

    @Autowired
    private CouponService couponService;

    @Test
    public void queryCoupons() {

//
//        Set<CouponPO> couponPOS = couponService.queryCoupons("MAxPMv5c190611", new BigDecimal(100), 2);
//
//        System.out.println(couponPOS);

        SysMemberPO sysMemberPO = new SysMemberPO();

        System.out.println(ObjectUtils.allNotNull(sysMemberPO));
        System.out.println(ObjectUtils.anyNotNull(sysMemberPO));
    }
}