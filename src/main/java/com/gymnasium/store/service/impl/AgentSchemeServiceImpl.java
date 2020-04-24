package com.gymnasium.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.SnowFlakeIdGenerator;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.Dao.AgentSchemeDao;
import com.gymnasium.store.PO.AgentSchemePO;
import com.gymnasium.store.VO.AgentSchemeVO;
import com.gymnasium.store.service.AgentSchemeService;
import javafx.scene.Scene;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:26
 * @Description:
 */
@Service
public class AgentSchemeServiceImpl implements AgentSchemeService {

    @Autowired
    private AgentSchemeDao agentSchemeDao;

    @Override
    public AgentSchemeVO editAgentScheme(AgentSchemeVO agentSchemeVO) {

        AgentSchemePO agentSchemePO = new AgentSchemePO();

        BeanUtil.copyPropertie(agentSchemeVO, agentSchemePO);

        AgentSchemePO agentScheme;

        if (agentSchemeVO.getType() ==1) {
            //查询会员方案
            agentScheme = agentSchemeDao.findByProductId(agentSchemeVO.getMemberLevel());

        } else {
            //查询产品方案
            agentScheme = agentSchemeDao.findByProductId(agentSchemeVO.getProductId());
        }

        if (agentScheme == null){

            String code  = "A" + RandomUtil.randomString(7);

            agentSchemePO.setAgentCode(code);
            agentSchemePO.setCreateTime(new Date());

            agentSchemeDao.save(agentSchemePO);
        }else {

            BeanUtil.copyPropertie(agentSchemeVO,agentScheme);
            agentScheme.setUpdateTime(new Date());

            agentSchemeDao.save(agentScheme);
        }

        BeanUtil.copyPropertie(agentSchemePO, agentSchemeVO);

        return agentSchemeVO;
    }

    @Override
    public Boolean editScheme(AgentSchemeVO agentSchemeVO) {

        AgentSchemePO agentSchemePO = agentSchemeDao.findAgentSchemePOById(agentSchemeVO.getId());

        if (ObjectUtil.isEmpty(agentSchemePO)){
            throw new SCException(450,"代理方案不存在");
        }

        BeanUtil.copyProperties(agentSchemeVO, agentSchemePO);
        agentSchemePO.setUpdateTime(new Date());

        return ObjectUtil.isNotEmpty(agentSchemeDao.save(agentSchemePO));
    }

    @Override
    public Boolean updateAgentScheme(AgentSchemeVO agentSchemeVO) {

        AgentSchemePO agentSchemePO = agentSchemeDao.findAgentSchemePOById(agentSchemeVO.getId());

        if (agentSchemePO == null) {

            throw new SCException(450,"代理方案不存在");
        }

        BeanUtil.copyProperties(agentSchemeVO,agentSchemePO);
        agentSchemePO.setUpdateTime(new Date());

        agentSchemeDao.save(agentSchemePO);

        return true;
    }

    @Override
    public Page<AgentSchemePO> pageAgentScheme(Paging page, AgentSchemeVO agentSchemeVO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<AgentSchemePO> agentSchemePOS = agentSchemeDao.findAll((Specification<AgentSchemePO>) (root, query, criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            if (ObjectUtils.anyNotNull(agentSchemeVO.getAgentCode())) {
                list.add(criteriaBuilder.like(root.get("agentCode"), "%" + agentSchemeVO.getAgentCode() + "%"));
            }

            if (ObjectUtil.isNotNull(agentSchemeVO.getAgentDeveloperCommission())) {
                list.add(criteriaBuilder.equal(root.get("agentDeveloperCommission").as(BigDecimal.class),
                        agentSchemeVO.getAgentDeveloperCommission()));
            }

            if (ObjectUtil.isNotNull(agentSchemeVO.getAgentCommission())) {
                list.add(criteriaBuilder.equal(root.get("agentCommission").as(BigDecimal.class),
                        agentSchemeVO.getAgentCommission()));
            }

            if (ObjectUtil.isNotNull(agentSchemeVO.getMemberLevel())) {
                list.add(criteriaBuilder.equal(root.get("memberLevel").as(Integer.class),
                        agentSchemeVO.getMemberLevel()));
            }

            if (ObjectUtil.isNotNull(agentSchemeVO.getType())) {
                list.add(criteriaBuilder.equal(root.get("type").as(Integer.class),
                        agentSchemeVO.getType()));
            }

            if (ObjectUtil.isNotNull(agentSchemeVO.getProductId())) {
                list.add(criteriaBuilder.equal(root.get("productId").as(Integer.class),
                        agentSchemeVO.getProductId()));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);

        return agentSchemePOS;
    }

    @Override
    public List<AgentSchemePO> queryAgentScheme(String name, Integer type) {

        return agentSchemeDao.queryAgentScheme(name,type);
    }

    @Override
    public AgentSchemeVO queryByProductId(Integer productId) {

        AgentSchemePO agentSchemePO = agentSchemeDao.findByProductId(productId);


        if (ObjectUtil.isNull(agentSchemePO)){

            throw new SCException(400451, "代理方案不存在");
        }

        AgentSchemeVO agentSchemeVO = new AgentSchemeVO();

        BeanUtil.copyProperties(agentSchemePO, agentSchemeVO);

        return agentSchemeVO;
    }
}
