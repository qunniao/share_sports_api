package com.gymnasium.order.Service;

import com.gymnasium.order.PO.WithdrawalPO;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/8 09:37
 * @Description:
 */
public interface WithdrawalService {

    /**
     * 佣金申请提现接口
     * @param withdrawalPO
     * @return
     */
    Boolean requestWithdrawal(WithdrawalPO withdrawalPO);

    /**
     * 分页查询提现记录
     * @param paging
     * @return
     */

    /**
     * 审核提现接口
     * @param id
     * @param reviewState
     * @param refuse
     * @return
     */
    Boolean auditWithdraw(Integer id, Integer reviewState, String refuse);
}
