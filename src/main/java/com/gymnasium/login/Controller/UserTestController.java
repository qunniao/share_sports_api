package com.gymnasium.login.Controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.JsonObject;
import com.gymnasium.Util.JgUtil;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.Dao.ProductDao;
import com.gymnasium.store.Dao.ShopOrderDao;
import com.gymnasium.store.PO.ShopOrderPO;
import com.gymnasium.store.VO.ShopOrderVO;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王志鹏
 * @title: UserController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/1 11:55
 */
@Api("用户测试")
@RestController
@RequestMapping(value = "/test")
public class UserTestController {

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JgUtil jgUtil;

//    @RequiresRoles("user")
    @RequiresPermissions("user:add")
    @RequestMapping(value = "/noPermissionsss", method = RequestMethod.POST)
    public String noPermission() {

        System.out.println("调用成功了");
        return "调用成功了";
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST)
    public String test1(String orderNumber) {

        ShopOrderPO shopOrderPO = shopOrderDao.findByOrderNumber(orderNumber);

        ShopOrderVO shopOrderVO = new ShopOrderVO();
        BeanUtil.copyProperties(shopOrderPO,shopOrderVO);
        System.out.println(shopOrderPO);

        JSONObject json = JSONUtil.parseObj(shopOrderPO);

        String toString = json.toString();

        Map<String, String> map = new HashMap<>(2);

        map.put("type","1");
        map.put("content", toString);

        jgUtil.sendSingleTextByAdmin(shopOrderPO,"2");

        return "调用成功了";
    }
    @RequestMapping(value = "/2", method = RequestMethod.POST)
    public UserPO test2(Integer id) {

        return userDao.findByUid(id);
    }

    @RequestMapping(value = "/3", method = RequestMethod.POST)
    public String test3(Integer id) {

        jgUtil.addFriend(id);

        return "添加好友测试";
    }

}
