package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.store.PO.ProductTypePO;
import com.gymnasium.store.VO.ProductTypeVO;
import com.gymnasium.store.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/18 15:07
 *
 */
@Api(tags = "产品类型 API")
@RestController
@RequestMapping("/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @ApiOperation("添加产品分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "产品分类名称", required = true),
            @ApiImplicitParam(name = "settlementType", value = "支付类型", required = true),
    })
    @RequestMapping(value = "/addProductType", method = RequestMethod.POST)
    public Result<ProductTypeVO> addProductType(@RequestParam("files") MultipartFile file, String title, Integer settlementType){

        return ResultUtil.success(productTypeService.insertProductType(file, title, settlementType));
    }

    @ApiOperation("查询产品分类信息列表")
    @RequestMapping(value = "/queryProductType", method = RequestMethod.POST)
    public Result<List<ProductTypePO>> queryProductType(){

        return ResultUtil.success(productTypeService.selectProductType());
    }

    @ApiOperation("更新产品分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true),
            @ApiImplicitParam(name = "title", value = "分类标题", required = true),
            @ApiImplicitParam(name = "settlementType", value = "支付类型", required = true)
    })
    @RequestMapping(value = "/updateProductType", method = RequestMethod.POST)
    public Result<Boolean> updateProductType(MultipartFile file, ProductTypeVO productTypeVO){

        return ResultUtil.success(productTypeService.updateProductType(file,productTypeVO));
    }

    @ApiOperation("删除产品分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "分类id", required = true)
    })
    @RequestMapping(value = "/deleteProductType", method = RequestMethod.POST)
    public Result<Boolean> deleteProductType(Integer id){

        return ResultUtil.success(productTypeService.deleteProductType(id));
    }
}
