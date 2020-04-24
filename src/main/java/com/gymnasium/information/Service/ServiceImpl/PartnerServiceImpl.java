package com.gymnasium.information.Service.ServiceImpl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.vo.DateVo;
import com.gymnasium.core.page.Paging;
import com.gymnasium.data.Dao.CityDao;
import com.gymnasium.data.PO.CityPO;
import com.gymnasium.information.Dao.GfriendDao;
import com.gymnasium.information.Dao.MatchRecordDao;
import com.gymnasium.information.Dao.PartnerRecordDao;
import com.gymnasium.information.Dao.PartnerpoolDao;
import com.gymnasium.information.PO.GfriendPO;
import com.gymnasium.information.PO.MatchRecord;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.information.PO.PartnerpoolPO;
import com.gymnasium.information.Service.PartnerService;
import com.gymnasium.information.VO.PartnerUserVO;
import com.gymnasium.information.VO.PartnerpoolVO;
import com.gymnasium.order.PO.ExpensesRecordPO;
import com.gymnasium.personnel.Dao.UserAdditionDao;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.UserAdditionPO;
import com.gymnasium.personnel.PO.UserPO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: PartnerServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/18 10:17
 */
@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerpoolDao partnerpoolDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GfriendDao gfriendDao;

    @Autowired
    private PartnerRecordDao partnerRecordDao;

    @Autowired
    private UserAdditionDao userAdditionDao;

    @Autowired
    private MatchRecordDao matchRecordDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private FlowRecordUtil flowRecordUtil;

    @Override
    public List<UserPO> queryGoodFriendList(String auserId) {
        List<UserPO> userPOS = new ArrayList<>();
        UserPO userPO = null;
        List<GfriendPO> list = gfriendDao.queryByAuserId(auserId);

        for (GfriendPO gfriendPO : list) {
            userPO = userDao.findByUserId(gfriendPO.getBuserId());
            if (userPO != null) {
                userPOS.add(userPO);
            }
        }

        return userPOS;
    }

    //更新/添加用户搭伙目的 添加搭伙池
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPartnerpool(PartnerpoolPO partnerpoolPO) {

        System.err.println(partnerpoolPO);

        PartnerpoolPO partnerpoolPO1 = partnerpoolDao.queryByUserId(partnerpoolPO.getUserId());

        System.err.println(partnerpoolPO1);

        if (partnerpoolPO1 != null) {
            //修改操作
            partnerpoolPO.setId(partnerpoolPO1.getId());

            BeanUtil.copyProperties(partnerpoolPO, partnerpoolPO1);

            System.err.println(partnerpoolPO1);

            partnerpoolDao.save(partnerpoolPO1);

        } else {
            UserAdditionPO userAdditionPO = userAdditionDao.queryByUserId(partnerpoolPO.getUserId());

            //用户详细资料不存在 拿不到年龄
            if (userAdditionPO == null) {
                throw new SCException(ResultEnum.USER_ITEM_IS_NULL);
            }
            partnerpoolPO.setStatus(SysConstant.STATUS_Enable_INT.getConstant());
            partnerpoolPO.setAge(userAdditionPO.getAge());
            partnerpoolDao.save(partnerpoolPO);
        }

        return true;
    }

    @Override
    public List<PartnerUserVO> pagePartnerpool(Paging page, PartnerpoolVO partnerpoolVO) {

        UserPO user = userDao.findByUserId(partnerpoolVO.getUserId());

        if (ObjectUtil.isNull(user)) {
            throw new SCException(ResultEnum.USER_REPEAT);
        }

        // 判断等级计算剩余名额
        Integer places = verifyPlaces(page, user.getLevel());

        //本人结果
        PartnerpoolPO partnerpoolPO1 = partnerpoolDao.queryByUserId(partnerpoolVO.getUserId());

        // 根据本人结果 排除自己得到匹配的列表
        List<PartnerpoolPO> partnerpoolPOPage = partnerpoolDao.findAll(new Specification<PartnerpoolPO>() {
            @Override
            public Predicate toPredicate(Root<PartnerpoolPO> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                return queryFilterExcute(partnerpoolPO1, root, criteriaQuery, criteriaBuilder);
            }
        });

        System.err.println(partnerpoolPOPage);


        // 将匹配时间列表添加进集合中
        List<Timestamp> u2startTimeList = new ArrayList<>();
        List<Timestamp> u2endList = new ArrayList<>();

        u2startTimeList.add(partnerpoolPO1.getStartTime1());
        u2startTimeList.add(partnerpoolPO1.getStartTime2());
        u2startTimeList.add(partnerpoolPO1.getStartTime3());
        u2startTimeList.add(partnerpoolPO1.getStartTime4());
        u2endList.add(partnerpoolPO1.getEndTime1());
        u2endList.add(partnerpoolPO1.getEndTime2());
        u2endList.add(partnerpoolPO1.getEndTime3());
        u2endList.add(partnerpoolPO1.getEndTime4());

        //用户目的字符串
        List<String> transferedList = ListUtil.transferArrayToList(partnerpoolPO1.getPurpose().split(","));
        // 将 身材 字符串转成 集合
        List<String> transferedFigureList = ListUtil.transferArrayToList(partnerpoolPO1.getFigure().split(","));
        // 将 职位 字符串转成 集合
        List<String> transferedJobList = ListUtil.transferArrayToList(partnerpoolPO1.getJob().split(","));

        System.out.println("当前用户:" + transferedList);
        System.out.println(partnerpoolPOPage.size() + ":循环总条数");
        System.out.println();

        // 遍历 搭伙匹配列表
        for (int i = partnerpoolPOPage.size() - 1; i >= 0; i--) {
            System.out.println("------------------华丽分割线---------------------");
            PartnerpoolPO partnerpoolPO = partnerpoolPOPage.get(i);
            // s
            System.out.println(partnerpoolPO.getPurpose() + "????????");
            //目的 无需会员
            boolean flag = true;

            List<String> purposeList = Arrays.asList(partnerpoolPO.getPurpose().split(","));
            System.out.println("匹配用户!!!!!:" + purposeList);
            c:
            for (int j = 0; j < transferedList.size(); j++) {
                System.out.println(purposeList.contains(transferedList.get(j)) + ":" + transferedList.get(j));

                if (purposeList.contains(transferedList.get(j))) {
                    flag = false;
                    break c;
                }
            }
            if (flag) {
                partnerpoolPOPage.remove(i);
                continue;
            }

            //身材 精英会员
            if (GeneralUtils.notEmpty(partnerpoolPO.getFigure())) {
                boolean figureflag = true;
                List<String> figureList = Arrays.asList(partnerpoolPO.getFigure().split(","));
                System.out.println(figureList + "=figureList??");
                for (String figure : transferedFigureList) {
                    if (figureList.contains(figure)) {
                        figureflag = false;
                        break;
                    }
                }
                if (figureflag) {
                    partnerpoolPOPage.remove(i);
                    continue;
                }
            }

            //职位 皇家会员
            if (GeneralUtils.notEmpty(partnerpoolPO.getJob())) {
                boolean jobflag = true;
                List<String> jobList = Arrays.asList(partnerpoolPO.getJob().split(","));
                for (String job : transferedJobList) {
                    if (jobList.contains(job)) {
                        jobflag = false;
                        break;
                    }
                }
                if (jobflag) {
                    partnerpoolPOPage.remove(i);
                    continue;
                }
            }
        }

        List<UserPO> userPOList = new ArrayList<UserPO>();

        for (PartnerpoolPO partnerpoolPO : partnerpoolPOPage) {
            List<Timestamp> u1startTimeList = new ArrayList<>();
            List<Timestamp> u1endList = new ArrayList<>();
            u1startTimeList.add(partnerpoolPO.getStartTime1());
            u1startTimeList.add(partnerpoolPO.getStartTime2());
            u1startTimeList.add(partnerpoolPO.getStartTime3());
            u1startTimeList.add(partnerpoolPO.getStartTime4());
            u1endList.add(partnerpoolPO.getEndTime1());
            u1endList.add(partnerpoolPO.getEndTime2());
            u1endList.add(partnerpoolPO.getEndTime3());
            u1endList.add(partnerpoolPO.getEndTime4());

            for (int i = 0; i < u1startTimeList.size(); i++) {

                Timestamp u1StartTime = u1startTimeList.get(i);
                Timestamp u1endTime = u1endList.get(i);
                if (u1StartTime == null && u1endTime == null) {
                    continue;
                }
                boolean timeFlag = false;
                for (int j = 0; j < u1startTimeList.size(); j++) {
                    Timestamp u2StartTime = u2startTimeList.get(j);
                    Timestamp u2endTime = u2endList.get(j);
                    if (u2StartTime == null && u2endTime == null) {
                        continue;
                    }
                    if (DateUtil.intersection(u1StartTime, u1endTime, u2StartTime, u2endTime)) {
                        UserPO userPO = userDao.findByUserId(partnerpoolPO.getUserId());
                        if (userPO != null) {
                            userPOList.add(userPO);
                        }

                        timeFlag = true;
                        break;
                    }
                }
                if (timeFlag) break;
            }
        }

        int start = (page.getPageNum() - 1) * 5;
        int end = ((page.getPageNum() - 1) * 5) + page.getPageSize();

        List<UserPO> pageUserPOList = new ArrayList<UserPO>();
        for (int i = 0; i < userPOList.size(); i++) {

            if (start <= i && i < end) {
                pageUserPOList.add(userPOList.get(i));
            }
        }

        List<PartnerUserVO> partnerUserVOList = new ArrayList<>();

        for (UserPO userPO : pageUserPOList) {
            PartnerUserVO partnerUserVO = new PartnerUserVO();
            PartnerpoolPO partnerpoolPO = partnerpoolDao.queryByUserId(userPO.getUserId());

            partnerUserVO.setEndTime(partnerpoolPO.getEndTime1());
            partnerUserVO.setStartTime(partnerpoolPO.getStartTime1());
            CityPO cityPO = cityDao.queryById(partnerpoolPO.getRegionId());
            String[] getMergerName = cityPO.getMergerName().split(",");
            String cityName = cityPO.getMergerName();

            if (getMergerName.length > 2) {
                cityName = getMergerName[2] + getMergerName[3];
            }
            partnerUserVO.setCityName(cityName);
            partnerUserVO.setHeadUrl(userPO.getAvatarUrl());
            partnerUserVO.setPurpose(partnerpoolPO.getPurpose());
            partnerUserVO.setUesrId(userPO.getUserId());
            partnerUserVO.setJiguangPassword(userPO.getJiguangPassword());
            partnerUserVO.setJiguangUsername(userPO.getJiguangUsername());
            partnerUserVO.setUserNike(userPO.getUserNike());

            partnerUserVOList.add(partnerUserVO);
        }

        if (CollectionUtil.isEmpty(partnerUserVOList)) {
            throw new SCException(400124, "搭伙列表为空");
        }

        MatchRecord matchRecord = matchRecordDao.findAllByUserId(user.getUid());

        if (ObjectUtil.isNull(matchRecord)) {

            MatchRecord matchRecordPO = new MatchRecord();
            matchRecordPO.setUserId(user.getUid());
            matchRecordPO.setPage(page.getPageNum());
            matchRecordPO.setNumber(places);
            matchRecordPO.setLevel(user.getLevel());
            matchRecordPO.setCreateTime(new Date());
            matchRecordDao.save(matchRecordPO);
        } else {
            matchRecord.setNumber(places);
            matchRecord.setPage(page.getPageNum());
            matchRecordDao.save(matchRecord);
        }

        return partnerUserVOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGoogFriend(String aUserId, String bUserId) throws SCException {

        Integer integer = gfriendDao.countByAuserIdOrBuserId(aUserId, bUserId);

        System.err.println(integer);

        //已经是好友了
        if (ObjectUtil.isNotEmpty(integer) && integer > 0) {
            return true;
        }

        // 添加好友
        Boolean aBoolean = addGfriend(aUserId, bUserId);
        Boolean bBoolean = addGfriend(bUserId, aUserId);

        return aBoolean == true && bBoolean == true;
    }

    private Boolean addGfriend(String aUserId, String bUserId) {

        GfriendPO gfriendPO = new GfriendPO();
        gfriendPO.setAuserId(aUserId);
        gfriendPO.setBuserId(bUserId);
        gfriendPO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        gfriendDao.save(gfriendPO);

        return ObjectUtils.anyNotNull(gfriendPO.getId());
    }

    //A添加搭伙记录
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addPartnerRecord(PartnerRecordPO partnerRecordPO, String startTime) {

        System.err.println("startTime" + startTime);

        List<PartnerRecordPO> partnerRecordPOList = partnerRecordDao.findAll(new Specification<PartnerRecordPO>() {
            @Override
            public Predicate toPredicate(Root<PartnerRecordPO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList();

                list.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("auserId").as(String.class), partnerRecordPO.getAuserId()),
                        criteriaBuilder.equal(root.get("buserId").as(String.class), partnerRecordPO.getBuserId())
                ));

                list.add(criteriaBuilder.equal(root.get("startTime").as(String.class), startTime));

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        });

        if (CollectionUtil.isNotEmpty(partnerRecordPOList)) {
            throw new SCException(10200, "搭伙记录已经存在");
        }

        System.err.println(partnerRecordPO);
        Timestamp times = DateUtil.getDateToTimestamp(new Date());
        partnerRecordPO.setCreateTime(times);
        partnerRecordPO.setStartTime(Timestamp.valueOf(startTime));
        partnerRecordPO.setType(PartnerRecordPO.TYPE_INIT);
        partnerRecordPO.setPromiseTime(DateUtil.getDateToDateString(partnerRecordPO.getEndTime(), "yyyy-MM-dd"));

        PartnerRecordPO save = partnerRecordDao.save(partnerRecordPO);

        return save.getId();
    }

    @Override
    public boolean partnerYES(int id) {
//        partnerRecordDao
        PartnerRecordPO partnerRecordPO = partnerRecordDao.getOne(id);
        String payType = partnerRecordPO.getPayType();
        UserPO AuserPO = userDao.findByUserId(partnerRecordPO.getAuserId());
        UserPO BuserPO = userDao.findByUserId(partnerRecordPO.getBuserId());
        double pay = 0;
        if (payType.equals("AA")) {
            if (AuserPO.getEnergy() < 10) {
                throw new SCException(121111, "发起人能量值小于10");
            }
            if (BuserPO.getEnergy() < 10) {
                throw new SCException(22222, "被搭伙人能量值小于10");
            }
            pay = 10;
            AuserPO.setEnergy(AuserPO.getEnergy() - pay);
            BuserPO.setEnergy(BuserPO.getEnergy() - pay);

        } else if (payType.equals("A")) {
            pay = 20;
            AuserPO.setEnergy(AuserPO.getEnergy() - pay);
        } else if (payType.equals("B")) {
            pay = 20;
            BuserPO.setEnergy(BuserPO.getEnergy() - pay);
        }

        addGoogFriend(partnerRecordPO.getAuserId(), partnerRecordPO.getBuserId());
        partnerRecordPO.setAtype(0);
        partnerRecordPO.setBtype(0);
        partnerRecordPO.setType(2);
        partnerRecordDao.save(partnerRecordPO);
        userDao.save(AuserPO);
        userDao.save(BuserPO);

        // 搭伙抵押
        flowRecordUtil.addExpensesRecord(AuserPO.getUserId(), pay, AuserPO.getEnergy(), 0,
                ExpensesRecordPO.Record_type_freeze, 5, "搭伙保证金冻结", "同意搭伙扣除", partnerRecordPO.getGymShopId());

        flowRecordUtil.addExpensesRecord(BuserPO.getUserId(), pay, BuserPO.getEnergy(), 0,
                ExpensesRecordPO.Record_type_freeze, 5, "搭伙保证金冻结", "同意搭伙扣除", partnerRecordPO.getGymShopId());

        System.out.println("111111");
        return true;
    }

    @Override
    public boolean partnerNO(int id) {

        PartnerRecordPO partnerRecordPO = partnerRecordDao.getOne(id);
        partnerRecordPO.setType(PartnerRecordPO.TYPE_NO);

        PartnerRecordPO save = partnerRecordDao.save(partnerRecordPO);

        return ObjectUtils.anyNotNull(save);
    }

    @Override
    public boolean partnerSTOP(String userId) {
        PartnerpoolPO partnerpoolPO = partnerpoolDao.queryByUserId(userId);
        if (partnerpoolPO == null) {
            throw new SCException(111111, "搭伙池未存在!");
        }
        partnerpoolPO.setStatus(SysConstant.STATUS_DISABLE_INT.getConstant());
        partnerpoolDao.save(partnerpoolPO);
        return false;
    }

    @Override
    public MatchRecord queryMatchRecord(Integer userId) {

        return matchRecordDao.findAllByUserId(userId);
    }

    @Override
    public Page<PartnerRecordPO> queryPartnerRecord(Paging page, PartnerRecordPO partnerRecordPO, DateVo dateVo) {


        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.DESC, "id");

        Page<PartnerRecordPO> poPage = partnerRecordDao.findAll((Specification<PartnerRecordPO>)
                (root, criteriaQuery, criteriaBuilder) -> {
                    List<Predicate> list = new ArrayList<Predicate>();

                    if (!StringUtils.isBlank(partnerRecordPO.getAuserId())) {
                        list.add(criteriaBuilder.equal(root.get("auserId").as(String.class), partnerRecordPO.getAuserId()));
                    }

                    if (!StringUtils.isBlank(partnerRecordPO.getBuserId())) {
                        list.add(criteriaBuilder.equal(root.get("buserId").as(String.class), partnerRecordPO.getBuserId()));
                    }

                    if (!StringUtils.isBlank(partnerRecordPO.getBuserId())) {
                        list.add(criteriaBuilder.equal(root.get("gymShopId").as(String.class), partnerRecordPO.getGymShopId()));
                    }

                    String formatDate = dateVo.formatDate();

                    if (StrUtil.isNotBlank(formatDate)) {

                        list.add(criteriaBuilder.like(root.get("createTime").as(String.class), formatDate + "%"));
                    }

                    Predicate[] p = new Predicate[list.size()];

                    return criteriaBuilder.and(list.toArray(p));
                }, pageable);

        return poPage;
    }

    @Override
    public Page<PartnerRecordPO> PagePartnerRecord(Paging page, String userId) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.DESC, "id");

        Page<PartnerRecordPO> poPage = partnerRecordDao.findAll(new Specification<PartnerRecordPO>() {
            @Override
            public Predicate toPredicate(Root<PartnerRecordPO> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (!StringUtils.isBlank(userId)) {
                    list.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("auserId").as(String.class), userId),
                            criteriaBuilder.equal(root.get("buserId").as(String.class), userId)));
                }

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return poPage;
    }

    @Override
    public List<PartnerRecordPO> queryByABuserId(String auserid, String buserId) {

        return partnerRecordDao.queryByAuserIdAndBuserIdOrderByIdDesc(auserid, buserId);
    }

    //查询用户搭伙计划
    @Override
    public PartnerpoolPO queryPartnerpoolByUserId(String userId) {

        PartnerpoolPO partnerpoolPO = partnerpoolDao.queryByUserId(userId);

        if (partnerpoolPO == null) {
            throw new SCException(ResultEnum.USER_NOTIN_POOL);
        }

        return partnerpoolPO;
    }

    public Predicate queryFilterExcute(PartnerpoolPO partnerpoolPO,
                                       Root<PartnerpoolPO> root,
                                       CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> list = new ArrayList<Predicate>();

        if (GeneralUtils.notEmpty(partnerpoolPO.getRegionId()) && partnerpoolPO.getRegionId() != 0) {
            list.add(criteriaBuilder.equal(root.get("regionId").as(Integer.class), partnerpoolPO.getRegionId()));
        }

        //体型
        if (partnerpoolPO.getBody() != 10 && partnerpoolPO.getBody() != 0) {
            System.out.println("add体型");
            list.add(criteriaBuilder.equal(root.get("body").as(Integer.class), partnerpoolPO.getBody()));
        }

        //性别
        if (GeneralUtils.notZero(partnerpoolPO.getGender())) {
            System.out.println("add性别");
            list.add(criteriaBuilder.equal(root.get("gender").as(Integer.class), partnerpoolPO.getGender()));
        }

        //年龄
        if (GeneralUtils.notZero(partnerpoolPO.getStartAge()) && GeneralUtils.notZero(partnerpoolPO.getEndAge())) {
            System.out.println("add年龄");
            list.add(criteriaBuilder.between(root.get("age").as(Integer.class), partnerpoolPO.getStartAge(),
                    partnerpoolPO.getEndAge()));
        }

        //身材
        /*if (GeneralUtils.notZero(partnerpoolVO.getFigure())) {
            System.out.println("add身材");
            list.add(criteriaBuilder.equal(root.get("figure").as(Integer.class), partnerpoolVO.getFigure()));
        }*/

        //人群等级
        if (GeneralUtils.notZero(partnerpoolPO.getLevel())) {
            System.out.println("人群等级");
            list.add(criteriaBuilder.equal(root.get("level").as(Integer.class), partnerpoolPO.getLevel()));
        }
        //职业类型
        /*if (GeneralUtils.notZero(partnerpoolVO.getJob())) {
            System.out.println("职业类型");
            list.add(criteriaBuilder.equal(root.get("job").as(Integer.class), partnerpoolVO.getJob()));
        }*/
        //收入类型
        if (GeneralUtils.notZero(partnerpoolPO.getIncome())) {
            System.out.println("收入类型");
            list.add(criteriaBuilder.equal(root.get("income").as(Integer.class), partnerpoolPO.getIncome()));
        }

        list.add(criteriaBuilder.notEqual(root.get("userId").as(String.class), partnerpoolPO.getUserId()));

        list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), 1));
        Predicate[] p = new Predicate[list.size()];

        return criteriaBuilder.and(list.toArray(p));
    }

    private Integer verifyPlaces(Paging page, Integer level) {

        Integer pageNum = page.getPageNum();

        Integer places = 0;

        switch (level) {

            case 1: {
                if (pageNum > 1) {
                    throw new SCException(400123, "剩余名额不足");
                }
                places = 5 - pageNum * 5;
            }
            break;
            case 2: {
                if (pageNum > 3) {
                    throw new SCException(400123, "剩余名额不足");
                }
                places = 15 - pageNum * 5;
            }
            break;
            case 3: {
                if (pageNum > 20) {
                    throw new SCException(400123, "剩余名额不足");
                }
                places = 100 - pageNum * 5;
            }
            break;
            case 4: {
                if (pageNum > 40) {
                    throw new SCException(400123, "剩余名额不足");
                }
                places = 200 - pageNum * 5;
            }
            break;
            default:
                break;
        }

        return places;
    }
}
