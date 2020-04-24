package com.gymnasium.data.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.data.Service.CodelogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王志鹏
 * @title: CodelogController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 16:11
 */
@Api(tags = "获取验证码API")
@RequestMapping(value = "/codelog")
@RestController
public class CodelogController {

    @Autowired
    private CodelogService codelogService;

    @ApiOperation("通过手机号获得验证码")
    @RequestMapping(value = "/geCheckCode", method = RequestMethod.POST)
    public Result<String> geCheckCode(@RequestParam("userPhone") String userPhone, @RequestParam("type") Short type) throws SCException {

        return ResultUtil.success(codelogService.getCode(userPhone, type));
    }


}
