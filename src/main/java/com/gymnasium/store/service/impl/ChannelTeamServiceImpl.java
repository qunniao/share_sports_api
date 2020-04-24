package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.GeneralUtils;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.Dao.CashFlowDao;
import com.gymnasium.store.Dao.CommissionDao;
import com.gymnasium.store.Dao.ShopOrderDao;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.store.PO.ShopOrderPO;
import com.gymnasium.store.service.ChannelTeamService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/4 15:11
 * @Description:
 */
@Service
public class ChannelTeamServiceImpl implements ChannelTeamService {

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private CashFlowDao cashFlowDao;

    @Override
    public Page<ShopOrderPO> queryTowOrder(Integer userId, Integer role, Integer orderState, Paging page) {

        // 二级团队客户id
        List<Integer> userIdList = new ArrayList<>();

        // 查询二级团队id
        List<Integer> channelList = commissionDao.queryUser(3,userId);

        if (CollectionUtils.isEmpty(channelList)){
            return null;
        }

        // 查询二级团队客户：superior in(二级团队id) && role=0
        List<CommissionPO> commissionList = commissionDao.findAllByRoleAndSuperiorIn(0, channelList);

        if (CollectionUtils.isEmpty(commissionList)){
            return null;
        }

        // 遍历二级团队客户 取出userId
        for (CommissionPO commissionPO: commissionList){
            userIdList.add(commissionPO.getUserId());
        }

        if (CollectionUtils.isEmpty(userIdList)){
            return  null;
        }

        if (GeneralUtils.isEmpty(role) && role != 2){
            for (Integer id :channelList){
                userIdList.add(id);
            }
        }

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<ShopOrderPO> shopOrderPO = shopOrderDao.findAll(new Specification<ShopOrderPO>() {

            @Override
            public Predicate toPredicate(Root<ShopOrderPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (ObjectUtils.anyNotNull(orderState)){

                    list.add(criteriaBuilder.equal(root.get("orderState").as(Integer.class), orderState));
                }

                if (role == 2){
                    Expression<String> exp = root.get("userId");

                    list.add(exp.in(userId));
                }else {
                    Expression<String> exp = root.get("userId");

                    list.add(exp.in(userIdList));
                }

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return shopOrderPO;
    }

    @Override
    public BigDecimal queryTotalCommission(Integer channelId) {

        List<Integer> userIdList = new ArrayList<>();

        List<Integer> channelList = new ArrayList<>();

        // 查询二级团队
        List<CommissionPO> commissionPOList = commissionDao.findAllBySuperior(channelId);

        if (CollectionUtils.isEmpty(commissionPOList)){
            return  null;
        }

        for (CommissionPO commissionPO: commissionPOList){

            if (commissionPO.getRole() != 0){
                userIdList.add(commissionPO.getUserId());
            }else {
                channelList.add(commissionPO.getUserId());
            }
        }

        if (ObjectUtils.anyNotNull(channelList)){

            List<CommissionPO> commissionPOList1 = commissionDao.findAllByRoleAndSuperiorIn(0, channelList);

            for (CommissionPO commissionPO : commissionPOList1){
                userIdList.add(commissionPO.getUserId());
            }
        }

        if (CollectionUtils.isEmpty(userIdList)){
            return  null;
        }

        BigDecimal clientCommission = cashFlowDao.sumClientCommission(channelId, 0);
        BigDecimal agentCommission = cashFlowDao.sumAgentCommission(channelId, 0);

        if (ObjectUtils.anyNotNull(clientCommission)){

            return clientCommission.add(agentCommission);
        }

        return agentCommission;
    }
}
