package com.gymnasium.Util;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.order.PO.OrderPO;
import com.gymnasium.store.Dao.*;
import com.gymnasium.store.PO.AgentSchemePO;
import com.gymnasium.store.PO.ChannelSchemePO;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.store.PO.OrderProductPO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/30 14:37
 * @Description:
 */
@Component
public class CommissionUtil {

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private AgentSchemeDao agentSchemeDao;

    @Autowired
    private ChannelSchemeDao channelSchemeDao;

    @Autowired
    private OrderProductDao orderProductDao;

    @Autowired
    private FlowRecordUtil flowRecordUtil;

    /**
     * 获取一级代理佣金
     *
     * @param orderId
     * @return
     */
    public BigDecimal getOneAgentCommission(Long orderId) {

        List<OrderProductPO> orderProductPO = orderProductDao.findByOrderId(orderId);

        // 单个产品的佣金
        BigDecimal oneCommission = new BigDecimal("0");

        for (OrderProductPO orderProduct : orderProductPO) {

            BigDecimal number = new BigDecimal(orderProduct.getNumber());

            AgentSchemePO agentScheme = agentSchemeDao.findByProductId(orderProduct.getProductId());

            // 一级代理佣金
            BigDecimal agentCommission = agentScheme.getAgentCommission();

            oneCommission = agentCommission.multiply(number).add(oneCommission);
        }
        return oneCommission;
    }

    /**
     * 获取二级代理佣金
     *
     * @param orderId
     * @return
     */
    public BigDecimal getTwoAgentCommission(Long orderId) {

        List<OrderProductPO> orderProductPO = orderProductDao.findByOrderId(orderId);

        // 单个产品的佣金
        BigDecimal towCommission = new BigDecimal("0");

        for (OrderProductPO orderProduct : orderProductPO) {

            BigDecimal number = new BigDecimal(orderProduct.getNumber());

            AgentSchemePO agentScheme = agentSchemeDao.findByProductId(orderProduct.getProductId());

            BigDecimal agentCommission = agentScheme.getAgentDeveloperCommission();

            towCommission = agentCommission.multiply(number).add(towCommission);
        }
        return towCommission;
    }

    /**
     * 获取一级渠道佣金
     *
     * @param orderId
     * @return
     */
    public BigDecimal countOneChannelCommission(Long orderId) {

        List<OrderProductPO> orderProductPO = orderProductDao.findByOrderId(orderId);

        // 一级渠道总佣金
        BigDecimal oneChannelCommission = new BigDecimal("0");

        for (OrderProductPO orderProduct : orderProductPO) {

            BigDecimal number = new BigDecimal(orderProduct.getNumber());

            System.out.println(orderProduct);

            ChannelSchemePO channelSchemePO = channelSchemeDao.findByProductIdAndLevel(orderProduct.getProductId(), 1);

            System.out.println(channelSchemePO);
            if (channelSchemePO == null) {
                throw new SCException(455, "渠道方案不存在");
            }

            BigDecimal profit = channelSchemePO.getProfit();

            oneChannelCommission = profit.multiply(number).add(oneChannelCommission);
        }

        return oneChannelCommission;
    }

    /**
     * 获取二级渠道佣金
     *
     * @param orderId
     * @param operationId
     * @return
     */
    public BigDecimal countTowChannelCommission(Long orderId, Integer operationId) {

        List<OrderProductPO> orderProductPO = orderProductDao.findByOrderId(orderId);

        // 渠道总佣金
        BigDecimal towChannelCommission = new BigDecimal("0");

        for (OrderProductPO orderProduct : orderProductPO) {

            BigDecimal number = new BigDecimal(orderProduct.getNumber());

            Integer productId = orderProduct.getProductId();

            ChannelSchemePO channelSchemePO = channelSchemeDao.findByProductIdAndLevelAndOperatorId(productId, 2, operationId);

            BigDecimal profit = channelSchemePO.getProfit();

            towChannelCommission = profit.multiply(number).add(towChannelCommission);
        }

        return towChannelCommission;
    }

    /**
     * 获取客户总佣金
     *
     * @param userId
     * @return
     */
    public BigDecimal getClientCommission(Integer userId) {

        List<Integer> clientList = commissionDao.queryCustomerTeam(userId, 0, 1);

        if (ObjectUtil.isEmpty(clientList)){
            return new BigDecimal("0");
        }

        List<CommissionPO> commissionList = commissionDao.findByUserIdIn(clientList);

        BigDecimal clientCommissions = new BigDecimal("0");

        // 客户佣金
        for (CommissionPO commissionPO : commissionList) {

            clientCommissions = clientCommissions.add(commissionPO.getProfits());
        }

        return clientCommissions;
    }

    /**
     * 获取代理总佣金
     *
     * @param userId
     * @return
     */
    public BigDecimal getAgentCommission(Integer userId) {

        List<Integer> agentList = commissionDao.queryAgentTeam(userId, 0, 1);

        if (ObjectUtil.isEmpty(agentList)){
            return new BigDecimal("0");
        }

        BigDecimal agentCommission = new BigDecimal("0");

        List<CommissionPO> commissionPOSList = commissionDao.findByUserIdIn(agentList);

        // 代理佣金
        for (CommissionPO commissionPO : commissionPOSList) {
            agentCommission = agentCommission.add(commissionPO.getProfits());
        }

        BigDecimal clientCommission = getClientCommission(userId);

        return agentCommission.add(clientCommission);
    }
    /**
     * 获取二级渠道总佣金
     *
     * @param userId
     * @return
     */
    public BigDecimal sumCommission(Integer userId, Integer role) {

        List<Integer> towChannelList = commissionDao.queryTeam(role, userId);

        BigDecimal channelCommission = new BigDecimal("0");

        if (ObjectUtil.isEmpty(towChannelList)){
            return new BigDecimal("0");
        }

        List<CommissionPO> commissionPOSList = commissionDao.findByUserIdIn(towChannelList);

        // 二级渠道总佣金
        for (CommissionPO commissionPO : commissionPOSList) {
            channelCommission = channelCommission.add(commissionPO.getProfits());
        }

        return channelCommission;
    }

    /**
     * 添加佣金数据
     *
     * @param userId
     * @param superiorId
     * @param role
     * @return
     */
    public Boolean addCommission(Integer userId, Integer superiorId, Integer role) {

        CommissionPO userCommission = commissionDao.findByUserId(userId);

        if (ObjectUtil.isNotNull(userCommission)){
            throw new SCException(400124, "用户佣金数据已存在");
        }

        CommissionPO commissionPO = new CommissionPO();

        CommissionPO commission = commissionDao.findByUserId(superiorId);

        if (commission != null) {
            commissionPO.setSecondLevel(commission.getSuperior());
        }

        commissionPO.setRole(role);
        commissionPO.setUserId(userId);
        commissionPO.setSuperior(superiorId);
        commissionPO.setStatus(SysConstant.STATUS_SHOW.getConstant());
        commissionPO.setCreateTime(new Date());
        commissionPO.setUpdateTime(new Date());

        return ObjectUtil.isNotEmpty(commissionDao.save(commissionPO));
    }

    public Map<String, Object> allotCommission(Integer userId, Long orderId) {

        Map<String, Object> map = new HashMap<>(4);

        // 1.查询(commission)用户上级superior和二级second_level
        CommissionPO commissionPO = commissionDao.findByUserId(userId);
        if (commissionPO == null) {
            throw new SCException(454, "佣金数据不存在");
        }
        // 用户上级superior
        Integer superior = commissionPO.getSuperior();
        // 二级second_level
        Integer secondLevel = commissionPO.getSecondLevel();

        // 查询用户身份
        int role = commissionPO.getRole();

        BigDecimal oneCommission = new BigDecimal("0");

        // 如果是代理或客户：获取代理佣金
        Integer oneRole = commissionDao.queryRole(superior);

        if (superior != null) {
            // 查询用户上级
            if (oneRole == 1) {
                oneCommission = getOneAgentCommission(orderId);
            } else if (oneRole == 2) {
                oneCommission = countOneChannelCommission(orderId);
            } else if (oneRole == 3) {
                oneCommission = countTowChannelCommission(orderId, superior);
            }
        }

        BigDecimal towCommission = new BigDecimal("0");

        if (secondLevel != null) {
            // 查询用户二级
            Integer towRole = commissionDao.queryRole(secondLevel);

            if (towRole == 1 || towRole == 3) {
                //代理
                towCommission = getTwoAgentCommission(orderId);
            } else if (oneRole == 3 && towRole == 2) {
                //一級渠道
                towCommission = countOneChannelCommission(orderId);
            }
        }

        // 根据用户身份分配佣金
        if (role == 0 || role == 1) {
            commissionDao.updateCommission(oneCommission, oneCommission, oneCommission, superior);
            commissionDao.updateCommission(towCommission, towCommission, towCommission, secondLevel);
        } else {
            oneCommission = new BigDecimal("0");
            towCommission = new BigDecimal("0");
        }

        map.put("superior", superior);
        map.put("secondLevel", secondLevel);
        map.put("role", role);
        map.put("oneCommission", oneCommission);
        map.put("towCommission", towCommission);

        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public void allotMemberCommission(Integer userId, OrderPO orderPO) {

        Integer memberLevel = orderPO.getLevel();

        // 根据用户id查询佣金数据
        CommissionPO commissionPO = commissionDao.findByUserId(userId);

        if (commissionPO.getRole() <= 1) {
            // 上级
            Integer superior = commissionPO.getSuperior();
            // 上上级
            Integer secondLevel = commissionPO.getSecondLevel();

            // 查询上级身份
            Integer role = commissionDao.queryRole(superior);

            // 分配上级佣金
            if (ObjectUtil.isNotEmpty(superior)) {
                // 初始化上级佣金
                BigDecimal superiorCommission = new BigDecimal(0);
                // 如果上级身份是代理或客户
                if (role == 1 || role == 0) {

                    AgentSchemePO agentSchemePO = agentSchemeDao.findByTypeAndMemberLevel(1, memberLevel);

                    if (ObjectUtil.isNotEmpty(agentSchemePO)) {
                        superiorCommission = agentSchemePO.getAgentCommission();
                    }
                } else if (role == 2 || role == 3) {

                    ChannelSchemePO channelSchemePO;
                    if (role == 2) {
                        // 如果上级身份是一级渠道
                        channelSchemePO = channelSchemeDao.queryMemberScheme(1, memberLevel,
                                1, null);
                    } else {
                        // 如果上级身份是二级渠道 一级渠道：secondLevel
                        channelSchemePO = channelSchemeDao.queryMemberScheme(1, memberLevel,
                                1, secondLevel);
                    }

                    if (ObjectUtil.isNotEmpty(channelSchemePO)) {
                        superiorCommission = channelSchemePO.getProfit();
                    }
                }

                commissionDao.updateCommission(superiorCommission, superiorCommission, superiorCommission, superior);

                //添加佣金的資金流水-訂單id user_id transaction_type：1，type：2 title:項目名 money：佣金金額
                flowRecordUtil.addFundFlow(userId, 1, 2, (long) orderPO.getId(), orderPO.getProductItem(), "佣金分配", superiorCommission);
            }

            if (ObjectUtil.isNotEmpty(secondLevel)) {

                // 初始化上上级佣金
                BigDecimal secondLevelCommission = new BigDecimal(0);

                Integer twoRole = commissionDao.queryRole(secondLevel);

                if (ObjectUtil.isNotEmpty(twoRole)) {

                    if (twoRole == 1 || twoRole == 3) {
                        //上上级如果是代理或者二级渠道，产生二级代理佣金
                        AgentSchemePO agentSchemePO = agentSchemeDao.findByTypeAndMemberLevel(1, memberLevel);

                        if (ObjectUtil.isNotEmpty(agentSchemePO)) {
                            secondLevelCommission = agentSchemePO.getAgentCommission();
                        }

                    } else if (role == 3 && twoRole == 2) {
                        //上級是二級渠道並且上上级是一级渠道，产生一级渠道佣金
                        ChannelSchemePO channelSchemePO = channelSchemeDao.queryMemberScheme(1, memberLevel,
                                1, null);

                        if (ObjectUtil.isNotEmpty(channelSchemePO)) {
                            secondLevelCommission = channelSchemePO.getProfit();
                        }
                    }
                }

                commissionDao.updateCommission(secondLevelCommission, secondLevelCommission, secondLevelCommission, secondLevel);

                //添加佣金的資金流水 fund_flow
                flowRecordUtil.addFundFlow(userId, 1, 2, (long) orderPO.getId(), orderPO.getProductItem(), "佣金分配", secondLevelCommission);
            }
        }
    }
}