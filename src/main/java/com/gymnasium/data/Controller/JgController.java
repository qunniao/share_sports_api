package com.gymnasium.data.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.RandomUtil;
import com.gymnasium.Util.ReadProperties;
import com.gymnasium.Util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * @author 王志鹏
 * @title: JgController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/11 14:02
 */
@Api(tags = "极光:API")
@RestController
@RequestMapping(value = "/jiguangs")
public class JgController {

    @ApiOperation("极光聊天")
    @RequestMapping(value = "/jgtm", method = RequestMethod.POST)
    public Result<HashMap> jgtm() {
        HashMap map = new HashMap();

        String random_str = RandomUtil.getRandomStringByLength(22);
        String appkey = ReadProperties.getProperties_1("", "AppKey");
        String MasterSecret = ReadProperties.getProperties_1("", "MasterSecret");
        String timestamps = System.currentTimeMillis() + "";
        String signature = "appkey=" + appkey + "&timestamp=" + timestamps + "&random_str=" + random_str + "&key=" + MasterSecret;
        Md5Hash md5Hash = new Md5Hash(signature);

        map.put("random_str", random_str);
        map.put("appkey", appkey);
        map.put("timestamp", timestamps);
        map.put("signature", md5Hash.toString());
        return ResultUtil.success(map);
    }

}
