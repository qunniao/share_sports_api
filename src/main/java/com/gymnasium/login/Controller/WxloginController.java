package com.gymnasium.login.Controller;

import cn.hutool.core.util.StrUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.login.Service.WxloginService;
import com.gymnasium.personnel.VO.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王志鹏
 * @title: WxloginController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/1 15:58
 */
@Api(tags = "微信登录")
@RestController
@RequestMapping(value = "/wxlog")
public class WxloginController {

    @Autowired
    private WxloginService wxloginService;

    @RequestMapping(value = "/loginUserForWX", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信登录时获取的code", required = true),
            @ApiImplicitParam(name = "superiorId", value = "上级id")
    })

    @ApiOperation(value = "微信登录,通过code进行登录,返回Userd对象")
    public Result<UserVO> loginUserForWX(String code, Integer superiorId) throws SCException, APIConnectionException, APIRequestException {

        if(StrUtil.isBlank(code)){

            return ResultUtil.error(41010,"code参数为空");
        }

        return ResultUtil.success(wxloginService.loginUserForWX(code,superiorId));
    }

    @RequestMapping(value = "/loginUserForH5", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信登录时获取的code", required = true),
            @ApiImplicitParam(name = "oneUserId", value = "分享人id"),
            @ApiImplicitParam(name = "role", value = "用户身份")
    })
    public Result<UserVO> loginUserForH5(String code, String oneUserId, Integer role) throws SCException, APIConnectionException, APIRequestException {

        if(StrUtil.isBlank(code)){
            return ResultUtil.error(41010,"code参数为空");
        }

        return ResultUtil.success(wxloginService.loginUserForGZH(code, oneUserId, role));
    }
}
