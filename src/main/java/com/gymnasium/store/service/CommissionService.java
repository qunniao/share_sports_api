package com.gymnasium.store.service;

import com.gymnasium.store.VO.CommissionVO;

import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/29 20:15
 * @Description:
 */
public interface CommissionService {

   /**
    * 统计客户佣金和代理佣金
    * @param userId
    * @return
    */
   Map<String, Object> countCommission(Integer userId);

   /**
    * 二级团队人数，累计佣金，累计销售额统计接口
    * @param userId
    * @return
    */
   Map<String, Integer> channelStatistics(Integer userId);

   /**
    * 查询代理人成功提现佣金，可提现佣金
    * @param agentId
    * @return
    */
   Map<String, Object> queryWithdraw(Integer agentId);

   /**
    * 用户佣金数据
    * @param userId
    * @return
    */
   CommissionVO queryCommission(Integer userId);
}