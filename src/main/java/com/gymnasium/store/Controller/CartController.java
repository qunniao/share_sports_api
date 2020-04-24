package com.gymnasium.store.Controller;

import cn.hutool.core.lang.Assert;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.store.Dao.ProductDao;
import com.gymnasium.store.PO.CartPO;
import com.gymnasium.store.PO.ProductPO;
import com.gymnasium.store.VO.CartVO;
import com.gymnasium.store.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/20 20:39
 * @Description:
 */
@Api(tags = "购物车 API")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductDao productDao;

    @ApiOperation(value = "添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "productId", value = "产品id", required = true),
            @ApiImplicitParam(name = "number", value = "数量", required = true),
            @ApiImplicitParam(name = "settlementType", value = "支付方式", required = true),
            @ApiImplicitParam(name = "style", value = "规格")
    })
    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    public Result<Boolean> addCart(CartVO cartVO) {

        System.out.println(cartVO);

        ProductPO productPO = productDao.findProductPOById(cartVO.getProductId());

        Assert.notNull(productPO,"产品不存在");
        if (productPO.getInventory() < cartVO.getNumber()){
            throw  new SCException(400601,"商品库存不足");
    }
        return ResultUtil.success(cartService.insertCart(cartVO));
}

    @ApiOperation("更新产品数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "购物车id", required = true),
            @ApiImplicitParam(name = "number", value = "产品数量", required = true)
    })
    @RequestMapping(value = "/updateCart", method = RequestMethod.POST)
    public Result<Boolean> updateCart(Long id, Integer number) {

        return ResultUtil.success(cartService.updateCart(id,number));
    }

    @ApiOperation("查询购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "Integer"),
            @ApiImplicitParam(name = "settlementType", value = "结算方式 1.现金支付 2.能量值抵扣", required = true, paramType =
                    "Integer"),
            @ApiImplicitParam(name = "cartIds", value = "购物车id", paramType = "String")
    })
    @RequestMapping(value = "/queryCart", method = RequestMethod.POST)
    public Result<List<CartPO>> queryCart(Integer userId, Integer settlementType, String cartIds){

        return ResultUtil.success(cartService.queryCart(userId,settlementType,cartIds));
    }

    @ApiOperation(value = "查询总价和数量",notes = "返回结果为Map( amount：总数量；totalPrice : 总价)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "settlementType", value = "支付类型", required = true)
    })
    @RequestMapping(value = "/countCart", method = RequestMethod.POST)
    public Result<Map<String, Object>> countCart(Integer userId, Integer settlementType){

        return ResultUtil.success(cartService.countCart(userId, settlementType));
    }

    @ApiOperation(value = "删除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "购物车id数组，例如：[1,2,3]", required = true)
    })
    @RequestMapping(value = "/deleteCart", method = RequestMethod.POST)
    public Result<Boolean> deleteCart(long[] cartId){

        if (ArrayUtils.isEmpty(cartId)){

            throw new SCException(400105,"购物车id为空");
        }

        return ResultUtil.success(cartService.deleteCart(cartId));

    }
}
