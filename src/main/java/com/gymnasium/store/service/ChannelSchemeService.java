package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.ChannelSchemePO;
import com.gymnasium.store.VO.AgentSchemeVO;
import com.gymnasium.store.VO.ChannelSchemeVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:14
 * @Description:
 */
public interface ChannelSchemeService {

    /**
     * 编辑一级渠道方案
     * @param channelSchemeVO
     * @return
     */
    ChannelSchemeVO editOneLevel(ChannelSchemeVO channelSchemeVO);

    /**
     * 编辑二级渠道方案
     * @param channelSchemeVO
     * @return
     */
    ChannelSchemeVO editSecondLevel(ChannelSchemeVO channelSchemeVO);

    /**
     * 根据用户id 和 产品id 查询渠道方案
     * @param userId
     * @param productId
     * @return
     */
    ChannelSchemeVO findByUserIdAndProductId(Integer userId, Integer productId);

    /**
     * 根据渠道编码查询渠道方案
     * @param channelSchemeCode
     * @return
     */
    ChannelSchemeVO findByChannelCode(String  channelSchemeCode);

    /**
     * 根据用户id 和 产品id 查询二级渠道方案
     * @param userId
     * @param productId
     * @return
     */
    ChannelSchemePO findTowProductScheme(Integer userId, Integer productId);

    /**
     * 分页查询一级渠道方案
     * @param page
     * @param type
     * @return
     */
    Page<ChannelSchemePO> pageOneChannelScheme(Paging page,Integer type);

    /**
     * 分页查询二级渠道方案
     * @param page
     * @param userId
     * @param type
     * @return
     */
    Page<ChannelSchemePO> pageTowChannelScheme(Paging page,Integer userId,Integer type);

    /**
     * 更新渠道方案
     * @param channelSchemeVO
     * @return
     */
    Boolean updateChannelScheme(ChannelSchemeVO channelSchemeVO);

    /**
     * 查询渠道方案
     * @param channelSchemeVO
     * @param name
     * @return
     */
    List<ChannelSchemePO> queryChannelScheme(ChannelSchemeVO channelSchemeVO, String name);

    /**
     * 根据等级查询渠道方案
     * @param type
     * @param level
     * @param userId
     * @return
     */
    List<ChannelSchemePO> queryAllChannelScheme(Integer type, Integer level, Integer userId);

    /**
     * 编辑渠道方案
     * @param channelSchemeVO
     * @return
     */
    boolean editChannelScheme(ChannelSchemeVO channelSchemeVO);


    /**
     * 根据产品id查询一级渠道方案
     *
     * @param productId 产品id
     * @return AgentSchemeVO
     */
    ChannelSchemeVO queryByProductId(Integer productId);
}
