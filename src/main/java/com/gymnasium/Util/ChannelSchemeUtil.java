package com.gymnasium.Util;

import cn.hutool.core.util.RandomUtil;
import com.gymnasium.store.Dao.ChannelSchemeDao;
import com.gymnasium.store.PO.ChannelSchemePO;
import com.gymnasium.store.VO.ChannelSchemeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 15:01
 * @Description:
 */
@Component
public class ChannelSchemeUtil{

    @Autowired
    private ChannelSchemeDao channelSchemeDao;

    public ChannelSchemePO getChannelSchemePO(ChannelSchemeVO channelSchemeVO, ChannelSchemePO channelScheme,Integer level){

        ChannelSchemePO channelSchemePO = new ChannelSchemePO();

        if (channelScheme == null){

            BeanUtil.copyPropertie(channelSchemeVO, channelSchemePO);

            String code  = "C" + RandomUtil.randomString(7);

            channelSchemePO.setChannelCode(code);
            channelSchemePO.setCreateTime(new Date());
            channelSchemePO.setLevel(level);
            channelSchemePO.setChannelPrice(channelSchemeVO.getChannelPrice());
            channelSchemePO.setUpdateTime(new Date());
            channelSchemePO.setMemberLevel(channelSchemeVO.getMemberLevel());

            channelSchemeDao.save(channelSchemePO);

            return channelSchemePO;
        }

        BeanUtil.copyPropertie(channelSchemeVO,channelScheme);

        channelScheme.setUpdateTime(new Date());
        channelScheme.setLevel(level);

        channelSchemeDao.save(channelScheme);

        return channelScheme;
    }
}
