package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.store.Dao.ProductCarouselDao;
import com.gymnasium.store.PO.ProductCarouselPO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/23 14:02
 * @Description:
 */

@Api(tags = "产品轮播图")
@RestController
@RequestMapping(value = "/carousel")
public class CarouselController {
        //查询产品轮播图
        //删除产品轮播图

        @Autowired
        private ProductCarouselDao productCarouselDao;

        @ApiOperation("查询产品轮播图")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "productId", value = "产品id", required = true),
        })
        @RequestMapping(value = "/queryProductCarousel", method = RequestMethod.POST)
        public Result<List<ProductCarouselPO>> queryProductCarousel(Integer productId) {

            return ResultUtil.success(productCarouselDao.findAllByProductIdAndStatus(productId,1));
        }

        @ApiOperation("删除产品轮播图")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "id", value = "主键", required = true),
        })
        @RequestMapping(value = "/delProductCarousel", method = RequestMethod.POST)
        @Transactional(rollbackFor = Exception.class)
        public Result<Boolean> delProductCarousel(@RequestParam Integer id) {

            ProductCarouselPO productCarouselPO = productCarouselDao.findProductCarouselPOById(id);

            if (productCarouselPO == null) {
                throw new SCException(111, "删除失败,未找到该图片!");
            }
            productCarouselDao.deleteById(id);

            return ResultUtil.success(true);
        }

    }
