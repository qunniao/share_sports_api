package com.gymnasium.store.service.impl;

import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.BeanUtil;
import com.gymnasium.Util.GeneralUtils;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.Dao.FundFlowDao;
import com.gymnasium.store.PO.FundFlowPO;
import com.gymnasium.store.VO.FundFlowRequestVO;
import com.gymnasium.store.VO.FundFlowVO;
import com.gymnasium.store.service.FundFlowService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/31 14:43
 * @Description:
 */
@Service
public class FundFlowServiceImpl implements FundFlowService {

    @Autowired
    private FundFlowDao fundFlowDao;

    @Override
    public Boolean insertFundFlow(FundFlowVO fundFlowVO) {

        FundFlowPO fundFlowPO = new FundFlowPO();

        BeanUtil.copyProperties(fundFlowVO, fundFlowPO);

        fundFlowPO.setCreateTime(new Date());
        fundFlowPO.setUpdateTime(new Date());
        fundFlowPO.setStatus(SysConstant.STATUS_SHOW.getConstant());

        FundFlowPO save = fundFlowDao.save(fundFlowPO);

        return save != null;
    }

    @Override
    public Page<FundFlowPO> pageAllByStatus(Paging page, FundFlowVO fundFlowVO, FundFlowRequestVO fundFlowRequestVO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<FundFlowPO> pageFundFlow = fundFlowDao.findAll(new Specification<FundFlowPO>() {
            @Override
            public Predicate toPredicate(Root<FundFlowPO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (GeneralUtils.notEmpty(fundFlowVO.getFlowNumber())) {
                    list.add(criteriaBuilder.equal(root.get("flowNumber").as(String.class),
                            fundFlowVO.getFlowNumber()));
                }

                if (GeneralUtils.notEmpty(fundFlowVO.getTransactionType())) {
                    list.add(criteriaBuilder.equal(root.get("transactionType").as(Integer.class),
                            fundFlowVO.getTransactionType()));
                }

                if (GeneralUtils.notEmpty(fundFlowVO.getType())) {
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), fundFlowVO.getType()));
                }

                if (StringUtils.isNotBlank(fundFlowRequestVO.getStartTime())) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class),
                            fundFlowRequestVO.getStartTime()));
                }

                if (StringUtils.isNotBlank(fundFlowRequestVO.getEndTime())) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class),
                            fundFlowRequestVO.getEndTime()));
                }

                if (StringUtils.isNotBlank(fundFlowRequestVO.getMinMoney())) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("money").as(String.class),
                            fundFlowRequestVO.getMinMoney()));
                }

                if (StringUtils.isNotBlank(fundFlowRequestVO.getMaxMoney())) {
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("money").as(String.class),
                            fundFlowRequestVO.getMaxMoney()));
                }

                if (StringUtils.isNotBlank(fundFlowVO.getTitle())) {

                    list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + fundFlowVO.getTitle() +
                            "%"));
                }

                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class),
                        SysConstant.STATUS_SHOW.getConstant()));

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return pageFundFlow;
    }

    @Override
    public Page<FundFlowPO> queryFlowRecord(Paging page, Integer userId) {

        Pageable pageable = PageRequest.of(page.getPageNum()-1, page.getPageSize());

        return fundFlowDao.findByUserIdAndType(pageable, userId, 3);
    }

    @Override
    public Page<FundFlowPO> pageWithdrawalRecord(Paging page, FundFlowVO fundFlowVO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<FundFlowPO> fundFlowPOPage = fundFlowDao.findAll(new Specification<FundFlowPO>() {

            @Override
            public Predicate toPredicate(Root<FundFlowPO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();

                list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));

                if (ObjectUtils.anyNotNull(fundFlowVO.getUserId())) {
                    list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), fundFlowVO.getUserId()));
                }

                if (ObjectUtils.anyNotNull(fundFlowVO.getType())) {
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), fundFlowVO.getType()));
                }

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        }, pageable);

        return fundFlowPOPage;
    }



}
