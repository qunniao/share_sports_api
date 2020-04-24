package com.gymnasium.Util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.message.MessageBody;
import com.gymnasium.store.Dao.ServiceUserDao;
import com.gymnasium.store.PO.ServiceUser;
import com.gymnasium.store.PO.ShopOrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 边书恒
 * @Title: JgSendUtil
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/19 20:01
 */
@Component
public class JgSendUtil {

    @Autowired
    private ServiceUserDao serviceUserDao;

    String APP_KEY = ReadProperties.getProperties_1("", "AppKey");
    String MASTER_SECRET = ReadProperties.getProperties_1("", "MasterSecret");


}
