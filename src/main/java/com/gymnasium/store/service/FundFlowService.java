package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.FundFlowPO;
import com.gymnasium.store.VO.FundFlowRequestVO;
import com.gymnasium.store.VO.FundFlowVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/31 14:42
 * @Description:
 */
public interface FundFlowService {

    Boolean insertFundFlow(FundFlowVO fundFlowVO);

    Page<FundFlowPO> pageAllByStatus(Paging page, FundFlowVO fundFlowVO, FundFlowRequestVO fundFlowRequestVO);

    Page<FundFlowPO> queryFlowRecord(Paging page, Integer userId);

    Page<FundFlowPO> pageWithdrawalRecord(Paging page, FundFlowVO fundFlowVO);

}
