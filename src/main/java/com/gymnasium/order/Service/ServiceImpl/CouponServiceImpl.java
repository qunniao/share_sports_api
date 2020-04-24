package com.gymnasium.order.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.*;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.core.page.Paging;
import com.gymnasium.order.DAO.CouponDao;
import com.gymnasium.order.DAO.CouponOperationDao;
import com.gymnasium.order.PO.CouponOperationPO;
import com.gymnasium.order.PO.CouponPO;
import com.gymnasium.order.Service.CouponService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 王志鹏
 * @title: CouponServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/23 11:00
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private CouponOperationDao couponOperationDao;

    @Override
    public Page<CouponPO> pageCoupo(Paging page, CouponPO couponPO) {
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.ASC, "id");

        Page<CouponPO> gymShopPage = couponDao.findAll(new Specification<CouponPO>() {
            @Override
            public Predicate toPredicate(Root<CouponPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (GeneralUtils.notZero(couponPO.getType())) {
                    list.add(criteriaBuilder.equal(root.get("type"), couponPO.getType()));
                }
                if (GeneralUtils.notEmpty(couponPO.getStatus())) {
                    list.add(criteriaBuilder.equal(root.get("status"), couponPO.getStatus()));
                }

                if (GeneralUtils.notEmpty(couponPO.getTitle())) {
                    list.add(criteriaBuilder.like(root.get("title"), "%" + couponPO.getTitle() + "%"));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return gymShopPage;
    }

    @Override
    public CouponPO queryDetails(Integer id) {

        return couponDao.findById(id.intValue());
    }

    @Override
    public Set<CouponPO> queryCoupons(String userId, BigDecimal totalPrices, Integer type) {

        Set<CouponPO> couPon = couponDao.queryAvailableCouPon(userId, totalPrices, type);

        return couPon;
    }

    @Override
    public Page<CouponOperationPO> pageCouponOperation(Paging page, CouponOperationPO couponOperationPO) {
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.ASC, "id");

        Page<CouponOperationPO> gymShopPage = couponOperationDao.findAll(new Specification<CouponOperationPO>() {
            @Override
            public Predicate toPredicate(Root<CouponOperationPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (GeneralUtils.notEmpty(couponOperationPO.getUserId())) {
                    list.add(criteriaBuilder.equal(root.get("userId"), couponOperationPO.getUserId()));
                }

                if (GeneralUtils.notEmpty(couponOperationPO.getType())) {

                    list.add(criteriaBuilder.equal(root.get("type"), couponOperationPO.getType()));
                }

                if (GeneralUtils.notEmpty(couponOperationPO.getStatus())) {

                    list.add(criteriaBuilder.equal(root.get("status"), couponOperationPO.getStatus()));
                }

                if (GeneralUtils.notEmpty(couponOperationPO.getVerifyState())) {

                    list.add(criteriaBuilder.equal(root.get("verifyState"), couponOperationPO.getVerifyState()));
                }
                if (GeneralUtils.notEmpty(couponOperationPO.getVerifyWay())) {

                    list.add(criteriaBuilder.equal(root.get("verifyWay"), couponOperationPO.getVerifyWay()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return gymShopPage;
    }

    @Override
    public List<CouponPO> queryPrivilegeCoupon(Integer memberLevel, Integer type, Integer isHot) {

        List<CouponPO> couponPOList = couponDao.findAll((Specification<CouponPO>) (root, query, criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            list.add(criteriaBuilder.equal(root.get("memberLevel").as(Integer.class), memberLevel));

            if (ObjectUtil.isNotNull(type)) {

                list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
            }

            if (ObjectUtil.isNotNull(isHot)) {

                list.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), isHot));
            }

            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));

            list.add(criteriaBuilder.equal(root.get("issueWay").as(Integer.class), 2));

            Predicate[] predicates = new Predicate[list.size()];

            return criteriaBuilder.and(list.toArray(predicates));
        });
        return couponPOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCoupon(MultipartFile files, MultipartFile iconFiles, CouponPO couponPO){

        System.err.println(couponPO);

        String images = FileUtils.uploadImage(files);

        String iconImages = FileUtils.uploadImage(iconFiles);

        couponPO.setIcon(iconImages);
        couponPO.setCover(images);

        Timestamp newDate = DateUtil.getDateToTimestamp(new Date());

        couponPO.setCreateTime(newDate);
        couponPO.setUpdateTime(newDate);
        couponPO.setStatus(1);
        couponDao.save(couponPO);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCoupon(MultipartFile files, MultipartFile iconFiles, CouponPO couponPO){

        // 上传图片
        String images = FileUtils.uploadImage(files);
        String iconImages = FileUtils.uploadImage(iconFiles);

        CouponPO couponPO1 = couponDao.findById(couponPO.getId().intValue());

        if (ObjectUtil.isNull(couponPO1)){
            throw new SCException(400125, "优惠券不存在");
        }

        BeanUtil.copyProperties(couponPO, couponPO1);

        couponPO1.setCover(images);
        couponPO1.setIcon(iconImages);
        couponPO1.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        CouponPO save = couponDao.save(couponPO1);

        return ObjectUtils.anyNotNull(save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDownCoupon(int id) {
        CouponPO couponPO1 = couponDao.findById(id);

        if (ObjectUtil.isNull(couponPO1)){
            throw new SCException(400125, "优惠券不存在");
        }
        couponPO1.setStatus(2);
        CouponPO save = couponDao.save(couponPO1);
        return ObjectUtils.anyNotNull(save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCouponStatus(int id) {
        CouponPO couponPO1 = couponDao.findById(id);

        if (ObjectUtil.isNull(couponPO1)){
            throw new SCException(400125, "优惠券不存在");
        }
        couponPO1.setStatus(0);
        CouponPO save = couponDao.save(couponPO1);
        return ObjectUtils.anyNotNull(save);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addCouponOperation(CouponOperationPO couponOperationPO) {

        String couponCode = RandomUtil.getRandomStringByLength(10);
        Timestamp nowTime = DateUtil.getDateToTimestamp(new Date());

        CouponPO couponPO = couponDao.findById(couponOperationPO.getCouponId().intValue());

        if (ObjectUtil.isNull(couponPO)){
            throw new SCException(400125, "优惠券不存在");
        }

        int day = couponPO.getValidity();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);//让日期加1

        Date expiration_time = calendar.getTime();

        couponOperationPO.setCouponCode(couponCode);
        couponOperationPO.setCreateTime(nowTime);
        couponOperationPO.setExpirationTime(DateUtil.getDateToTimestamp(expiration_time));
        couponOperationPO.setUpdateTime(nowTime);
        couponOperationPO.setVerifyWay(2);
        couponOperationPO.setStatus(0);
        couponOperationDao.save(couponOperationPO);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCouponOperation(CouponOperationPO couponOperationPO) {

        CouponOperationPO couponOperationPO1 = couponOperationDao.findById(couponOperationPO.getId().intValue());

        if (ObjectUtil.isNull(couponOperationPO1)){
            throw new SCException(400125, "优惠券不存在");
        }

        Timestamp nowTime = DateUtil.getDateToTimestamp(new Date());

        couponOperationPO1.setUpdateTime(nowTime);
        couponOperationPO1.setOperatorId(couponOperationPO.getOperatorId());
        couponOperationPO1.setVerifyState(couponOperationPO.getVerifyState());
        couponOperationPO1.setExpirationTime(nowTime);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDelCouponOperationType(int id) {
        CouponOperationPO couponOperationPO1 = couponOperationDao.findById(id);
        Timestamp nowTime = DateUtil.getDateToTimestamp(new Date());
        couponOperationPO1.setUpdateTime(nowTime);
        couponOperationPO1.setStatus(0);

        return true;
    }
}
