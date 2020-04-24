package com.gymnasium.personnel.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.core.page.Paging;
import com.gymnasium.data.Service.CodelogService;
import com.gymnasium.information.Dao.AttentionDao;
import com.gymnasium.information.Dao.GfriendDao;
import com.gymnasium.personnel.Dao.AuthenticationDao;
import com.gymnasium.personnel.Dao.UserAdditionDao;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.Authentication;
import com.gymnasium.personnel.PO.UserAdditionPO;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.personnel.Service.UserService;
import com.gymnasium.personnel.VO.AuthenticationVO;
import com.gymnasium.personnel.VO.UserVO;
import com.gymnasium.store.Dao.CommissionDao;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author 王志鹏
 * @title: UserServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 15:10
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CodelogService codelogService;

    @Autowired
    private UserAdditionDao userAdditionDao;

    @Autowired
    private AuthenticationDao authenticationDao;

    @Autowired
    private AttentionDao attentionDao;

    @Autowired
    private GfriendDao gfriendDao;

    @Autowired
    private CommissionDao commissionDao;


    @Override
    public Page<UserPO> pageUserPO(Paging page, UserPO userPO) {
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.ASC, "uid");

        Page<UserPO> gymShopPage = userDao.findAll(new Specification<UserPO>() {
            @Override
            public Predicate toPredicate(Root<UserPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (GeneralUtils.notEmpty(userPO.getUserId())) {

                    list.add(criteriaBuilder.like(root.get("userId").as(String.class),
                            "%" + userPO.getUserId() + "%"));
                }

                if (GeneralUtils.notEmpty(userPO.getUserName())) {
                    list.add(criteriaBuilder.like(root.get("userName").as(String.class),
                            "%" + userPO.getUserName() + "%"));
                }

                if (GeneralUtils.notEmpty(userPO.getUserPhone())) {
                    list.add(criteriaBuilder.like(root.get("userPhone").as(String.class),
                            "%" + userPO.getUserPhone() + "%"));
                }

                if (GeneralUtils.notEmpty(userPO.getLevel())) {
                    list.add(criteriaBuilder.equal(root.get("level").as(String.class), userPO.getLevel()));
                }
                if (GeneralUtils.notEmpty(userPO.getSex())) {
                    list.add(criteriaBuilder.equal(root.get("sex").as(String.class), userPO.getSex()));
                }

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return gymShopPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(UserVO userVO) throws SCException{

        UserPO oldUserPO = userDao.findByUnionId(userVO.getUnionId());
        if (ObjectUtil.isNull(oldUserPO)) {
            oldUserPO = userDao.findByOpenid(userVO.getOpenid());
        }

        if (ObjectUtil.isNotEmpty(oldUserPO)) {
            throw new SCException(ResultEnum.USER_NOT_NULL);

        }

        UserPO userPO = new UserPO();

        userPO.setUserId(RandomUtil.getShortUuid() + DateUtil.current("yyMMdd"));
        userPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        userPO.setLevel(0);
        userPO.setEnergy(0.000);
        userPO.setOpenid(userVO.getOpenid());
        userPO.setWebid(userVO.getWebid());
        userPO.setType(0);
        userPO.setSex(userVO.getSex());
        userPO.setAvatarUrl(userVO.getAvatarUrl());
        userPO.setUserNike(userVO.getUserNike());
        userPO.setUnionId(userVO.getUnionId());
        userPO.setOneUserId(userVO.getOneUserId());
        userPO.setTowUsuerId(userVO.getTowUsuerId());

        String nameAndPassword = "OYOC_" + (System.currentTimeMillis());

        JgUtil.testCreateGroup(nameAndPassword, nameAndPassword);

        userPO.setJiguangUsername(nameAndPassword);
        userPO.setJiguangPassword(nameAndPassword);

        userDao.save(userPO);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserAddition(UserAdditionPO userAdditionPO) {

        System.out.println("userAdditionPO: " + userAdditionPO);
        UserAdditionPO userAdditionPO1 = userAdditionDao.queryByUserId(userAdditionPO.getUserId());
        if (userAdditionPO1 != null) {
            userAdditionPO.setId(userAdditionPO1.getId());
        }
        System.out.println("userAdditionPO1" + userAdditionPO1);
        System.out.println("userAdditionPO" + userAdditionPO);
        userAdditionDao.saveAndFlush(userAdditionPO);
        UserPO userPO = userDao.findByUserId(userAdditionPO.getUserId());
        if (StringUtils.isEmpty(userPO)) {
            throw new SCException(ResultEnum.USER_REPEAT);
        }
        System.out.println("userPO" + userPO);
        userPO.setSex(userAdditionPO.getSex());
        userPO.setUserNike(userAdditionPO.getUserNike());
        userPO.setIdentityNumber(userAdditionPO.getIdentityNumber());
        userDao.save(userPO);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(MultipartFile files, UserVO userVO) {

        UserPO userPO = userDao.findByUserId(userVO.getUserId());

        if (GeneralUtils.isEmpty(userPO)) {
            throw new SCException(442, "用户不存在");
        }

        if (ObjectUtil.isNotEmpty(files)) {
            String image = FileUtils.uploadImage(files);
            userVO.setAvatarUrl(image);
        }

        BeanUtil.copyProperties(userVO, userPO, Arrays.asList("uid", "userId", "energy", "openid", "level"));

        UserPO save = userDao.save(userPO);

        return ObjectUtil.isNotEmpty(save);
    }

    @Override
    public Boolean uploadAvatar(String userId, String url) {

        UserPO userPO = userDao.findByUserId(userId);

        if (GeneralUtils.isEmpty(userPO)) {

            throw new SCException(400201, "该用户不存在");
        }

        userPO.setAvatarUrl(url);

        userDao.save(userPO);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCrashUser(UserAdditionPO userAdditionPO) {

        UserAdditionPO userAdditionPO1 = userAdditionDao.queryByUserId(userAdditionPO.getUserId());

        if (ObjectUtil.isNull(userAdditionPO1)) {
            throw new SCException(ResultEnum.NO_DATA_EXIST, "userAddition");
        }

        userAdditionPO1.setCrashName(userAdditionPO.getCrashName());
        userAdditionPO1.setCrashUserPhone(userAdditionPO.getCrashUserPhone());

        UserAdditionPO save = userAdditionDao.save(userAdditionPO1);

        return ObjectUtil.isNotEmpty(save);
    }

    @Override
    public UserVO queryUserByUserId(String userId) {
        UserPO userPO = userDao.findByUserId(userId);

        if (ObjectUtil.isEmpty(userPO)) {
            throw new SCException(ResultEnum.NO_DATA_EXIST, "user");
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userPO, userVO, Arrays.asList("uid"));
        return userVO;
    }

    @Override
    @Transactional
    public Result deleteUserByUserId(String userId) {
        UserPO byUserId = userDao.findByUserId(userId);
        if (byUserId == null) {
            return ResultUtil.error(444, "not found user");
        }
        userDao.deleteAllByUserId(userId);

        return ResultUtil.success();
    }

    @Override
    public Boolean certification(AuthenticationVO authenticationVO, MultipartFile files) {

        Authentication save;


        if (ObjectUtils.anyNotNull(files)) {

            String image = FileUtils.uploadImage(files);

            System.out.println(image);

            authenticationVO.setImage(image);
        }

        Authentication authentication = authenticationDao.findByUserId(authenticationVO.getUserId());

        if (ObjectUtils.anyNotNull(authentication)) {

            BeanUtil.copyProperties(authenticationVO, authentication);

            save = authenticationDao.save(authentication);
        } else {
            Authentication authenticationPO = new Authentication();

            BeanUtil.copyProperties(authenticationVO, authenticationPO);
            authenticationPO.setReviewState(0);
            authenticationPO.setStatus(1);
            save = authenticationDao.save(authenticationPO);
        }

        return ObjectUtils.anyNotNull(save.getId());

    }

    @Override
    public Boolean audit(AuthenticationVO authenticationVO) {

        Authentication authentication = authenticationDao.findByUserId(authenticationVO.getUserId());

        if (ObjectUtils.anyNotNull(authentication)) {

            authentication.setReviewState(authenticationVO.getReviewState());
            authentication.setReject(authenticationVO.getReject());

            authenticationDao.save(authentication);

            return true;
        }

        return false;
    }

    @Override
    public AuthenticationVO queryRealNameUser(Integer userId) {

        AuthenticationVO authenticationVO = new AuthenticationVO();

        Authentication authentication = authenticationDao.findByUserId(userId);

        if (ObjectUtil.isNotEmpty(authentication)) {

            BeanUtil.copyPropertie(authentication, authenticationVO);
            return authenticationVO;
        }

        return null;
    }

    @Override
    public Page<Authentication> pageRealNameInfo(Paging page) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<Authentication> authenticationPage = authenticationDao.findAll(new Specification<Authentication>() {

            @Override
            public Predicate toPredicate(Root<Authentication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        }, pageable);

        return authenticationPage;
    }

    @Override
    public Map<String, Object> countUserData(String userId) {

        Map<String, Object> map = new HashMap<>(16);

        Integer friendNumber = gfriendDao.countByAuserIdOrBuserId(userId, userId);

        Integer attentionNumber = attentionDao.countByUserId(userId);

        map.put("friendNumber", friendNumber);

        map.put("attentionNumber", attentionNumber);

        return map;
    }

    @Override
    public Integer queryUserRole(Integer userId) {

        Integer role = commissionDao.queryRole(userId);

        if (ObjectUtil.isNull(role)) {
            throw new SCException(400456, "用户身份为空");
        }

        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserByPhone(UserVO userVO, String code) throws SCException {
        //验证验证码
        if (!codelogService.validCode(userVO.getUserPhone(), code)) {
            throw new SCException(ResultEnum.CODE_TIME_OUT);
        }

        UserPO userPO = userDao.findByUserId(userVO.getUserId());

        userPO.setUserPhone(userVO.getUserPhone());

        userDao.saveAndFlush(userPO);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserAddition(UserAdditionPO userAdditionPO) throws SCException {
        UserAdditionPO userAdditionPO1 = userAdditionDao.queryByUserId(userAdditionPO.getUserId());
        if (userAdditionPO1 == null) {
            throw new SCException(ResultEnum.USER_REPEAT);
        }
        BeanUtil.copyProperties(userAdditionPO, userAdditionPO1, Arrays.asList("id", "userId"));
        userAdditionDao.save(userAdditionPO1);
        return false;
    }

}
