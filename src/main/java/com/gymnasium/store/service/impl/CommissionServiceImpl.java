package com.gymnasium.store.service.impl;

import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.CommissionUtil;
import com.gymnasium.Util.GeneralUtils;
import com.gymnasium.Util.SCException;
import com.gymnasium.store.Dao.CommissionDao;
import com.gymnasium.store.PO.CommissionPO;
import com.gymnasium.store.VO.CommissionVO;
import com.gymnasium.store.service.CommissionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/29 20:22
 * @Description:
 */
@Service
public class CommissionServiceImpl implements CommissionService {

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private CommissionUtil commissionUtil;

    @Override
    public Map<String, Object> countCommission(Integer userId) {

        Map<String, Object> map = new HashMap<>(16);

        BigDecimal clientCommission = commissionUtil.getClientCommission(userId);
        BigDecimal agentCommission = commissionUtil.getAgentCommission(userId);

        map.put("clientCommissions", clientCommission);

        map.put("agentCommission", agentCommission);

        return map;
    }

    @Override
    public Map<String, Integer> channelStatistics(Integer userId) {

        return commissionDao.teamStatistics(userId,3);
    }

    @Override
    public Map<String, Object> queryWithdraw(Integer agentId) {

        Map<String, Object> map = new HashMap<>(16);

        CommissionPO commissionPO = commissionDao.findByUserIdAndRole(agentId, 1);

        if (GeneralUtils.isEmpty(commissionPO)){

            throw new SCException(40010,"代理人不存在");
        }

        map.put("cashed",commissionPO.getCashed());

        map.put("canCarry",commissionPO.getCanCarry());

        return map;
    }

    @Override
    public CommissionVO queryCommission(Integer userId) {

        CommissionVO commissionVO = new CommissionVO();

        CommissionPO commissionPO = commissionDao.findByUserId(userId);

        if (ObjectUtils.anyNotNull(commissionPO)){
            BeanUtil.copyProperties(commissionPO,commissionVO);
            return commissionVO;
        }

        return null;
    }
}


