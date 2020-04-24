package com.gymnasium.store.service.impl;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.RandomUtil;
import com.gymnasium.store.Dao.CashFlowDao;
import com.gymnasium.store.PO.CashFlowPO;
import com.gymnasium.store.VO.CashFlowVO;
import com.gymnasium.store.service.CashFlowService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/31 09:48
 * @Description:
 */
@Service
public class CashFlowServiceImpl implements CashFlowService {

    @Autowired
    private CashFlowDao cashFlowDao;

    @Override
    public Boolean insertCashFlow(CashFlowVO cashFlowVO) {

        CashFlowPO cashFlowPO = new CashFlowPO();

        BeanUtil.copyProperties(cashFlowVO, cashFlowPO);

        String number = "M" + System.currentTimeMillis() + RandomUtil.getRandom_Six();

        cashFlowPO.setFlowNumber(number);

        cashFlowPO.setCreateTime(new Date());

        cashFlowPO.setStatus(SysConstant.STATUS_SHOW.getConstant());

        CashFlowPO save = cashFlowDao.save(cashFlowPO);

        if (save == null){
            return false;
        }
        return true;
    }

    @Override
    public CashFlowVO queryCashFlow(Long orderId) {

        CashFlowPO cashFlowPO = cashFlowDao.findByOrderId(orderId);

        CashFlowVO cashFlowVO = new CashFlowVO();

        if (ObjectUtils.anyNotNull(cashFlowPO)){
            BeanUtils.copyProperties(cashFlowPO,cashFlowVO);
        }

        return cashFlowVO;
    }
}
