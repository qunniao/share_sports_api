package com.gymnasium.order.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.FlowRecordUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.order.DAO.WithdrawalDao;
import com.gymnasium.order.PO.WithdrawalPO;
import com.gymnasium.order.Service.WithdrawalService;
import com.gymnasium.store.Dao.CommissionDao;
import com.gymnasium.store.PO.CommissionPO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/8 09:38
 * @Description:
 */
@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private WithdrawalDao withdrawalDao;

    @Autowired
    private FlowRecordUtil flowRecordUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean requestWithdrawal(WithdrawalPO withdrawalPO) {

        // 提现金额
        BigDecimal amount = withdrawalPO.getAmount();

        CommissionPO commissionPO = commissionDao.findByUserId(withdrawalPO.getUserId());

        if (!ObjectUtils.anyNotNull(commissionPO)){
            throw new SCException(40110,"用户佣金数据不存在");
        }
        // 可提现金额
        BigDecimal canMoney = commissionPO.getCanCarry();

        if ((canMoney).compareTo(amount) == -1){
            throw new SCException(40111,"提现金额不能大于可提现金额");
        }

        withdrawalPO.setStatus(1);
        withdrawalPO.setReviewState(0);
        withdrawalDao.save(withdrawalPO);

        commissionPO.setCanCarry(canMoney.subtract(amount));

        CommissionPO save = commissionDao.save(commissionPO);

        return ObjectUtil.isNotNull(save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean auditWithdraw(Integer id, Integer reviewState, String refuse) {

        WithdrawalPO withdrawalPO = withdrawalDao.findWithdrawalPOById(id);

        if (StringUtils.isEmpty(withdrawalPO)){
            throw new SCException(ResultEnum.NO_DATA_EXIST);

        // 同意提现 添加提现记录
    }
        if (reviewState == 1){

            flowRecordUtil.addFundFlow(withdrawalPO.getUserId(),2,3,null,"佣金提现",
                    "佣金提现审核通过",withdrawalPO.getAmount());
        }
        // 拒绝 转账，返还佣金
        else if (reviewState == 2){

            // 提现金额返还
            BigDecimal amount = withdrawalPO.getAmount();

            CommissionPO commissionPO = commissionDao.findByUserId(withdrawalPO.getUserId());

            if (StringUtils.isEmpty(commissionPO)){
                throw new SCException(ResultEnum.NO_DATA_EXIST);
            }
            BigDecimal add = amount.add(commissionPO.getCanCarry());

            //返还佣金
            commissionPO.setCanCarry(add);

            commissionDao.save(commissionPO);

            withdrawalPO.setRefuse(refuse);
        }

        withdrawalPO.setReviewState(reviewState);
        WithdrawalPO save = withdrawalDao.save(withdrawalPO);

        return ObjectUtils.anyNotNull(save);
    }
}
