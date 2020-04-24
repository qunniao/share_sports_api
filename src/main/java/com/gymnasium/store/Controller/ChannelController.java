package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.store.VO.ChannelVO;
import com.gymnasium.store.VO.CommissionVO;
import com.gymnasium.store.service.ChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 15:10
 * @Description:
 */
@Api(tags = "渠道人 API")
@RestController
@RequestMapping(value = "/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @ApiOperation(value = "一级渠道人注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "superiorId", value = "操作人id"),
            @ApiImplicitParam(name = "userId", value = "渠道人id", required = true),
            @ApiImplicitParam(name = "trueName", value = "真实姓名", required = true),
            @ApiImplicitParam(name = "idCard", value = "身份证", required = true),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = true),
            @ApiImplicitParam(name = "region", value = "所属区域", required = true),
            @ApiImplicitParam(name = "title", value = "机构名称"),
            @ApiImplicitParam(name = "address", value = "机构地址"),
            @ApiImplicitParam(name = "servicePhone", value = "服务电话"),
            @ApiImplicitParam(name = "username", value = "登录账号"),
            @ApiImplicitParam(name = "password", value = "登录密码"),
            @ApiImplicitParam(name = "qrCode", value = "推广二维码"),
            @ApiImplicitParam(name = "code", value = "注册验证码")
    })
    @RequestMapping(value = "/insertOneChannel", method = RequestMethod.POST)
    public Result<Boolean> insertOneChannel(ChannelVO channelVO, String superiorId, String code){

        channelVO.setLevel(1);

        return ResultUtil.success(channelService.insertChannel(channelVO, superiorId, code));
    }

    @ApiOperation(value = "二级渠道人注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "superiorId", value = "操作人id"),
            @ApiImplicitParam(name = "userId", value = "渠道人id", required = true),
            @ApiImplicitParam(name = "trueName", value = "真实姓名", required = true),
            @ApiImplicitParam(name = "idCard", value = "身份证", required = true),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = true),
            @ApiImplicitParam(name = "region", value = "所属区域", required = true),
            @ApiImplicitParam(name = "title", value = "机构名称"),
            @ApiImplicitParam(name = "address", value = "机构地址"),
            @ApiImplicitParam(name = "servicePhone", value = "服务电话"),
            @ApiImplicitParam(name = "username", value = "登录账号"),
            @ApiImplicitParam(name = "password", value = "登录密码"),
            @ApiImplicitParam(name = "qrCode", value = "推广二维码"),
            @ApiImplicitParam(name = "code", value = "注册验证码")
    })
    @RequestMapping(value = "/insertTowChannel", method = RequestMethod.POST)
    public Result<Boolean> insertTowChannel(ChannelVO channelVO, String superiorId, String code){

        channelVO.setLevel(2);
        return ResultUtil.success(channelService.insertChannel(channelVO, superiorId, code));
    }

    @ApiOperation(value = "渠道人查询列表")
            @ApiImplicitParams({
                    @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
                    @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
                    @ApiImplicitParam(name = "level", value = "渠道等级2.一级3.二级"),
                    @ApiImplicitParam(name = "trueName", value = "真实姓名"),
                    @ApiImplicitParam(name = "title", value = "机构"),
                    @ApiImplicitParam(name = "phone", value = "联系电话"),
                    @ApiImplicitParam(name = "operationId", value = "操作人id"),
                    @ApiImplicitParam(name = "auditStatus", value = "审核状态: 0 :审核中，1: 正式")
    })
    @RequestMapping(value = "/pageAllChannel", method = RequestMethod.POST)
    public Result<Page<ChannelVO>> pageAllChannel(Paging page, ChannelVO channelVO){

        return ResultUtil.success(channelService.pageAllChannel(page, channelVO));
    }

    @ApiOperation(value = "渠道人修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "operationId", value = "操作人id"),
            @ApiImplicitParam(name = "userId", value = "渠道人id"),
            @ApiImplicitParam(name = "level", value = "渠道等级2.一级3.二级"),
            @ApiImplicitParam(name = "title", value = "机构名称"),
            @ApiImplicitParam(name = "address", value = "机构地址"),
            @ApiImplicitParam(name = "servicePhone", value = "服务电话"),
            @ApiImplicitParam(name = "trueName", value = "真实姓名"),
            @ApiImplicitParam(name = "idCard", value = "身份证"),
            @ApiImplicitParam(name = "phone", value = "联系电话"),
            @ApiImplicitParam(name = "region", value = "所属区域"),
            @ApiImplicitParam(name = "username", value = "登录账号"),
            @ApiImplicitParam(name = "password", value = "登录密码"),
            @ApiImplicitParam(name = "qrCode", value = "推广二维码")
    })
    @RequestMapping(value = "/updateChannel", method = RequestMethod.POST)
    public Result<Boolean> updateChannel(ChannelVO channelVO){

        return ResultUtil.success(channelService.updateChannel(channelVO));
    }

    @ApiOperation(value = "渠道人审核接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "auditStatus", value = "审核状态(auditStatus):0.删除1.未审核2.通过-1.拒绝", required = true)
    })
    @RequestMapping(value = "/auditChannel", method = RequestMethod.POST)
    public Result<Boolean> auditChannel(Integer id, Integer auditStatus){

        return ResultUtil.success(channelService.auditChannel(id, auditStatus));
    }

    @ApiOperation(value = "渠道人删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token令牌", paramType = "header",required = true),
            @ApiImplicitParam(name = "id", value = "主键", required = true)
    })
    @RequestMapping(value = "/deleteChannel", method = RequestMethod.POST)
    public Result<Boolean> deleteChannel(Integer id){

        return ResultUtil.success(channelService.deleteChannel(id));
    }

    @ApiOperation(value = "查询渠道团队接口", notes = "totalNumber:订单数; totalCommission:总佣金；sales：销售额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "userId", value = "渠道人id", required = true),
            @ApiImplicitParam(name = "status", value = "审核状态(auditStatus):0.删除1.未审核2.通过-1.拒绝")
    })
    @RequestMapping(value = "/queryChannelTeam", method = RequestMethod.POST)
    public Result<List<Map>> queryChannelTeam(CommissionVO commissionVO, Paging page){

        return ResultUtil.success(channelService.queryChannelTeam(commissionVO, page));
    }

    @ApiOperation(value = "查询渠道人等级接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "渠道人id", required = true)
    })
    @RequestMapping(value = "/queryChannelLevel", method = RequestMethod.POST)
    public Result<Integer> queryChannelLevel(Integer userId){

        return ResultUtil.success(channelService.queryChannelLevel(userId));
    }
}

