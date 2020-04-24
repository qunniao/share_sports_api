package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.ChannelSchemeUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.SnowFlakeIdGenerator;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.Dao.ChannelSchemeDao;
import com.gymnasium.store.PO.ChannelSchemePO;
import com.gymnasium.store.VO.AgentSchemeVO;
import com.gymnasium.store.VO.ChannelSchemeVO;
import com.gymnasium.store.service.ChannelSchemeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 14:32
 * @Description:
 */
@Service
public class ChannelSchemeServiceImpl implements ChannelSchemeService {

    @Autowired
    private ChannelSchemeDao channelSchemeDao;

    @Autowired ChannelSchemeUtil channelSchemeUtil;

    @Override
    public ChannelSchemeVO editOneLevel(ChannelSchemeVO channelSchemeVO) {

        ChannelSchemePO channelScheme = channelSchemeDao.findByProductId(channelSchemeVO.getProductId());

        ChannelSchemePO channelSchemePO = channelSchemeUtil.getChannelSchemePO(channelSchemeVO, channelScheme, 1);

        BeanUtil.copyPropertie(channelSchemePO,channelSchemeVO);

        return channelSchemeVO;

    }

    @Override
    public ChannelSchemeVO editSecondLevel(ChannelSchemeVO channelSchemeVO) {

        ChannelSchemePO channelScheme = channelSchemeDao.findByUserIdAndProductIdAndOperatorId(
                channelSchemeVO.getUserId(),channelSchemeVO.getProductId(),channelSchemeVO.getOperatorId());

        ChannelSchemePO channelSchemePO = channelSchemeUtil.getChannelSchemePO(channelSchemeVO, channelScheme, 2);

        BeanUtil.copyPropertie(channelSchemePO,channelSchemeVO);

        return channelSchemeVO;
    }

    @Override
    public ChannelSchemeVO findByUserIdAndProductId(Integer userId, Integer productId) {

        ChannelSchemeVO channelSchemeVO = new ChannelSchemeVO();

        ChannelSchemePO channelSchemePO = channelSchemeDao.findByUserIdAndProductId(userId, productId);

        if (ObjectUtil.isNull(channelSchemePO)){
            throw new SCException(400457, "渠道方案不存在");
        }

        System.err.println(channelSchemePO);

        BeanUtil.copyPropertie(channelSchemePO,channelSchemeVO);

        System.err.println(channelSchemeVO);

        return channelSchemeVO;
    }

    @Override
    public ChannelSchemeVO findByChannelCode(String channelCode) {

        ChannelSchemeVO channelSchemeVO = new ChannelSchemeVO();

        ChannelSchemePO channelSchemePO = channelSchemeDao.findByChannelCode(channelCode);

        BeanUtil.copyPropertie(channelSchemePO,channelSchemeVO);

        return channelSchemeVO;

    }

    @Override
    public ChannelSchemePO findTowProductScheme(Integer operatorId, Integer productId) {

        return channelSchemeDao.findByOperatorIdAndProductId(operatorId,productId);
    }

    @Override
    public Page<ChannelSchemePO> pageOneChannelScheme(Paging page,Integer type) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        if (ObjectUtil.isNotNull(type)){
            return channelSchemeDao.findByLevelAndType(1, type, pageable);
        }

        return channelSchemeDao.findAllByLevel(1,pageable);
    }

    @Override
    public Page<ChannelSchemePO> pageTowChannelScheme(Paging page, Integer userId,Integer type) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        if (ObjectUtil.isNotNull(type)){
            return  channelSchemeDao.findAllByUserIdAndLevelAndType(userId, 2, type, pageable);
        }

        return channelSchemeDao.findAllByUserIdAndLevel(userId, 2, pageable);
    }

    @Override
    public Boolean updateChannelScheme(ChannelSchemeVO channelSchemeVO) {

        ChannelSchemePO channelSchemePO = channelSchemeDao.findChannelSchemePOById(channelSchemeVO.getId());

        if (channelSchemePO == null) {

            throw new SCException(450,"渠道方案不存在");
        }

        channelSchemePO.setProfit(channelSchemeVO.getProfit());
        channelSchemePO.setUpdateTime(new Date());

        channelSchemeDao.save(channelSchemePO);

        return true;

    }

    @Override
    public List<ChannelSchemePO> queryChannelScheme(ChannelSchemeVO channelSchemeVO, String name) {

        return channelSchemeDao.queryChannelScheme(name, channelSchemeVO.getType(), channelSchemeVO.getLevel(),
                channelSchemeVO.getOperatorId());

    }

    @Override
    public List<ChannelSchemePO> queryAllChannelScheme(Integer type, Integer level, Integer userId) {

        List<ChannelSchemePO> channelSchemeDaoAll = channelSchemeDao.findAll(new Specification<ChannelSchemePO>() {

            @Override
            public Predicate toPredicate(Root<ChannelSchemePO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();

                if (ObjectUtil.isNotEmpty(type)){
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
                }

                if (ObjectUtil.isNotEmpty(level)){
                    list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), level));
                }

                if (ObjectUtil.isNotEmpty(userId)){
                    list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), userId));
                }

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        });

        return channelSchemeDaoAll;
    }

    @Override
    public boolean editChannelScheme(ChannelSchemeVO channelSchemeVO) {

        ChannelSchemePO channelScheme = channelSchemeDao.findByUserIdAndLevelAndType(channelSchemeVO.getId(), channelSchemeVO.getLevel(), channelSchemeVO.getType());

        if (ObjectUtil.isEmpty(channelScheme)){

            ChannelSchemePO channelSchemePO = new ChannelSchemePO();

            BeanUtil.copyProperties(channelSchemeVO, channelSchemePO);

            String code  = "C" + SnowFlakeIdGenerator.nextId();

            channelSchemePO.setChannelCode(code);
            channelSchemePO.setCreateTime(new Date());

            return ObjectUtil.isNotEmpty(channelSchemeDao.save(channelSchemePO));
        }

        BeanUtil.copyProperties(channelSchemeVO, channelScheme);

        channelScheme.setUpdateTime(new Date());

        return ObjectUtil.isNotEmpty(channelSchemeDao.save(channelScheme));
    }

    @Override
    public ChannelSchemeVO queryByProductId(Integer productId) {

        ChannelSchemePO channelSchemePO = channelSchemeDao.findByProductIdAndLevel(productId, 1);

        if (ObjectUtil.isNull(channelSchemePO)){

            throw new SCException(400451, "一级渠道方案不存在");
        }

        ChannelSchemeVO channelSchemeVO = new ChannelSchemeVO();

        BeanUtil.copyProperties(channelSchemePO, channelSchemeVO);

        return channelSchemeVO;
    }
}
