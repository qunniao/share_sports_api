package com.gymnasium.Util;

import com.gymnasium.order.DAO.ExpensesRecordDao;
import com.gymnasium.order.PO.ExpensesRecordPO;
import com.gymnasium.store.Dao.CashFlowDao;
import com.gymnasium.store.Dao.FundFlowDao;
import com.gymnasium.store.PO.CashFlowPO;
import com.gymnasium.store.PO.FundFlowPO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/24 17:06
 * @Description:
 */
@Component
public class FlowRecordUtil {

    @Autowired
    private FundFlowDao fundFlowDao;

    @Autowired
    private CashFlowDao cashFlowDao;

    @Autowired
    private ExpensesRecordDao expensesRecordDao;

    /**
     * 添加资金流水
     * @return
     */
    public Boolean addFundFlow(Integer userId, Integer transactionType, Integer type, Long orderId, String title,
                               String content, BigDecimal money){

        // 流水号
        String flowNum = "F" + RandomUtil.getShortUuid();

        FundFlowPO fundFlowPO = new FundFlowPO();
        fundFlowPO.setUserId(userId);
        fundFlowPO.setFlowNumber(flowNum);
        fundFlowPO.setTransactionType(transactionType);
        fundFlowPO.setType(type);
        fundFlowPO.setOrderId(orderId);
        fundFlowPO.setTitle(title);
        fundFlowPO.setContent(content);
        fundFlowPO.setMoney(money);
        fundFlowPO.setCreateTime(new Date());
        fundFlowPO.setUpdateTime(new Date());
        fundFlowPO.setStatus(1);

        fundFlowDao.save(fundFlowPO);

        return ObjectUtils.anyNotNull(fundFlowPO.getId());
    }

    public Boolean addFundFlow(Integer userId, Integer transactionType, Integer type, Long orderId, String title,
                               String content, BigDecimal money, String transactionId){

        FundFlowPO fundFlowPO = new FundFlowPO();
        fundFlowPO.setUserId(userId);
        fundFlowPO.setFlowNumber(transactionId);
        fundFlowPO.setTransactionType(transactionType);
        fundFlowPO.setType(type);
        fundFlowPO.setCreateTime(new Date());
        fundFlowPO.setUpdateTime(new Date());
        fundFlowPO.setOrderId(orderId);
        fundFlowPO.setTitle(title);
        fundFlowPO.setContent(content);
        fundFlowPO.setMoney(money);
        fundFlowPO.setStatus(1);

        fundFlowDao.save(fundFlowPO);

        return ObjectUtils.anyNotNull(fundFlowPO.getId());
    }

    /**
     * 添加佣金流水
     * @return
     */
    public Boolean addCashFlow(Integer userId, String orderNumber, String title,
                               String content, BigDecimal money, Integer type, Integer role,
                               Long orderId, Integer superior, Integer secondLevel, Integer way,
                               BigDecimal clientCommission, BigDecimal agentCommission){
        // 佣金流水号
        String cashNumber = "M" +  RandomUtil.getShortUuid();

        CashFlowPO cashFlowPO = new CashFlowPO();
        cashFlowPO.setUserId(userId);
        cashFlowPO.setOrderNumber(orderNumber);
        cashFlowPO.setFlowNumber(cashNumber);
        cashFlowPO.setTitle(title);
        cashFlowPO.setContent(content);
        cashFlowPO.setMoney(money);
        cashFlowPO.setType(type);
        cashFlowPO.setRole(role);
        cashFlowPO.setOrderId(orderId);
        cashFlowPO.setSuperior(superior);
        cashFlowPO.setSecondLevel(secondLevel);
        cashFlowPO.setWay(way);
        cashFlowPO.setClientCommission(clientCommission);
        cashFlowPO.setAgentCommission(agentCommission);
        cashFlowPO.setUpdateTime(new Date());
        cashFlowPO.setCreateTime(new Date());
        cashFlowPO.setStatus(1);

        cashFlowDao.save(cashFlowPO);

        return ObjectUtils.anyNotNull(cashFlowPO.getId());
    }

    public Boolean addExpensesRecord(String userId, Double energy, Double surplusPrice, Integer type, Integer titleType,
                                     Integer itemType, String caption, String remarks, String gymShopId){

        ExpensesRecordPO expensesRecordPO = new ExpensesRecordPO();

        String uexId = RandomUtil.getShortUuid();

        expensesRecordPO.setUexId(uexId);
        expensesRecordPO.setUserId(userId);
        expensesRecordPO.setEnergy(energy);
        expensesRecordPO.setType(type);
        expensesRecordPO.setTitleType(titleType);
        expensesRecordPO.setSurplusPrice(surplusPrice);
        expensesRecordPO.setCaption(caption);
        expensesRecordPO.setRemarks(remarks);
        expensesRecordPO.setOperatingTime(new Timestamp(System.currentTimeMillis()));
        expensesRecordPO.setItemType(itemType);
        expensesRecordPO.setShopId(gymShopId);

        expensesRecordDao.save(expensesRecordPO);

        return ObjectUtils.anyNotNull(expensesRecordPO.getId());

    }
}
