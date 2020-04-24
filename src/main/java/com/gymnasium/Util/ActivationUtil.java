package com.gymnasium.Util;

import com.gymnasium.card.Dao.ActivationDao;
import com.gymnasium.card.Dao.ActivationRecordDao;
import com.gymnasium.card.PO.ActivationPO;
import com.gymnasium.card.PO.ActivationRecordPO;
import com.gymnasium.order.PO.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/28 15:19
 * @Description:
 */
@Component
public class ActivationUtil {

    @Autowired
    private ActivationDao activationDao;

    @Autowired
    private ActivationRecordDao activationRecordDao;

    @Transactional(rollbackFor = Exception.class)
    public ActivationPO addActivation(Integer orderId, Integer level){

        ActivationPO activationPO = new ActivationPO();

        // 生成卡号
        String cardNumber = RandomUtil.getRandomStringByLength(1) + SnowFlakeIdGenerator.nextId();

        // 生成密码
        String cardPwd= RandomUtil.getShortUuid();

        activationPO.setCardNum(cardNumber);
        activationPO.setCardPassWord(cardPwd);
        activationPO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        activationPO.setStatus(0);
        activationPO.setOrderId(orderId);
        activationPO.setType(level);

        return activationDao.save(activationPO);
    }

    @Transactional(rollbackFor = Exception.class)
    public ActivationRecordPO addActivationRecord(Integer payType, String userId, String cardNumber){

        ActivationRecordPO activationRecordPO = new ActivationRecordPO();

        activationRecordPO.setPayType(payType);
        activationRecordPO.setType(0);
        activationRecordPO.setBuyUserId(userId);
        activationRecordPO.setCardNum(cardNumber);

        return activationRecordDao.save(activationRecordPO);
    }
}
