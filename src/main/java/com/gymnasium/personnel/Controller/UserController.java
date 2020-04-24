package com.gymnasium.personnel.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.core.page.Paging;
import com.gymnasium.personnel.Dao.UserAdditionDao;
import com.gymnasium.personnel.PO.Authentication;
import com.gymnasium.personnel.PO.UserAdditionPO;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.personnel.Service.UserService;
import com.gymnasium.personnel.VO.AuthenticationVO;
import com.gymnasium.personnel.VO.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 王志鹏
 * @title: UserController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 16:25
 */
@Api(tags = "微信用户人员信息")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAdditionDao userAdditionDao;


    @ApiOperation(value = "分页查询用户", notes = "支持性别,年龄,编号,会员等级条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true),
            @ApiImplicitParam(name = "sex", value = "性别"),
            @ApiImplicitParam(name = "age", value = "年龄"),
            @ApiImplicitParam(name = "userId", value = "用户编号"),
            @ApiImplicitParam(name = "userName", value = "用户真实姓名"),
            @ApiImplicitParam(name = "userPhone", value = "用户手机号"),
            @ApiImplicitParam(name = "level", value = "会员等级")
    })
    @RequestMapping(value = "/pageUserPO", method = RequestMethod.POST)
    public Result<UserAdditionPO> pageUserPO(Paging page, UserPO userPO) {

        return ResultUtil.success(userService.pageUserPO(page, userPO));
    }

    @ApiOperation(value = "查询用户详细资料", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编码", required = true)
    })
    @RequestMapping(value = "/queryUserAdditionPOByUserId", method = RequestMethod.POST)
    public Result<UserAdditionPO> queryUserAdditionPOByUserId(@RequestParam String userId) {

        return ResultUtil.success(userAdditionDao.queryByUserId(userId));
    }

    @ApiOperation(value = "完善用户信息,更新用户信息", notes = "跳过更新以下重要字段uid,userId,energy,openid,level")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "uid", value = "用户id",required = true),
            @ApiImplicitParam(name = "userName", value = "用户真实姓名"),
            @ApiImplicitParam(name = "userNike", value = "用户昵称"),
            @ApiImplicitParam(name = "userPhone", value = "用户电话"),
            @ApiImplicitParam(name = "sex", value = "性别1男,2女"),
            @ApiImplicitParam(name = "oneUserId", value = "分享人uid"),
            @ApiImplicitParam(name = "towUsuerId", value = "二级分享人编号"),
            @ApiImplicitParam(name = "energy", value = "能量值"),
            @ApiImplicitParam(name = "level", value = "权限等级,0初始,1体验,2大众,3精英,4皇家"),
            @ApiImplicitParam(name = "type", value = "0未健身,1正在健身,2等待核销"),
            @ApiImplicitParam(name = "files", value = "用户头像")
    })
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Result<UserVO> updateUser(MultipartFile files, UserVO userVO) {

        return ResultUtil.success(userService.updateUser(files,userVO));
    }

    @ApiOperation(value = "上传用户头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "url", value = "路径", required = true)
    })
    @RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
    public Result<Boolean> uploadAvatar(String userId, String url) {

        return ResultUtil.success(userService.uploadAvatar(userId, url));
    }

    @ApiOperation(value = "添加/修改用户详细数据", notes = "注意!条件参数为typeid(date_usertype 表主键),详情参考UserAdditionPO")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号str", required = true),
            @ApiImplicitParam(name = "purpose", value = "目的int", required = true),
            @ApiImplicitParam(name = "bodyType", value = "体型int", required = true),
            @ApiImplicitParam(name = "figure", value = "身材int", required = true),
            @ApiImplicitParam(name = "age", value = "用户年龄int"),
            @ApiImplicitParam(name = "job", value = "用户职业int", required = true),
            @ApiImplicitParam(name = "height", value = "身高int"),
            @ApiImplicitParam(name = "weight", value = "体重int"),
            @ApiImplicitParam(name = "income", value = "收入类型int", required = true),
            @ApiImplicitParam(name = "signature", value = "个性签名", required = true)
    })
    @RequestMapping(value = "/addUserAddition", method = RequestMethod.POST)
    public Result<UserAdditionPO> addUserAddition(UserAdditionPO userAdditionPO) {

        return ResultUtil.success(userService.addUserAddition(userAdditionPO));
    }

    @ApiOperation(value = "更新/添加 紧急联系人", notes = "无")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号str", required = true),
            @ApiImplicitParam(name = "crashName", value = "紧急联系人姓名str", required = true),
            @ApiImplicitParam(name = "crashUserPhone", value = "紧急联系电话str", required = true),
    })
    @RequestMapping(value = "/updateCrashUser", method = RequestMethod.POST)
    public Result<Boolean> updateCrashUser(UserAdditionPO userAdditionPO) {

        return ResultUtil.success(userService.updateCrashUser(userAdditionPO));
    }


    @ApiOperation("更新用户手机号")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    @RequestMapping(value = "/updateUserByPhone", method = RequestMethod.POST)
    public Result<UserVO> updateUserByPhone(UserVO userVO, @RequestParam("code") String code) {
        return ResultUtil.success(userService.updateUserByPhone(userVO, code));
    }

    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    @RequestMapping(value = "/deleteUserByUserId", method = RequestMethod.POST)
    public Result<UserVO> deleteUserByUserId(String userId) {
        return userService.deleteUserByUserId(userId);
    }



    @ApiOperation("根据用户编号查询用户")
    @RequestMapping(value = "/queryUserByUserId", method = RequestMethod.POST)
    public Result<UserVO> queryUserByUserId(@RequestParam("userId") String userId) {
        return ResultUtil.success(userService.queryUserByUserId(userId));
    }

    @ApiOperation("实名认证提交接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "trueName", value = "姓名", required = true),
            @ApiImplicitParam(name = "idCard", value = "身份证号", required = true),
            @ApiImplicitParam(name = "files", value = "files", required = true)
    })
    @RequestMapping(value = "/certification", method = RequestMethod.POST)
    public Result<Boolean> certification(AuthenticationVO authenticationVO, MultipartFile files) {
        return ResultUtil.success(userService.certification(authenticationVO,files));
    }

    @ApiOperation("实名认证审核接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "reviewState", value = "审核状态(1.审核通过2.拒绝)", required = true),
            @ApiImplicitParam(name = "reject", value = "拒绝原因")
    })
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public Result<Boolean> audit(AuthenticationVO authenticationVO) {

        return ResultUtil.success(userService.audit(authenticationVO));
    }

    @ApiOperation("查询用户实名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/queryRealNameUser", method = RequestMethod.POST)
    public Result<AuthenticationVO> queryRealNameUser(Integer userId) {
        return ResultUtil.success(userService.queryRealNameUser(userId));
    }

    @ApiOperation("分页查询实名认证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页容量", required = true)
    })
    @RequestMapping(value = "/pageRealNameInfo", method = RequestMethod.POST)
    public Result<Page<Authentication>> pageRealNameInfo(Paging page) {
        return ResultUtil.success(userService.pageRealNameInfo(page));
    }

    @ApiOperation(value = "统计用户的搭伙好友和我的关注数量",
            notes = "friendNumber: 搭伙好友数量; attentionNumber: 关注数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "本人编号 或者 搭伙人编号", required = true)
    })
    @RequestMapping(value = "/countUserData", method = RequestMethod.POST)
    public Result<Map<String, Object>> countUserData(String userId){

        return ResultUtil.success(userService.countUserData(userId));
    }

    @ApiOperation(value = "查询用户身份")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @RequestMapping(value = "/queryUserRole", method = RequestMethod.POST)
    public Result<Integer> queryUserRole(Integer userId){

        return ResultUtil.success(userService.queryUserRole(userId));
    }

}