package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.PO.AgentPO;
import com.gymnasium.store.PO.ChannelPO;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.store.VO.AgentVO;
import com.gymnasium.store.VO.ChannelVO;
import com.gymnasium.store.VO.CommissionVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 14:43
 * @Description:
 */
public interface ChannelService {

    Boolean insertChannel(ChannelVO channelVO, String superiorId, String code);

    Page<ChannelPO> pageAllChannel(Paging page, ChannelVO channelVO);

    Boolean updateChannel(ChannelVO channelVO);

    Boolean auditChannel(Integer id, Integer auditStatus);

    Boolean deleteChannel(Integer id);

    List<Map> queryChannelTeam(CommissionVO commissionVO, Paging page);

    Integer queryChannelLevel(Integer userId);
}
