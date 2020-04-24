package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.Dao.ProductDao;
import com.gymnasium.store.PO.ProductPO;
import com.gymnasium.store.PO.ShopOrderPO;
import com.gymnasium.store.VO.ProductInfoVO;
import com.gymnasium.store.VO.ShopOrderVO;
import com.gymnasium.store.dto.ShopOrderQuery;
import com.gymnasium.store.service.ShopOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 20:27
 * @Description:
 */
@Api(tags = "商城订单 API")
@RestController
@RequestMapping("/shopOrder")
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @Autowired
    private ProductDao productDao;

    @ApiOperation("提交订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "Integer"),
            @ApiImplicitParam(name = "productInfo", value = "多个产品，字典数组json" +
                    "（例：\"[{productId:1,number:2,style:\"黑色\"}]\"）",
                    required = true),
            @ApiImplicitParam(name = "contactName", value = "姓名", required =
                    true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "address", value = "地址", required = true),
            @ApiImplicitParam(name = "addressId", value = "收货地址id", required
                    = true, paramType = "Integer"),
            @ApiImplicitParam(name = "discount", value = "优惠价"),
            @ApiImplicitParam(name = "couponId", value = "优惠券id", paramType = "Integer"),
            @ApiImplicitParam(name = "settlementType", value = "结算方式：1.现金2" +
                    ".能量值", required = true, paramType = "Integer"),
            @ApiImplicitParam(name = "remark", value = "备注")
    })
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    public Result<String> submitOrder(@RequestBody ShopOrderVO shopOrderVO) {

        for (ProductInfoVO productInfoVO : shopOrderVO.getProductInfo()) {

            ProductPO productPO = productDao.findProductPOById(productInfoVO.getProductId());

            if (productPO.getInventory() < productInfoVO.getNumber()) {

                throw new SCException(400601, productPO.getName() + "商品库存不足");
            }
        }

        return ResultUtil.success(shopOrderService.insertShopOrder(shopOrderVO));
    }

    @ApiOperation("提交购物车订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "cartIds", value = "多个购物车id(例:1,2,3)",
                    required = true),
            @ApiImplicitParam(name = "contactName", value = "姓名", required =
                    true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "address", value = "地址", required = true),
            @ApiImplicitParam(name = "addressId", value = "收货地址id", required
                    = true),
            @ApiImplicitParam(name = "discount", value = "优惠价"),
            @ApiImplicitParam(name = "couponId", value = "用户可用优惠券id"),
            @ApiImplicitParam(name = "settlementType", value = "结算方式：1.现金2" +
                    ".能量值", required = true),
            @ApiImplicitParam(name = "remark", value = "备注")
    })
    @RequestMapping(value = "/submitCart", method = RequestMethod.POST)
    public Result<String> submitCart(ShopOrderVO shopOrderVO, String cartIds) {

        return ResultUtil.success(shopOrderService.submitCart(shopOrderVO,
                cartIds));
    }

    @ApiOperation("发货接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单号", required = true),
            @ApiImplicitParam(name = "deliveryWay", value = "配送方式", required = true),
            @ApiImplicitParam(name = "courierNumber", value = "快递单号", required = true)
    })
    @RequestMapping(value = "/shipments", method = RequestMethod.POST)
    public Result<Boolean> shipments(ShopOrderVO shopOrderVO) {

        return ResultUtil.success(shopOrderService.shipments(shopOrderVO));
    }

    @ApiOperation("收货接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/receiving", method = RequestMethod.POST)
    public Result<Boolean> receiving(Long id, Integer userId) {

        return ResultUtil.success(shopOrderService.receiving(id, userId));
    }

    @ApiOperation("查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id"),
            @ApiImplicitParam(name = "orderState", value = "订单状态"),
            @ApiImplicitParam(name = "orderNumber", value = "订单号"),
            @ApiImplicitParam(name = "year", value = "年"),
            @ApiImplicitParam(name = "month", value = "月")
    })
    @RequestMapping(value = "/queryShopOrder", method = RequestMethod.POST)
    public Result<Page<ShopOrderPO>> queryShopOrder(Paging page, ShopOrderVO shopOrderVO, Integer year, Integer month) {

        return ResultUtil.success(shopOrderService.queryShopOrder(page, shopOrderVO, year, month));
    }

    @ApiOperation("后台查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "settlementType", value = "支付方式"),
            @ApiImplicitParam(name = "orderState", value = "订单状态"),
            @ApiImplicitParam(name = "orderNumber", value = "订单号"),
            @ApiImplicitParam(name = "createTime", value = "下单时间,字符串类型", example = "2019-08-30"),
            @ApiImplicitParam(name = "deliveryTime", value = "发货时间，字符串类型", example = "2019-08-30"),
            @ApiImplicitParam(name = "keyword", value = "关键词")
    })
    @RequestMapping(value = "/queryOrder", method = RequestMethod.POST)
    public Result<Page<ShopOrderPO>> queryOrder(ShopOrderQuery shopOrderQuery) {

        return ResultUtil.success(shopOrderService.queryOrder(shopOrderQuery));
    }

    @ApiOperation("修改收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true),
            @ApiImplicitParam(name = "contactName", value = "姓名", required =
                    true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "address", value = "地址", required = true),
            @ApiImplicitParam(name = "address_id", value = "收货地址id",
                    required = true)
    })
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public Result<Boolean> updateAddress(ShopOrderVO shopOrderVO) {

        return ResultUtil.success(shopOrderService.updateShopOrder(shopOrderVO));
    }

    @ApiOperation("代理和渠道订单查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "代理人id", required = true),
            @ApiImplicitParam(name = "role", value = "客户类型：0:全部、1:客户、2:代理或渠道",
                    required = true),
            @ApiImplicitParam(name = "orderState", value = "订单状态：0.已取消1.待付款2.待发货3.待收货4.已完成", required = true)
    })
    @RequestMapping(value = "/pageCustomerOrder", method = RequestMethod.POST)
    public Result<Page<ShopOrderPO>> pageCustomerOrder(Integer role, Integer userId, Integer orderState, Paging page) {
        return ResultUtil.success(shopOrderService.pageCustomerOrder(role, userId, orderState, page));
    }

    @ApiOperation("根据订单id统计商品预计总佣金")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true)
    })
    @RequestMapping(value = "/calculateTotalCommission", method = RequestMethod.POST)
    public Result<BigDecimal> calculateTotalCommission(Long orderId) {

        return ResultUtil.success(shopOrderService.calculateTotalCommission(orderId));
    }

    @ApiOperation("根据订单id查询订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true)
    })
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public Result<ShopOrderPO> findGymImages(Long id) {

        return ResultUtil.success(shopOrderService.queryShopOrderDetails(id));
    }

    @ApiOperation("取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true)
    })
    @RequestMapping(value = "/cancelShopOrder", method = RequestMethod.POST)
    public Result<Boolean> cancelShopOrder(Long id) {

        return ResultUtil.success(shopOrderService.cancelShopOrder(id));
    }

    @ApiOperation("快递实时查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", required = true)
    })
    @RequestMapping(value = "/queryExpress", method = RequestMethod.POST)
    public Result<String> queryExpress(Long id) {

        return ResultUtil.success(shopOrderService.queryExpress(id));
    }
}
