package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.ShopOrderPO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/4 15:07
 * @Description:
 */
public interface ChannelTeamService {

    /**
     * 查询渠道订单
     * @param userId
     * @param role
     * @param orderState
     * @param page
     * @return
     */
    Page<ShopOrderPO> queryTowOrder(Integer userId, Integer role, Integer orderState, Paging page);

    /**
     * 查询所有订单的累计佣金
     * @param userId
     * @return
     */
    BigDecimal queryTotalCommission(Integer userId);

}
