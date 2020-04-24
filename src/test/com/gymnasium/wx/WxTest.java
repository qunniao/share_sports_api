package com.gymnasium.wx;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gymnasium.user.bean.WxDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author 边书恒
 * @Title: WxTest
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/7/22 20:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WxTest {

    @Test
    public void wxDeCode(){


        JSONObject userInfo = new JSONObject("{\"country\":\"China\",\"unionId\":\"oIsr2Up151HOxk_Vh1d4\",\"watermark\":{\"appid\":\"wxc2c9c1f68146c9a6\",\"timestamp\":1563799417},\"gender\":1,\"province\":\"Zhejiang\",\"city\":\"Wenzhou\",\"avatarUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/H1k9UFwLkliaqAiahhTnXnebIjmhcdWsGFTibEsA9ehXcRSGib64GQlRmbenwRuaGyZKeAWZicS1EBfxt5c4SZIyyOg/132\",\"openId\":\"oWQKX5HeJBbImRF_a_OBquyXc-Qo\",\"nickName\":\"晴天安好\",\"language\":\"zh_CN\"}\n");
//        JSONObject userInfo = JSONObject.parseObject("{\"country\":\"China\",\"unionId\":\"oIsr2Up151HOxk_Vh1d4\",\"watermark\":{\"appid\":\"wxc2c9c1f68146c9a6\",\"timestamp\":1563799417},\"gender\":1,\"province\":\"Zhejiang\",\"city\":\"Wenzhou\",\"avatarUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/H1k9UFwLkliaqAiahhTnXnebIjmhcdWsGFTibEsA9ehXcRSGib64GQlRmbenwRuaGyZKeAWZicS1EBfxt5c4SZIyyOg/132\",\"openId\":\"oWQKX5HeJBbImRF_a_OBquyXc-Qo\",\"nickName\":\"晴天安好\",\"language\":\"zh_CN\"}\n");

        String json = "{\"country\":\"China\",\"unionId\":\"1HOxk_Vh1d4\",\"watermark\":{\"appid\":\"wxc2c9c1f68146c9a6\",\"timestamp\":1563799417},\"gender\":1,\"province\":\"Zhejiang\",\"city\":\"Wenzhou\",\"avatarUrl\":\"https://wx.qlogo.cn/mmopen/vi_32/H1k9UFwLkliaqAiahhTnXnebIjmhcdWsGFTibEsA9ehXcRSGib64GQlRmbenwRuaGyZKeAWZicS1EBfxt5c4SZIyyOg/132\",\"openId\":\"oWQKX5HeJBbImRF_a_OBquyXc-Qo\",\"nickName\":\"晴天安好\",\"language\":\"zh_CN\"}\n";

        WxDTO wxDTO = JSONUtil.toBean(json, WxDTO.class);

        WxDTO wxDTO1 = JSONUtil.toBean(userInfo, WxDTO.class);

        Assert.assertNotNull("json对象转换失败", wxDTO);

    }
}
