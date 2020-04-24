package com.gymnasium.card.Service;

import cn.hutool.db.sql.Order;
import com.gymnasium.card.Dao.ActivationRecordDao;
import com.gymnasium.card.PO.ActivationPO;
import com.gymnasium.card.PO.ActivationRecordPO;
import com.gymnasium.card.vo.ActivationRecordVO;
import com.gymnasium.core.page.Paging;
import com.gymnasium.order.PO.OrderPO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 王志鹏
 * @title: ActivationService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 10:46
 */

public interface ActivationService {

    //添加激活卡
    boolean addActivation(ActivationPO activationPO);

    //购买激活卡
    boolean buyActivationCard(String userId);

    //激活 激活卡
    boolean activationCard(ActivationRecordVO activationRecordVO, String code);

    Boolean verifyCard(String cardNum, String password);

    //退卡(回购)
    boolean backActivationCard(String userId, String cardNum, String password);

    Page<ActivationPO> queryMemberCard(Paging page, Integer orderId);

    /**
     * 查询我的会员最新一条激活记录
     * @param useUserId
     * @return
     */
    ActivationRecordPO queryNewestRecord(String useUserId);
}
