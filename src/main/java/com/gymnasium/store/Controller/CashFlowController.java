package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.store.PO.CartPO;
import com.gymnasium.store.VO.CashFlowVO;
import com.gymnasium.store.service.CashFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/4 17:23
 * @Description:
 */

@Api(tags = "资金流水 API")
@RestController
@RequestMapping("/cashFlow")
public class CashFlowController {

    @Autowired
    private CashFlowService cashFlowService;

    @ApiOperation("根据订单id查询资金流水")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true)
    })
    @RequestMapping(value = "/queryCashFlow", method = RequestMethod.POST)
    public Result<CashFlowVO> queryCashFlow(Long orderId){

        return ResultUtil.success(cashFlowService.queryCashFlow(orderId));
    }
}
