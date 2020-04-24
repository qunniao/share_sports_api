package com.gymnasium.information.Dao;

import com.gymnasium.order.DAO.CouponOperationDao;
import com.gymnasium.personnel.Dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/20 21:01
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class test {

    @Autowired
    private CouponOperationDao couponOperationDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void tests() {

//        String str = "1,2,3";
//
//        String[] split = StringUtils.split(str,",");
//
//        for (String string: split){
//            System.err.println(string);
//        }

//        String sign =
//                "gymShopId="+ "GY_1ZSpFUco190530"+"&type=" + "11545"  +"&failure_time="+System.currentTimeMillis()/1000;
//
//        String code = JwtUtil.sign(sign);
//
//        String verify = JwtUtil.verify(code);
//
//        String failureTime = verify.substring(verify.lastIndexOf("failure_time=") + 13);
//
//        String gymShopId = verify.substring(verify.indexOf("gymShopId=") + 10,verify.indexOf("&"));
//
//        String type = verify.substring(verify.indexOf("type=") + 5,verify.lastIndexOf("&"));
//
//        System.err.println("sign:\t" + sign);
//        System.err.println("code:\t" + code);
//        System.err.println("verify:\t" + verify);
//        System.err.println("failureTime:\t" + failureTime);
//        System.err.println("gymShopId:\t" + gymShopId);
//        System.err.println("type:\t" + type);
//
//        String codes = JwtUtil.sign(sign);
//
//        System.err.println("code" + codes);
//
//        String verify = JwtUtil.verify(codes);
//
//        System.err.println("verify" + verify);
//
//        String code = verify.substring(verify.lastIndexOf("failure_time=") + 13);
//
//        System.err.println("code" + code);

//        String attach = "{\"type\":" + 1 + ",\"orderNumber\":" + 2 + "}";
//
//        JSONObject json = JSONObject.parseObject(attach);
//
//        System.out.println(json.getString("type"));
//        System.out.println(json.getString("orderNumber"));

//        int a = 3;
//
//        String s = a == 1 ? "成功" : a == 4 ? "失败" : a == 3 ? "555" : a == 8 ? "失败233" : "";
//
//        System.err.println(s);

//        Timestamp newDate = new Timestamp(System.currentTimeMillis());
//
//        System.err.println("newDate\t" + newDate);
//
//        Date date = newDate;
//
//        System.err.println("date\t" + date);
//
//        Date ExpirationTime = DateUtil.offsetDay(date, 365);
//
//        System.err.println("ExpirationTime\t" + ExpirationTime);
//
//        Timestamp timestamp = new Timestamp(ExpirationTime.getTime());
//
//        System.err.println("timestamp\t" + timestamp);


    }

}
