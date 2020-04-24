package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.store.PO.ServiceCommentPO;
import com.gymnasium.store.PO.ServicePersonnelPO;
import com.gymnasium.store.PO.ServiceUser;
import com.gymnasium.store.VO.ServiceCommentVO;
import com.gymnasium.store.VO.ServicePersonnelVO;
import com.gymnasium.store.VO.ServiceRewardVO;
import com.gymnasium.store.service.ServiceCommentService;
import com.gymnasium.store.service.ServicePersonnelService;
import com.gymnasium.store.service.ServiceRewardService;
import com.gymnasium.store.service.ServiceUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/28 16:49
 * @Description:
 */

@Api(tags = "客服管理 API")
@RestController
@RequestMapping(value = "/customerService")

public class ServiceController {

    @Autowired
    private ServiceRewardService serviceRewardService;

    @Autowired
    private ServiceCommentService serviceCommentService;

    @Autowired
    private ServicePersonnelService servicePersonnelService;

    @Autowired
    private ServiceUserService serviceUserService;

    @ApiOperation("客服注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true),
            @ApiImplicitParam(name = "nick", value = "昵称", required = true),
            @ApiImplicitParam(name = "coverFile", value = "头像", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
    })
    @RequestMapping(value = "/insertServicePersonnel", method = RequestMethod.POST)
    public Result<Boolean> insertServicePersonnel(MultipartFile coverFile, ServicePersonnelVO servicePersonnelVO) {

        return ResultUtil.success(servicePersonnelService.insertServicePersonnel(coverFile, servicePersonnelVO));
    }

    @ApiOperation("开始服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true),
            @ApiImplicitParam(name = "jPushId", value = "用户极光id", required = true)
    })
    @RequestMapping(value = "/startService", method = RequestMethod.POST)
    public Result<Boolean> startService(Integer serviceId, String jPushId) {

        return ResultUtil.success(servicePersonnelService.updateServiceNumber(serviceId, jPushId, 1));
    }

    @ApiOperation("结束服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true),
            @ApiImplicitParam(name = "jPushId", value = "用户极光id", required = true)
    })
    @RequestMapping(value = "/endService", method = RequestMethod.POST)
    public Result<Boolean> endService(Integer serviceId, String jPushId) {

        return ResultUtil.success(servicePersonnelService.updateServiceNumber(serviceId, jPushId, 2));
    }

    @ApiOperation("用户随机匹配客服")
    @RequestMapping(value = "/queryServicePersonnel", method = RequestMethod.POST)
    public Result<ServicePersonnelPO> queryServicePersonnel() {

        return ResultUtil.success(servicePersonnelService.queryServicePersonnel());
    }

    @ApiOperation("根据工号查询客服")
    @ApiImplicitParam(name = "workNumber", value = "客服工号", required = true)
    @RequestMapping(value = "/queryCustomerService", method = RequestMethod.POST)
    public Result<ServicePersonnelPO> queryCustomerService(String workNumber) {

        return ResultUtil.success(servicePersonnelService.queryCustomerService(workNumber));
    }

    @ApiOperation("用户评价客服")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true),
            @ApiImplicitParam(name = "level", value = "评分(1-5)", required = true),
            @ApiImplicitParam(name = "content", value = "建议内容"),
            @ApiImplicitParam(name = "solved", value = "是否解决:0.没有解决1.已解决", required = true)
    })
    @RequestMapping(value = "/insertServiceComment", method = RequestMethod.POST)
    public Result<Boolean> insertServiceComment(ServiceCommentVO serviceCommentVO) {

        return ResultUtil.success(serviceCommentService.insertServiceComment(serviceCommentVO));
    }

    @ApiOperation(value = "打赏客服")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true),
            @ApiImplicitParam(name = "money", value = "打赏金额", required = true),
            @ApiImplicitParam(name = "content", value = "评价内容", required = true)
    })
    @RequestMapping(value = "/insertServiceReward", method = RequestMethod.POST)
    public Result<Boolean> insertServiceReward(ServiceRewardVO serviceRewardVO) {

        return ResultUtil.success(serviceRewardService.insertServiceReward(serviceRewardVO));
    }

    @ApiOperation(value = "查询客服打赏次数", notes = "打赏次数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true)
    })
    @RequestMapping(value = "/countByServiceId", method = RequestMethod.POST)
    public Result<Integer> countByServiceId(Integer serviceId) {

        return ResultUtil.success(serviceRewardService.countByServiceId(serviceId));
    }

    @ApiOperation(value = "查询满意度平均分", notes = "返回结果为满意度平均分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true)
    })
    @RequestMapping(value = "/queryLevelAvg", method = RequestMethod.POST)
    public Result<Integer> queryLevelAvg(Integer serviceId) {

        return ResultUtil.success(serviceCommentService.queryLevelAvg(serviceId));
    }

    @ApiOperation(value = "客服的用户评价信息查询", notes = "返回值为评价信息和用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId", value = "客服id", required = true)
    })
    @RequestMapping(value = "/findAllByServiceId", method = RequestMethod.POST)
    public Result<ServiceCommentPO> findAllByServiceId(Integer serviceId) {

        return ResultUtil.success(serviceCommentService.findAllByServiceId(serviceId));

    }

    @ApiOperation(value = "用户查询客服接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/queryServiceUser", method = RequestMethod.POST)
    public Result<ServiceUser> queryServiceUser(Integer userId) {

        return ResultUtil.success(serviceUserService.queryServiceUser(userId));
    }
}
