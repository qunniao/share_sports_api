package com.gymnasium.card.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.DateUtil;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.GeneralUtils;
import com.gymnasium.Util.SCException;
import com.gymnasium.card.Dao.ActivationDao;
import com.gymnasium.card.Dao.ActivationRecordDao;
import com.gymnasium.card.PO.ActivationPO;
import com.gymnasium.card.PO.ActivationRecordPO;
import com.gymnasium.card.Service.ActivationService;
import com.gymnasium.card.vo.ActivationRecordVO;
import com.gymnasium.core.page.Paging;
import com.gymnasium.data.Service.CodelogService;
import com.gymnasium.order.DAO.OrderDao;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: ActivationServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/29 10:52
 */

@Service
public class ActivationServiceImpl implements ActivationService {

    @Autowired
    private ActivationDao activationDao;

    @Autowired
    private ActivationRecordDao activationRecordDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CodelogService codelogService;

    @Autowired
    private OrderDao orderDao;

    @Override
    public boolean addActivation(ActivationPO activationPO) {

        activationPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        activationDao.save(activationPO);
        return true;
    }

    @Override
    public boolean buyActivationCard(String userId) {
        UserPO userPO = userDao.findByUserId(userId);

        if(userPO==null){
            throw new SCException(ResultEnum.USER_REPEAT);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean activationCard(ActivationRecordVO activationRecordVO, String code) {

        if (!codelogService.validCode(activationRecordVO.getPhone(), code)) {
            throw new SCException(ResultEnum.CODE_TIME_OUT);
        }

        // 根据卡号和密码查询会员卡
        ActivationPO activationPO = activationDao.findByCardNumAndCardPassWord(activationRecordVO.getCardNum(),
                activationRecordVO.getCardPassword());

        if (GeneralUtils.isNull(activationPO)){
            throw new SCException(400701,"卡号或密码错误,激活会员卡失败");
        }

        ActivationRecordPO activationRecordPO = activationRecordDao.findByCardNum(activationRecordVO.getCardNum());

        if (GeneralUtils.isNull(activationRecordPO)){
            throw new SCException(400702,"会员卡订单不存在,激活会员卡失败");
        }

        if (activationRecordPO.getType() == 1){
            throw new SCException(45455, "会员卡已激活");
        }

        BeanUtil.copyProperties(activationRecordVO, activationRecordPO);

        // 订单状态:0待激活,1激活,2退卡
        activationRecordPO.setCardId(activationPO.getCid());
        activationRecordPO.setType(1);
        activationRecordPO.setCardNum(activationRecordVO.getCardNum());
        activationRecordPO.setOperTime(new Timestamp(System.currentTimeMillis()));

        ActivationRecordPO save = activationRecordDao.save(activationRecordPO);

        return ObjectUtils.anyNotNull(save);
    }

    @Override
    public Boolean verifyCard(String cardNum, String password) {

        ActivationPO activationPO = activationDao.findByCardNumAndCardPassWord(cardNum, password);

        if (ObjectUtil.isNotNull(activationPO)){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public boolean backActivationCard(String userId, String cardNum, String password) {

        return true;
    }

    @Override
    public Page<ActivationPO> queryMemberCard(Paging page, Integer orderId) {

        Pageable pageable = PageRequest.of(page.getPageNum()-1, page.getPageSize());

        Page<ActivationPO> pageActivationPO = activationDao.findAll(new Specification<ActivationPO>() {

            @Override
            public Predicate toPredicate(Root<ActivationPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (ObjectUtil.isNotEmpty(orderId)) {
                    list.add(criteriaBuilder.equal(root.get("orderId").as(Integer.class), orderId));
                }

                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return pageActivationPO;
    }

    @Override
    public ActivationRecordPO queryNewestRecord(String useUserId) {

        return activationRecordDao.queryNewestRecord(useUserId);
    }
}
