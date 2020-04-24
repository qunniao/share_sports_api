package com.gymnasium.order.Controller;

import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.order.PO.CouponOperationPO;
import com.gymnasium.order.PO.CouponPO;
import com.gymnasium.order.Service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author 王志鹏
 * @title: CouponController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/23 10:58
 */

@Api(tags = "优惠券")
@RestController
@RequestMapping(value = "/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;


    @ApiOperation(value = "分页查询优惠券", notes = "可安类型进行筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态:默认全部 1.上架2.下架"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "title", value = "优惠券名称模糊搜索"),
            @ApiImplicitParam(name = "type", value = "类型1.特权券2.礼包券3.商城券")
    })
    @RequestMapping(value = "/pageCoupo", method = RequestMethod.POST)
    public Result<Page<CouponPO>> pageCoupo(Paging page, CouponPO couponPO) {

        return ResultUtil.success(couponService.pageCoupo(page, couponPO));
    }

    @ApiOperation(value = "分页查询我的优惠券", notes = "可进行筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = false),
            @ApiImplicitParam(name = "type", value = "类型1.特权券2.礼包券3.商城券", required = false),
            @ApiImplicitParam(name = "status", value = "状态1.正常可使用0.已使用或失效", required = false),
            @ApiImplicitParam(name = "verifyState", value = "核销状态: 0.未核销,1.用户申请核销,2.已核销,3.已拒绝", required = false),
            @ApiImplicitParam(name = "verifyWay", value = "核销方式1.人工2.自动", required = false)
    })
    @RequestMapping(value = "/pageCouponOperation", method = RequestMethod.POST)
    public Result<Page<CouponOperationPO>> pageCouponOperation(Paging page, @ApiIgnore("CouponOperationPO") CouponOperationPO couponOperationPO) {

        return ResultUtil.success(couponService.pageCouponOperation(page, couponOperationPO));
    }

    @ApiOperation(value = "查询会员特权优惠券", notes = "可进行筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberLevel", value = "会员等级", required = true),
            @ApiImplicitParam(name = "type", value = "类型1.特权券2.礼包券3.商城券"),
            @ApiImplicitParam(name = "isHot", value = "是否人气"),
    })
    @RequestMapping(value = "/queryPrivilegeCoupon", method = RequestMethod.POST)
    public Result<List<CouponPO>> queryPrivilegeCoupon(Integer memberLevel, Integer type, Integer isHot) {

        return ResultUtil.success(couponService.queryPrivilegeCoupon(memberLevel, type, isHot));
    }

    @ApiOperation(value = "查询我可用的优惠券", notes = "可进行筛选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "totalPrices", value = "商品总价",required = true),
            @ApiImplicitParam(name = "type", value = "支付方式：1.现金支付 2.能量值抵扣", required = true)
    })
    @RequestMapping(value = "/queryCoupons", method = RequestMethod.POST)
    public Result<Set<CouponPO>> queryCoupons(String userId, BigDecimal totalPrices, Integer type) {

        if (!ObjectUtils.allNotNull(userId, totalPrices, type)){
            return ResultUtil.error(ResultEnum.INCOMPLETE_PARAMETER);
        }

        return ResultUtil.success(couponService.queryCoupons(userId, totalPrices, type));
    }

    @ApiOperation(value = "添加优惠券", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "单文件"),
            @ApiImplicitParam(name = "title", value = "名称", required = true),
            @ApiImplicitParam(name = "type", value = "类型1.特权券2.礼包券3.商城券", required = true),
            @ApiImplicitParam(name = "content", value = "内容"),
            @ApiImplicitParam(name = "number", value = "数量", required = true),
            @ApiImplicitParam(name = "memberLevel", value = "会员等级1.体验2.大众3.精英4.皇家", required = true),
            @ApiImplicitParam(name = "useWay", value = "使用方式:1.直接核销2.商品减部分能量值3.商品减部分金额", required = true),
            @ApiImplicitParam(name = "issueWay", value = "发放方式:1.人工2.自动(会员购买或激活)", required = true),
            @ApiImplicitParam(name = "price", value = "优惠金额", required = true),
            @ApiImplicitParam(name = "validity", value = "有效期(天):0.永久365.一年", required = true),
            @ApiImplicitParam(name = "status", value = "状态:0.删除1.上架2.下架", required = true),
            @ApiImplicitParam(name = "isHot", value = "是否火热"),
            @ApiImplicitParam(name = "iconFiles", value = "上传图标文件")

    })
    @RequestMapping(value = "/addCoupon", method = RequestMethod.POST)
    public Result<Boolean> addCoupon(@RequestParam("files") MultipartFile files, MultipartFile iconFiles,
                                     @ApiIgnore("CouponPO") CouponPO couponPO) throws Exception {

        return ResultUtil.success(couponService.addCoupon(files, iconFiles, couponPO));
    }

    @ApiOperation(value = "修改优惠卷", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "优惠卷主键", required = true),
            @ApiImplicitParam(name = "files", value = "配图文件"),
            @ApiImplicitParam(name = "title", value = "名称", required = true),
            @ApiImplicitParam(name = "type", value = "类型1.特权券2.礼包券3.商城券", required = true),
            @ApiImplicitParam(name = "content", value = "内容"),
            @ApiImplicitParam(name = "number", value = "数量", required = true),
            @ApiImplicitParam(name = "memberLevel", value = "会员等级1.体验2.大众3.精英4.皇家", required = true),
            @ApiImplicitParam(name = "useWay", value = "使用方式:1.直接核销2.商品减部分能量值3.商品减部分金额", required = true),
            @ApiImplicitParam(name = "issueWay", value = "发放方式:1.人工2.自动(会员购买或激活)", required = true),
            @ApiImplicitParam(name = "price", value = "价格", required = true),
            @ApiImplicitParam(name = "validity", value = "有效期(天):0.永久365.一年", required = true),
            @ApiImplicitParam(name = "status", value = "状态:0.删除1.上架2.下架", required = true),
            @ApiImplicitParam(name = "isHot", value = "是否火热"),
            @ApiImplicitParam(name = "iconFiles", value = "上传图标文件")
    })
    @RequestMapping(value = "/updateCoupon", method = RequestMethod.POST)
    public Result<Boolean> updateCoupon(@RequestParam("files") MultipartFile files, MultipartFile iconFiles, CouponPO couponPO) throws Exception {

        return ResultUtil.success(couponService.updateCoupon(files, iconFiles, couponPO));
    }

    @ApiOperation(value = "下架优惠券", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态:0.删除1.上架2.下架", required = true)
    })
    @RequestMapping(value = "/updateDownCoupon", method = RequestMethod.POST)
    public Result<Boolean> updateDownCoupon(@RequestParam int id) {
        return ResultUtil.success(couponService.updateDownCoupon(id));
    }

    @ApiOperation(value = "查询优惠券详情", notes = "无")
    @ApiImplicitParam(name = "id", value = "优惠券id", required = true)
    @RequestMapping(value = "/queryDetails", method = RequestMethod.POST)
    public Result<CouponPO> queryDetails(Integer id) {
        return ResultUtil.success(couponService.queryDetails(id));
    }

    @ApiOperation(value = "删除优惠券", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态:0.删除1.上架2.下架", required = true)
    })
    @RequestMapping(value = "/updateCouponStatus", method = RequestMethod.POST)
    public Result<Boolean> updateCouponStatus(@RequestParam int id) {

        return ResultUtil.success(couponService.updateCouponStatus(id));
    }

    @ApiOperation(value = "人工发放接口", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "couponId", value = "优惠券id", required = true),
            @ApiImplicitParam(name = "type", value = "优惠券类型", required = true),
            @ApiImplicitParam(name = "issueId", value = "发放人id", required = true)
    })
    @RequestMapping(value = "/addCouponOperation", method = RequestMethod.POST)
    public Result<Boolean> addCouponOperation(CouponOperationPO couponOperationPO) {
        return ResultUtil.success(couponService.addCouponOperation(couponOperationPO));
    }

    @ApiOperation(value = "核销优惠券", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "couponId", value = "优惠卷id", required = true),
            @ApiImplicitParam(name = "operatorId", value = "操作人id(核销客服人员)", required = true),
            @ApiImplicitParam(name = "verifyState", value = "核销状态(2.通过3.拒绝)", required = true)
    })
    @RequestMapping(value = "/updateCouponOperation", method = RequestMethod.POST)
    public Result<Boolean> updateCouponOperation(CouponOperationPO couponOperationPO) {
        return ResultUtil.success(couponService.updateCouponOperation(couponOperationPO));
    }

    @ApiOperation(value = "删除优惠券", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态:0.删除1.上架2.下架", required = true),
            @ApiImplicitParam(name = "id", value = "CouponOperationPO_id", required = true),
            @ApiImplicitParam(name = "operatorId", value = "操作人id(核销客服人员)", required = true),
            @ApiImplicitParam(name = "verifyState", value = "核销状态(2.通过3.拒绝)", required = true)
    })
    @RequestMapping(value = "/updateDelCouponOperationType", method = RequestMethod.POST)
    public Result<Boolean> updateDelCouponOperationType(@RequestParam int id) {

        return ResultUtil.success(couponService.updateDelCouponOperationType(id));
    }
}
