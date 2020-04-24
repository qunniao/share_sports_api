package com.gymnasium.information.Service;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.AttentionPo;
import com.gymnasium.information.PO.FriendGroupPO;
import com.gymnasium.information.PO.GroupCommentPO;
import com.gymnasium.information.PO.GroupPraisePO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GroupService
 * @projectName gymnasium
 * @date 2019/5/21 13:39
 */
public interface GroupService {

    Page<FriendGroupPO> pageFriendGroup(Paging page, FriendGroupPO friendGroupPO);

    //发布动态
    boolean addFriendGroupPO(List<String> file, FriendGroupPO friendGroupPO) throws Exception;

    //增加评论
    boolean addGroupCommentPO(GroupCommentPO commentPO);

    //增加点赞
    boolean addFriendGroupNums(GroupPraisePO groupPraisePO);

    //取消点赞
    boolean deleteFriendGroupNums(GroupPraisePO groupPraisePO);

    Result searchFriendGroup(Paging page, String word);

    Result followUser(AttentionPo attentionPo);

    Result unFollowUser(AttentionPo attentionPo);

    Result findFollowMoment(String userId);

    Result findAllFollowUser(String userId);

    Boolean isFollow(String userId, String followUserId);

}







