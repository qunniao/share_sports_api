package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.PO.ProductPO;
import com.gymnasium.store.VO.ProductVO;
import com.gymnasium.store.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/18 15:07
 */
@Api(tags = "产品信息 API")
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("上架产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "files", value = "单个图片"),
            @ApiImplicitParam(name = "detailsFiles", value = "多个详情图片"),
            @ApiImplicitParam(name = "carouselFiles", value = "多个产品轮播图"),
            @ApiImplicitParam(name = "name", value = "产品名称", required = true),
            @ApiImplicitParam(name = "productTypeId", value = "产品类型Id", required = true),
            @ApiImplicitParam(name = "settlementType", value = "支付方式 1.现金支付 2.能量值抵扣", required = true),
            @ApiImplicitParam(name = "showPrice", value = "产品展示价格", required = true),
            @ApiImplicitParam(name = "realPrice", value = "产品实际价格", required = true),
            @ApiImplicitParam(name = "floorPrice", value = "产品底价", required = true),
            @ApiImplicitParam(name = "inventory", value = "库存数量"),
            @ApiImplicitParam(name = "intro", value = "简介"),
            @ApiImplicitParam(name = "putawayId", value = "上架人员"),
            @ApiImplicitParam(name = "recommend", value = "是否推荐0. 不推荐 1. 推荐"),
            @ApiImplicitParam(name = "styleName", value = "款式名称"),
            @ApiImplicitParam(name = "style", value = "款式"),
            @ApiImplicitParam(name = "param", value = "参数"),
            @ApiImplicitParam(name = "freeShipping", value = "是否包邮:0 否; 1 是"),
            @ApiImplicitParam(name = "timelyDelivery", value = "是否七天发货:0 否; 1 是"),
            @ApiImplicitParam(name = "salableProduct", value = "是否正品:0 否; 1 是"),
            @ApiImplicitParam(name = "fullService", value = "是否全程服务:0 否; 1 是"),
            @ApiImplicitParam(name = "freight", value = "运费"),
            @ApiImplicitParam(name = "isCoupon", value = "是否可使用优惠券 : 0 否; 1 是"),
            @ApiImplicitParam(name = "isAgent", value = "是否代理:0 否; 1 是"),
            @ApiImplicitParam(name = "isChannel", value = "是否渠道:0 否; 1 是"),
    })
    @RequestMapping(value = "/inertProduct", method = RequestMethod.POST)
    public Result<Integer> inertProduct(MultipartFile files, List<MultipartFile> detailsFiles,
                                        List<MultipartFile> carouselFiles, ProductVO productVO) {

        return ResultUtil.success(productService.insertProduct(files, detailsFiles, carouselFiles, productVO));
    }

    @ApiOperation("分页查询产品信息, 备注：所有查询都是一个方法，按所传参数进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "productTypeId", value = "产品类型Id: 1:运动营养; 2:训练工具; 3:健身装备; 4:运动娱乐; " +
                    "5:特权产品" + " 6: 礼包产品"),
            @ApiImplicitParam(name = "settlementType", value = "支付类型 1.现金支付 2.能量值抵扣"),
            @ApiImplicitParam(name = "name", value = "产品名称模糊查询"),
            @ApiImplicitParam(name = "recommend", value = "是否推荐 0. 不推荐 1. 推荐"),
            @ApiImplicitParam(name = "sorts", value = "排序方式: 0.默认1.价格2.销量")
    })
    @RequestMapping(value = "/pageAll", method = RequestMethod.POST)
    public Result<Page<ProductPO>> pageAll(Paging page, ProductVO productVO, String sorts) {

        productVO.setPutawayStatus(1);
        return ResultUtil.success(productService.pageAll(page, productVO, sorts));
    }

    @ApiOperation("后台分页查询产品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "productTypeId", value = "产品类型Id: 1:运动营养; 2:训练工具; 3:健身装备; 4:运动娱乐; " +
                    "5:特权产品" + " 6: 礼包产品"),
            @ApiImplicitParam(name = "settlementType", value = "支付类型 1.现金支付 2.能量值抵扣"),
            @ApiImplicitParam(name = "name", value = "产品名称模糊查询"),
            @ApiImplicitParam(name = "recommend", value = "是否推荐 0. 不推荐 1. 推荐"),
            @ApiImplicitParam(name = "putawayStatus", value = "上架状态 1： 上架 0.下架"),
            @ApiImplicitParam(name = "sorts", value = "排序方式: 0.默认1.价格2.销量")
    })
    @PostMapping("/findAll")
    public Result<Page<ProductPO>> findAll(Paging page, ProductVO productVO, String sorts) {

        return ResultUtil.success(productService.pageAll(page, productVO, sorts));
    }

    @ApiOperation("根据id查询产品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true)
    })
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public Result<ProductVO> findById(Integer id) {

        return ResultUtil.success(productService.findById(id));
    }

    @ApiOperation("更新产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键",required = true),
            @ApiImplicitParam(name = "files", value = "单个图片"),
            @ApiImplicitParam(name = "detailsFiles", value = "多个图片"),
            @ApiImplicitParam(name = "carouselFiles", value = "多个产品轮播图"),
            @ApiImplicitParam(name = "name", value = "产品名称"),
            @ApiImplicitParam(name = "productTypeId", value = "产品类型Id"),
            @ApiImplicitParam(name = "settlementType", value = "支付方式 1.现金支付 2.能量值抵扣"),
            @ApiImplicitParam(name = "showPrice", value = "产品展示价格"),
            @ApiImplicitParam(name = "realPrice", value = "产品实际价格"),
            @ApiImplicitParam(name = "floorPrice", value = "产品底价"),
            @ApiImplicitParam(name = "intro", value = "简介"),
            @ApiImplicitParam(name = "putawayId", value = "上架人员"),
            @ApiImplicitParam(name = "inventory", value = "库存数量"),
            @ApiImplicitParam(name = "recommend", value = "是否推荐0. 不推荐 1. 推荐"),
            @ApiImplicitParam(name = "styleName", value = "款式名称"),
            @ApiImplicitParam(name = "style", value = "款式"),
            @ApiImplicitParam(name = "param", value = "参数"),
            @ApiImplicitParam(name = "freeShipping", value = "是否包邮:0 否; 1 是"),
            @ApiImplicitParam(name = "timelyDelivery", value = "是否七天发货:0 否; 1 是"),
            @ApiImplicitParam(name = "salableProduct", value = "是否正品:0 否; 1 是"),
            @ApiImplicitParam(name = "fullService", value = "是否全程服务:0 否; 1 是"),
            @ApiImplicitParam(name = "freight", value = "运费"),
            @ApiImplicitParam(name = "isCoupon", value = "是否可使用优惠券 : 0 否; 1 是"),
            @ApiImplicitParam(name = "isAgent", value = "是否代理:0 否; 1 是"),
            @ApiImplicitParam(name = "isChannel", value = "是否渠道:0 否; 1 是"),
    })
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public Result<Boolean> updateProduct(MultipartFile files, List<MultipartFile> detailsFiles,
                                         List<MultipartFile> carouselFiles, ProductVO productVO) {

        return ResultUtil.success(productService.updateProduct(files, detailsFiles, carouselFiles, productVO));
    }

    @ApiOperation("下架产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "id", value = "产品Id", required = true)
    })
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public Result<Boolean> deleteProduct(Integer id) {

        return ResultUtil.success(productService.deleteProduct(id));
    }

    @ApiOperation("删除产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品Id", required = true)
    })
    @RequestMapping(value = "/delProduct", method = RequestMethod.POST)
    public Result<Boolean> delProduct(Integer id) {

        return ResultUtil.success(productService.delProduct(id));
    }
}
