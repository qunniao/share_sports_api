package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.store.VO.AddressVO;
import com.gymnasium.store.service.AddressService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/22 09:39
 * @Description:
 */
@Api(tags = "收货地址 API")
@RestController
@RequestMapping(value = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation("添加收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "consignee", value = "收货人", required = true),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true),
            @ApiImplicitParam(name = "area", value = "所在地区", required = true),
            @ApiImplicitParam(name = "detailedAddress", value = "详细地址", required = true),
            @ApiImplicitParam(name = "isDefault", value = "设为默认地址")
    })
    @RequestMapping(value = "/insertAddress", method = RequestMethod.POST)
    public Result<Boolean> insertAddress(AddressVO addressVO){

        return ResultUtil.success(addressService.insertAddress(addressVO));
    }

    @ApiOperation("根据默认地址查询收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/findAddressBySetDefault", method = RequestMethod.POST)
    public Result<AddressVO> findAddressBySetDefault(Integer userId){

        return ResultUtil.success(addressService.findAddressBySetDefault(userId));
    }

    @ApiOperation("查询收货地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
    })
    @RequestMapping(value = "/queryAddress", method = RequestMethod.POST)
    public Result<List<AddressVO>> queryAddress(Integer userId){

        return ResultUtil.success(addressService.selectAddress(userId));
    }

    @ApiOperation("更新收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "consignee", value = "收货人", required = true),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true),
            @ApiImplicitParam(name = "area", value = "所在地区", required = true),
            @ApiImplicitParam(name = "detailedAddress", value = "详细地址"),
            @ApiImplicitParam(name = "isDefault", value = "设为默认地址")
    })
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public Result<Boolean>updateAddress(AddressVO addressVO){

        return ResultUtil.success(addressService.updateAddress(addressVO));
    }

    @ApiOperation("删除收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)
    })
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public Result<Boolean> deleteAddress(Integer id){

        return ResultUtil.success(addressService.deleteAddress(id));
    }
}
