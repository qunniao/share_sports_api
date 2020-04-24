package com.gymnasium.store.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.store.VO.AgentVO;
import com.gymnasium.store.VO.CommissionVO;
import com.gymnasium.store.service.AgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/25 15:04
 * @Description:
 */
@Api(tags = "代理人 API")
@RestController
@RequestMapping(value = "/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @ApiOperation(value = "后台一级代理人注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "superiorId", value = "操作人id"),
            @ApiImplicitParam(name = "userId", value = "代理人id"),
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
            @ApiImplicitParam(name = "code", value = "手机验证码")
    })
    @RequestMapping(value = "/insertOneAgent", method = RequestMethod.POST)
    public Result<Boolean> insertOneAgent(AgentVO agentVO, String superiorId, String code){

        agentVO.setLevel(1);

        return ResultUtil.success(agentService.insertAgent(agentVO,superiorId, code));
    }

    @ApiOperation(value = "二级代理人注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "superiorId", value = "操作人id"),
            @ApiImplicitParam(name = "userId", value = "代理人id", required = true),
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
            @ApiImplicitParam(name = "code", value = "手机验证码")
    })
    @RequestMapping(value = "/insertTowAgent", method = RequestMethod.POST)
    public Result<Boolean> insertTowAgent(AgentVO agentVO, String superiorId, String code){
        agentVO.setLevel(2);
        return ResultUtil.success(agentService.insertAgent(agentVO,superiorId, code));
    }

    @ApiOperation(value = "代理人列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "level", value = "代理人等级1.一级2.二级"),
            @ApiImplicitParam(name = "trueName", value = "真实姓名"),
            @ApiImplicitParam(name = "title", value = "机构"),
            @ApiImplicitParam(name = "phone", value = "联系电话"),
            @ApiImplicitParam(name = "operationId", value = "操作人id"),
            @ApiImplicitParam(name = "auditStatus", value = "审核状态: 0 :审核中，1: 正式")
    })
    @RequestMapping(value = "/pageAllAgent", method = RequestMethod.POST)
    public Result<Page<AgentVO>> pageAllAgent(Paging page, AgentVO agentVO){

        return ResultUtil.success(agentService.pageAllAgent(page, agentVO));
    }

    @ApiOperation(value = "代理人修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "operationId", value = "操作人id"),
            @ApiImplicitParam(name = "userId", value = "代理人id"),
            @ApiImplicitParam(name = "trueName", value = "真实姓名"),
            @ApiImplicitParam(name = "idCard", value = "身份证"),
            @ApiImplicitParam(name = "phone", value = "联系电话"),
            @ApiImplicitParam(name = "region", value = "所属区域"),
            @ApiImplicitParam(name = "level", value = "代理等级1.一级2.二级"),
            @ApiImplicitParam(name = "title", value = "机构名称"),
            @ApiImplicitParam(name = "address", value = "机构地址"),
            @ApiImplicitParam(name = "servicePhone", value = "服务电话"),
            @ApiImplicitParam(name = "username", value = "登录账号"),
            @ApiImplicitParam(name = "password", value = "登录密码"),
            @ApiImplicitParam(name = "qrCode", value = "推广二维码")
    })
    @RequestMapping(value = "/updateAgent", method = RequestMethod.POST)
    public Result<Boolean> updateAgent(AgentVO agentVO){

        return ResultUtil.success(agentService.updateAgent(agentVO));
    }

    @ApiOperation(value = "代理人审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true),
            @ApiImplicitParam(name = "auditStatus", value = "审核状态(auditStatus):0.删除1.未审核2.通过-1.拒绝", required = true)
    })
    @RequestMapping(value = "/auditAgent", method = RequestMethod.POST)
    public Result<Boolean> auditAgent(Integer id, Integer auditStatus){

        return ResultUtil.success(agentService.auditAgent(id,auditStatus));
    }
    @ApiOperation(value = "代理人删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true)
    })
    @RequestMapping(value = "/deleteAgent", method = RequestMethod.POST)
    public Result<Boolean> deleteAgent(Integer id){

        return ResultUtil.success(agentService.deleteAgent(id));
    }

    @ApiOperation(value = "代理人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "主键", required = true)
    })
    @RequestMapping(value = "/queryUserInfo", method = RequestMethod.POST)
    public Result<CommissionVO> queryUserInfo(Integer userId){

        return ResultUtil.success(agentService.queryUserInfo(userId));
    }

    @ApiOperation(value = "用户详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/queryDetails", method = RequestMethod.POST)
    public Result<Map<String, Object>> queryDetails(Integer userId){

        return ResultUtil.success(agentService.queryDetails(userId));
    }
}
