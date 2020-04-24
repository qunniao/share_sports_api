package com.gymnasium.store.service;

import com.gymnasium.store.PO.CashFlowPO;
import com.gymnasium.store.VO.CashFlowVO;

import java.math.BigDecimal;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/30 18:48
 * @Description:
 */
public interface CashFlowService {

    /**
     * 添加佣金流水
     * @param cashFlowVO
     * @return
     */
    Boolean insertCashFlow(CashFlowVO cashFlowVO);

    /**
     * 查询佣金流水
     * @param orderId
     * @return
     */
    CashFlowVO queryCashFlow(Long orderId);
}
