package com.gymnasium.order.Service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.order.PO.CouponOperationPO;
import com.gymnasium.order.PO.CouponPO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author 王志鹏
 * @title: CouponService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/23 11:00nos
 */
public interface CouponService {

    //分页查询优惠卷
    Page<CouponPO> pageCoupo(Paging page, CouponPO couponPO);

    /**
     * 查询优惠券详情
     * @param id
     * @return
     */
    CouponPO queryDetails(Integer id);

    //查询我可用的优惠卷
    Set<CouponPO> queryCoupons(String userId, BigDecimal totalPrices, Integer type);

    //查询我的优惠券(coupon_operation),关联查询coupon
    Page<CouponOperationPO> pageCouponOperation(Paging page, CouponOperationPO couponOperationPO);

    /**
     * 查询会员特权优惠券
     * @return
     */
    List<CouponPO> queryPrivilegeCoupon(Integer memberLevel, Integer type, Integer isHot);

    //添加优惠券
    boolean addCoupon(MultipartFile files, MultipartFile iconFiles, CouponPO couponPO) throws Exception;

    //修改优惠卷
    boolean updateCoupon(MultipartFile files, MultipartFile iconFiles, CouponPO couponPO) throws Exception;

    //下架优惠券
    boolean updateDownCoupon(int id);

    //删除优惠券
    boolean updateCouponStatus(int id);
    //1.自动发放

    //人工发放接口
    boolean addCouponOperation(CouponOperationPO couponOperationPO);

    //核销优惠券
    boolean updateCouponOperation(CouponOperationPO couponOperationPO);

    //删除优惠券
    boolean updateDelCouponOperationType(int id);

}