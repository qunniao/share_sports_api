package com.gymnasium.information.Controller;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.AttentionPo;
import com.gymnasium.information.PO.FriendGroupPO;
import com.gymnasium.information.PO.GroupCommentPO;
import com.gymnasium.information.PO.GroupPraisePO;
import com.gymnasium.information.Service.GroupService;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GroupController
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 15:02
 */

@Api(tags = "广场动态")
@RestController
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @ApiOperation(value = "分页查询动态")
    @ApiImplicitParam(name = "userId", value = "本人userId", required = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "userId", value = "本人userId", required = false)
    })
    @RequestMapping(value = "/pageFriendGroup", method = RequestMethod.POST)
    public Result<Page<FriendGroupPO>> pageFriendGroup(Paging page, FriendGroupPO friendGroupPO) throws SCException {

        return ResultUtil.success(groupService.pageFriendGroup(page, friendGroupPO));
    }


    @ApiOperation(value = "分页模糊查询动态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "容量", required = true),
            @ApiImplicitParam(name = "word", value = "关键词")
    })
    @RequestMapping(value = "/searchFriendGroup", method = RequestMethod.POST)
    public Result searchFriendGroup(Paging page, String word) throws SCException {
        return groupService.searchFriendGroup(page, word);
    }


    @ApiOperation(value = "发布说说")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "多文件", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "content", value = "内容", required = true),
            @ApiImplicitParam(name = "place", value = "地点", required = true),
            @ApiImplicitParam(name = "longitude", value = "经度", required = true),
            @ApiImplicitParam(name = "latitude", value = "纬度", required = true)
    })
    @RequestMapping(value = "/addFriendGroupPO", method = RequestMethod.POST)
    public Result<Boolean> addFriendGroupPO(@RequestParam List<String> files, @ApiIgnore("FriendGroupPO") FriendGroupPO friendGroupPO) throws Exception {

        return ResultUtil.success(groupService.addFriendGroupPO(files, friendGroupPO));
    }

    @ApiOperation(value = "增加评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "动态主键", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true),
            @ApiImplicitParam(name = "comment", value = "品论内容", required = true)
    })
    @RequestMapping(value = "/addGroupCommentPO", method = RequestMethod.POST)
    public Result<Boolean> addGroupCommentPO(GroupCommentPO commentPO){
        return ResultUtil.success(groupService.addGroupCommentPO(commentPO));
    }

    @ApiOperation(value = "增加点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "动态主键", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @RequestMapping(value = "/addFriendGroupNums", method = RequestMethod.POST)
    public Result<Boolean> addFriendGroupNums(GroupPraisePO groupPraisePO) throws Exception {
        return ResultUtil.success(groupService.addFriendGroupNums(groupPraisePO));
    }

    @ApiOperation(value = "取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mid", value = "动态主键", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true)
    })
    @RequestMapping(value = "/deleteFriendGroupNums", method = RequestMethod.POST)
    public Result<Boolean> deleteFriendGroupNums(GroupPraisePO groupPraisePO) throws Exception {
        return ResultUtil.success(groupService.deleteFriendGroupNums(groupPraisePO));
    }


    @ApiOperation(value = "关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "关注人Id", required = true),
            @ApiImplicitParam(name = "followUserId", value = "被关注人Id", required = true)
    })
    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    public Result followUser(AttentionPo attentionPo) {
        return groupService.followUser(attentionPo);
    }

    @ApiOperation(value = "查询用户关注的所有的人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "关注人Id", required = true)
    })
    @RequestMapping(value = "/findAllFollowUser", method = RequestMethod.POST)
    public Result findAllFollowUser(String userId) {
        return groupService.findAllFollowUser(userId);
    }

    @ApiOperation(value = "查询是否关注过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "关注人Id", required = true),
            @ApiImplicitParam(name = "followUserId", value = "被关注人Id", required = true),
    })
    @RequestMapping(value = "/isFollow", method = RequestMethod.POST)
    public Result<Boolean> isFollow(String userId, String followUserId) {
        return ResultUtil.success(groupService.isFollow(userId, followUserId));
    }

    @ApiOperation(value = "取消关注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "关注人Id", required = true),
            @ApiImplicitParam(name = "followUserId", value = "被关注人Id", required = true)
    })
    @RequestMapping(value = "/unFollowUser", method = RequestMethod.POST)
    public Result unFollowUser(AttentionPo attentionPo) {
        return groupService.unFollowUser(attentionPo);
    }

    @ApiOperation(value = "查询关注圈动态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户的Id", required = true),
    })
    @RequestMapping(value = "/findFollowMoment", method = RequestMethod.POST)
    public Result findFollowMoment(String userId) {
        return groupService.findFollowMoment(userId);
    }

}
