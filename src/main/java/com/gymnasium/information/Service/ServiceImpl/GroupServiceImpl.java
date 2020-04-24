package com.gymnasium.information.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.gymnasium.Util.DateUtil;
import com.gymnasium.Util.Enums.FinalEnum;
import com.gymnasium.Util.GeneralUtils;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.ResultUtil;
import com.gymnasium.Util.SCException;
import com.gymnasium.core.page.Paging;
import com.gymnasium.file.FastDFSClient;
import com.gymnasium.information.Dao.*;
import com.gymnasium.information.PO.*;
import com.gymnasium.information.Service.GroupService;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.personnel.Service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: GroupServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/21 13:39
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private FriendGroupDao friendGroupDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupCommentDao groupCommentDao;

    @Autowired
    private GroupPictoreDao groupPictoreDao;

    @Autowired
    private GroupPraiseDao groupPraiseDao;

    @Autowired
    private AttentionDao attentionDao;

    @Override
    public Page<FriendGroupPO> pageFriendGroup(Paging page, FriendGroupPO friendGroupPO) {
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.DESC, "mid");
        Page<FriendGroupPO> gymShopPage = friendGroupDao.findAll(new Specification<FriendGroupPO>() {
            @Override
            public Predicate toPredicate(Root<FriendGroupPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (GeneralUtils.notEmpty(friendGroupPO.getUserId())) {
                    list.add(criteriaBuilder.equal(root.get("userId").as(String.class), friendGroupPO.getUserId()));
                }
                if (GeneralUtils.notEmpty(friendGroupPO.getContent())) {
                    list.add(criteriaBuilder.like(root.get("userId").as(String.class), "%" + friendGroupPO.getContent() + "%"));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return gymShopPage;
    }

    @Override
    public boolean addFriendGroupPO(List<String> file, FriendGroupPO friendGroupPO) throws Exception {

        Timestamp times = DateUtil.getDateToTimestamp(new Date());
        friendGroupPO.setCreateTime(times);

        friendGroupDao.save(friendGroupPO);
        int mid = friendGroupPO.getMid();

        for (String url : file) {
            GroupPictorePO groupPictorePO = new GroupPictorePO();
            groupPictorePO.setMid(mid);
            groupPictorePO.setCreateTime(times);
            groupPictorePO.setUrl(url);
            groupPictoreDao.save(groupPictorePO);
        }
        return true;
    }

    @Override
    public boolean addGroupCommentPO(GroupCommentPO commentPO) {
        groupCommentDao.save(commentPO);
        return false;
    }

    @Override
    public boolean addFriendGroupNums(GroupPraisePO groupPraisePO) {
        GroupPraisePO groupPraisePO1 = groupPraiseDao.queryByMidAndUserId(groupPraisePO.getMid(), groupPraisePO.getUserId());
        if (groupPraisePO1 != null) {
            throw new SCException(11111, "已经点过赞了!");
        }
        groupPraiseDao.save(groupPraisePO);
        return true;
    }

    @Override
    public boolean deleteFriendGroupNums(GroupPraisePO groupPraisePO) {
        GroupPraisePO groupPraisePO1 = groupPraiseDao.queryByMidAndUserId(groupPraisePO.getMid(), groupPraisePO.getUserId());
        if (groupPraisePO1 != null) {
            groupPraiseDao.delete(groupPraisePO1);
        }
        return true;
    }

    @Override
    public Result searchFriendGroup(Paging page, String word) {
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());
        List<FriendGroupPO> first10ByContentLike = friendGroupDao.findFirst10ByContentLike("%" + word + "%", pageable);
        return ResultUtil.success(first10ByContentLike);
    }

    @Override
    public Result followUser(AttentionPo attentionPo) {
        String userId = attentionPo.getUserId();
        String followUserId = attentionPo.getFollowUserId();

        if (StrUtil.isNotBlank(userId) && userId.equals(followUserId)){
            throw new SCException(454551, "关注人和被关注人id不能相同");
        }
        AttentionPo temp = attentionDao.findByUserIdAndFollowUserId(userId, followUserId);
        if (temp == null) {
            attentionDao.save(attentionPo);
        } else {
            return ResultUtil.error(444, "已经关注");
        }
        return ResultUtil.success();
    }

    @Override
    @Transactional
    public Result unFollowUser(AttentionPo attentionPo) {
        AttentionPo temp = attentionDao.findByUserIdAndFollowUserId(attentionPo.getUserId(), attentionPo.getFollowUserId());
        if (temp != null) {
            attentionDao.deleteByUserIdAndFollowUserId(attentionPo.getUserId(), attentionPo.getFollowUserId());
        }
        return ResultUtil.success();
    }

    @Override
    public Result findFollowMoment(String userId) {
        List<AttentionPo> list = attentionDao.findByUserId(userId);
        List<FriendGroupPO> result = new ArrayList<FriendGroupPO>();
        for (AttentionPo attentionPo : list) {
            List<FriendGroupPO> byUserId = friendGroupDao.findByUserId(attentionPo.getFollowUserId());
            result.addAll(byUserId);
        }
        return ResultUtil.success(result);
    }

    @Override
    public Result findAllFollowUser(String userId) {
        List<AttentionPo> byUserId = attentionDao.findByUserId(userId);
        return ResultUtil.success(byUserId);
    }

    @Override
    public Boolean isFollow(String userId, String followUserId) {

        AttentionPo attention = attentionDao.findByUserIdAndFollowUserId(userId, followUserId);

        if (ObjectUtil.isNotNull(attention)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
