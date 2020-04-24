package com.gymnasium.stadium.Service.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.gymnasium.Enums.SysConstant;
import com.gymnasium.Util.*;
import com.gymnasium.Util.Enums.FinalEnum;
import com.gymnasium.Util.Enums.ResultEnum;
import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.oss.FileUtils;
import com.gymnasium.Util.vo.DateVo;
import com.gymnasium.core.page.Paging;
import com.gymnasium.file.VO.GymImagesVO;
import com.gymnasium.information.Dao.PartnerRecordDao;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.order.DAO.ExpensesRecordDao;
import com.gymnasium.order.PO.ExpensesRecordPO;
import com.gymnasium.personnel.Dao.CoachUserDao;
import com.gymnasium.personnel.Dao.UserDao;
import com.gymnasium.personnel.PO.CoachUserPO;
import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.stadium.Dao.*;
import com.gymnasium.stadium.PO.*;
import com.gymnasium.stadium.Service.GymShopService;
import com.gymnasium.stadium.VO.GymFitnessRecordVO;
import com.gymnasium.stadium.VO.GymShopVO;
import com.gymnasium.stadium.VO.GymbsVO;
import com.gymnasium.system.Dao.SysMemberDao;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 王志鹏
 * @title: GymShopServiceImpl
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/2 15:25
 */
@Service
public class GymShopServiceImpl implements GymShopService {

    @Autowired
    private GymShopDao gymShopDao;

    @Autowired
    private GymImagesDao gymImagesDao;

    @Autowired
    private GymBuildingDao gymBuildingDao;

    @Autowired
    private GymSubjectDao gymSubjectDao;

    @Autowired
    private CardTypeDao cardTypeDao;

    @Autowired
    private CoachUserDao coachUserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GymFitnessRecordDao gymFitnessRecordDao;

    @Autowired
    private SysMemberDao sysMemberDao;

    @Autowired
    private ExpensesRecordDao expensesRecordDao;

    @Autowired
    private PartnerRecordDao partnerRecordDao;

    @Autowired
    private FlowRecordUtil flowRecordUtil;


    @Override
    public Page<GymShopPO> pageGymShop(Paging page, GymShopPO gymShopPO) {
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.ASC, "gid");
        Page<GymShopPO> gymShopPage = gymShopDao.findAll(new Specification<GymShopPO>() {
            @Override
            public Predicate toPredicate(Root<GymShopPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                return queryFilterExcute(gymShopPO, root, criteriaQuery, criteriaBuilder);
            }
        }, pageable);
        return gymShopPage;
    }

    @Override
    public Page<GymShopPO> searchGymShop(Paging page, GymShopPO gymShopPO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<GymShopPO> gymShopPage = gymShopDao.findAll(new Specification<GymShopPO>() {
            @Override
            public Predicate toPredicate(Root<GymShopPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

//                if (ObjectUtils.anyNotNull(gymShopPO.getCityId())){
//
//                    list.add(criteriaBuilder.equal(root.get("cityId").as(Integer.class), gymShopPO.getCityId()));
//                }
//                if (ObjectUtils.anyNotNull(gymShopPO.getGymPhone())){
//
//                    list.add(criteriaBuilder.like(root.get("gymPhone"), "%" + gymShopPO.getGymPhone() + "%"));
//                }
                if (ObjectUtils.anyNotNull(gymShopPO.getGymName())) {

                    list.add(criteriaBuilder.like(root.get("gymName"), "%" + gymShopPO.getGymName() + "%"));
                }

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));

            }
        }, pageable);
        return gymShopPage;
    }

    @Override
    public GymShopVO queryByGymShopId(String gymShopId) {

        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymShopId);

        if (ObjectUtil.isNull(gymShopPO)){
            return null;
        }

        List<GymImagesPO> imageList = gymImagesDao.queryByGymShopIdAndStatus(gymShopId, SysConstant.STATUS_Enable_INT.getConstant());
        GymShopVO gymShopVO = new GymShopVO();
        BeanUtil.copyProperties(gymShopPO, gymShopVO, Arrays.asList("gid"));
        gymShopVO.setGymImagesPOList(imageList);

        return gymShopVO;
    }

    @Override
    public List<CardTypePO> queryCardTypePOByGymShopId(String gymShopId) {

        return cardTypeDao.queryByGymShopId(gymShopId);
    }

    @Override
    public List<CoachUserPO> queryCoachUserByGymShopId(String gymShopId) {

        return coachUserDao.queryByGymShopId(gymShopId);
    }

    @Override
    public GymbsVO queryGymShopByaAttach(String gymShopId) {
        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymShopId);

        List<GymBuildingPO> buildingPOS = gymBuildingDao.queryByGid(gymShopPO.getGid());
        List<GymSubjectPO> subjectPOS = gymSubjectDao.queryByGid(gymShopPO.getGid());

        HashMap map = new HashMap();
        map.put("GymBuildingPOList", buildingPOS);
        map.put("GymSubjectPOList", subjectPOS);
        GymbsVO gymbsVO = new GymbsVO();
        gymbsVO.setData(map);

        return gymbsVO;
    }

    @Override
    public List<GymShopPO> queryByMatching(Integer districtId) {

        List<GymShopPO> gymShopList = gymShopDao.findAll(new Specification<GymShopPO>() {

            @Override
            public Predicate toPredicate(Root<GymShopPO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (ObjectUtils.anyNotNull(districtId)) {
                    list.add(criteriaBuilder.equal(root.get("districtId").as(Integer.class), districtId));
                }

                list.add(criteriaBuilder.equal(root.get("matching").as(Integer.class), 1));

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        });

        return gymShopList;
    }

    @Override
    public Page<GymFitnessRecordPO> pageGymFitnessRecord(Paging page, GymFitnessRecordPO gymFitnessRecordPO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.ASC, "id");

        Page<GymFitnessRecordPO> poPage = gymFitnessRecordDao.findAll(new Specification<GymFitnessRecordPO>() {
            @Override
            public Predicate toPredicate(Root<GymFitnessRecordPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (gymFitnessRecordPO.getStartTime() != null) {
                    String start = DateUtil.getDateToDateString(gymFitnessRecordPO.getStartTime(), "yyyy-MM-dd") + " 00:00:00";
                    String EndTime = DateUtil.getDateToDateString(gymFitnessRecordPO.getStartTime(), "yyyy-MM-dd") + " 23:59:59";
                    list.add(criteriaBuilder.between(root.get("startTime"), start, EndTime));
                }

                if (gymFitnessRecordPO.getUserId() != null) {
                    list.add(criteriaBuilder.equal(root.get("userId"), gymFitnessRecordPO.getUserId()));
                }
                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);

        return poPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGymShop(MultipartFile files, GymShopVO gymShopVO) throws Exception {

        System.out.println(gymShopVO);

        String image = FileUtils.uploadImage(files);

        if (StrUtil.isNotBlank(image)) {
            gymShopVO.setImage(image);
        }

        String gymShopId = "GY_" + RandomUtil.getShortUuid() + DateUtil.current("yyMMdd");
        GymShopPO gymShopPO = new GymShopPO();
        BeanUtil.copyProperties(gymShopVO, gymShopPO, Arrays.asList("gymImagesPOList", "gymBuildingPOList", "gymSubjectPOList"));
        gymShopPO.setGymShopId(gymShopId);
        String qrCoderul = FinalEnum.URL_HEAD.getContent() + QrCodeUtils.url_FastFDFS(gymShopId, "", false);
        gymShopPO.setStatus(SysConstant.STATUS_Enable_INT.getConstant());
        gymShopPO.setScore(0);
        gymShopPO.setCreateTime(DateUtil.getDateToTimestamp(new Date()));
        gymShopPO.setQrCodeUrl(qrCoderul);
        gymShopPO.setUnit(3 + "");
        gymShopDao.save(gymShopPO);

        Boolean aBoolean = insertGymBuilding(gymShopVO.getBid(), gymShopPO.getGid());
        Boolean aBoolean1 = insertGymSubject(gymShopVO.getSid(), gymShopPO.getGid());

        return aBoolean && aBoolean1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGymBuilding(String gymShopId, int bid) {
        GymBuildingPO gymBuildingPO = new GymBuildingPO();
        gymBuildingPO.setBidAndGig(bid, gymShopDao.findByGymShopId(gymShopId).getGid());
        gymBuildingDao.save(gymBuildingPO);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGymSubject(String gymShopId, int sid) {

        GymSubjectPO gymSubjectPO = new GymSubjectPO();
        gymSubjectPO.setSidAndGig(sid, gymShopDao.findByGymShopId(gymShopId).getGid());
        gymSubjectDao.save(gymSubjectPO);

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGymShopByGymShopId(MultipartFile files, GymShopVO gymShopVO, Integer bid) {

        String image = FileUtils.uploadImage(files);

        if (StrUtil.isNotBlank(image)) {
            gymShopVO.setImage(image);
        }

        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymShopVO.getGymShopId());

        BeanUtil.copyProperties(gymShopVO, gymShopPO, Arrays.asList("gid", "gymShopId", "image", "buildingPO", "gymSubjectPO", "gymBuildingPOList", "gymSubjectPOList"));

        gymShopDao.save(gymShopPO);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGymShopAvatar(MultipartFile files, GymImagesVO gymImagesVO) {

        String image = FileUtils.uploadImage(files);

        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymImagesVO.getGymShopId());
        gymShopPO.setImage(image);

        return ObjectUtils.anyNotNull(gymShopDao.save(gymShopPO));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addStratGym(GymFitnessRecordPO gymFitnessRecordPO) throws SCException {
        //设置当前健身时间
        //TODO 开始健身
        UserPO userPO = userDao.findByUserId(gymFitnessRecordPO.getUserId());

        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymFitnessRecordPO.getGymShopId());

        double distance = TangChaoUtils.getDistance(gymShopPO.getLongitude(), gymShopPO.getLatitude(), gymFitnessRecordPO.getLnt(), gymFitnessRecordPO.getLat());

        if (distance > 0.5) {
            throw new SCException(ResultEnum.DISTANCE_ERROR); // 超出范围
        }

        if (userPO == null) {
            throw new SCException(ResultEnum.USER_REPEAT); //用户不存在
        }

        if (userPO.getType() == 1) {
            throw new SCException(ResultEnum.GYM_TOO_ERROR); //状态不能为已开始健身
        }

        if (userPO.getType() == 2) {
            throw new SCException(ResultEnum.GYM_hexiao_ERROR); //状态不能为等待核销
        }

        //判断人数
        if (gymFitnessRecordPO.getPeopleNum() > sysMemberDao.queryByLevel(userPO.getLevel()).getPeopleNumber()) {
            throw new SCException(ResultEnum.LEVEL_ERROR);
        }

        //判断能量值不能少于一次健身的
        if (gymShopPO.getGymEnergy() * gymFitnessRecordPO.getPeopleNum() > userPO.getEnergy()) {
            throw new SCException(ResultEnum.ENERGY_ERROR);
        }

        String serialId = RandomUtil.getShortUuid() + DateUtil.current();

        gymFitnessRecordPO.setSerialId(serialId);
        gymFitnessRecordPO.setStartTime(DateUtil.getDateToTimestamp(new Date()));
        gymFitnessRecordPO.setUserType(SysConstant.TYPE_GYMUSER_RUNING.getConstant());
//        gymFitnessRecordDao.save(gymFitnessRecordPO);

        userPO.setType(SysConstant.TYPE_GYMUSER_RUNING.getConstant());//开始健身
        userPO.setSerialId(serialId);
        userDao.save(userPO);


        //------------------------------搭伙处理---------------------

        Timestamp nowTime = DateUtil.getDateToTimestamp(new Date());
        List<PartnerRecordPO> poList = partnerRecordDao.queryByPromiseTime(DateUtil.getDateToDateString(nowTime, "yyyy-MM-dd"));
        int setPartnerType = 0;
        for (PartnerRecordPO partnerRecordPO : poList) {
            UserPO userPO1 = userDao.findByUserId(userPO.getUserId());
            String userId = userPO1.getUserId();
            String auserId = partnerRecordPO.getAuserId();
            String buserId = partnerRecordPO.getBuserId();
            String payType = partnerRecordPO.getPayType();

            if (userId.equals(auserId)) {
                //String paytype, String
                //1到场,2未到场
                setPartnerType = 1;
                partnerRecordPO.setAtype(1);
                partnerRecordDao.save(partnerRecordPO);

                enegryExecute(userPO1, payType, "A");
            } else if (userId.equals(buserId)) {
                setPartnerType = 1;
                partnerRecordPO.setBtype(1);
                partnerRecordDao.save(partnerRecordPO);
                enegryExecute(userPO1, payType, "B");
            }
        }
        gymFitnessRecordPO.setPartnerType(setPartnerType);
        gymFitnessRecordDao.save(gymFitnessRecordPO);

        return true;
    }

    @Override
    public Result findGymImages(Paging page, String gymShopId, Integer type) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<GymImagesPO> gymShopDaoAll = gymImagesDao.findAll(new Specification<GymImagesPO>() {

            @Override
            public Predicate toPredicate(Root<GymImagesPO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();

                if (ObjectUtils.anyNotNull(type)) {
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
                }

                list.add(criteriaBuilder.equal(root.get("gymShopId").as(String.class), gymShopId));

                Predicate[] predicates = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(predicates));
            }
        }, pageable);

        return ResultUtil.success(gymShopDaoAll);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GymFitnessRecordVO addEndGym(String uesrId, String code) throws SCException {

        // 解密二维码
        String verify = JwtUtil.verify(code);

        String type = verify.substring(verify.indexOf("type=") + 5, verify.lastIndexOf("&"));

        if ("1".equals(type)) {
            throw new SCException(400602, "不是结束健身的二维码");
        }

        code = verify.substring(verify.lastIndexOf("failure_time=") + 13);

        long time = Long.valueOf(code);
        long l = System.currentTimeMillis() / 1000;
        if (l - time > 600L) {
            throw new SCException(ResultEnum.QCODE_OUT_TIME); //二维码超时
        }
        UserPO userPO = userDao.findByUserId(uesrId);
        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER); //用户不能为空
        }
        if (userPO.getType() == SysConstant.TYPE_GYMUSER_NO_START.getConstant()) {
            throw new SCException(ResultEnum.GYM_USER_NO_BEING);//用户未在健身,不能停止
        }
        if (userPO.getType() == SysConstant.TYPE_GYMUSER_CHECK.getConstant()) {
            throw new SCException(112312, "核销状态不能重复停止!");
        }
        GymFitnessRecordPO gymFitnessRecordPO = gymFitnessRecordDao.queryBySerialId(userPO.getSerialId());
        Timestamp endTime = DateUtil.getDateToTimestamp(new Date());
        gymFitnessRecordPO.setEndTime(endTime);
        gymFitnessRecordPO.setUserType(SysConstant.TYPE_GYMUSER_CHECK.getConstant());//更新记录为等待核销
        gymFitnessRecordDao.save(gymFitnessRecordPO);

        userPO.setType(SysConstant.TYPE_GYMUSER_CHECK.getConstant());//等待核销2
        userDao.save(userPO);

        GymFitnessRecordVO gymFitnessRecordVO = new GymFitnessRecordVO();
        BeanUtil.copyProperties(gymFitnessRecordPO, gymFitnessRecordVO);

        Timestamp startTime = gymFitnessRecordPO.getStartTime();
        gymFitnessRecordVO.setSecond((int) ((endTime.getTime() - startTime.getTime()) / 1000));
        gymFitnessRecordVO.setType(SysConstant.TYPE_GYMUSER_CHECK.getConstant());//等待核销
        return gymFitnessRecordVO;
    }

    @Override
    public GymFitnessRecordVO addUserEndGym(String userId, String lat, String lnt) throws SCException {

        UserPO userPO = userDao.findByUserId(userId);


        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER);
        }

        //用户未在核销状态
        if (userPO.getType() != SysConstant.TYPE_GYMUSER_CHECK.getConstant()) {
            throw new SCException(111, "不是核销状态!");
        }

        GymFitnessRecordPO gymFitnessRecordPO = gymFitnessRecordDao.queryBySerialId(userPO.getSerialId());
        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymFitnessRecordPO.getGymShopId());

        double distance = TangChaoUtils.getDistance(gymShopPO.getLongitude(), gymShopPO.getLatitude(), gymFitnessRecordPO.getLnt(), gymFitnessRecordPO.getLat());

        if (distance < 0.5) {
            throw new SCException(ResultEnum.DISTANCE_ERROR); // 超出范围
        }

        Timestamp twoEndTime = DateUtil.getDateToTimestamp(new Date());//核销时间
        Timestamp startTime = gymFitnessRecordPO.getStartTime();
        Timestamp endTime = gymFitnessRecordPO.getEndTime();//扫码结束时间
        if (twoEndTime.getTime() / 1000 - endTime.getTime() / 1000 > 1200) {
            throw new SCException(ResultEnum.CODE_TIME_OUT);
        }
        if (endTime == null) {
            throw new SCException(ResultEnum.GYM_NULL_ENDTIME);//请先扫码进行结束
        }
        gymFitnessRecordPO.setTowEndTime(twoEndTime);
        gymFitnessRecordPO.setType(SysConstant.TYPE_IS_NORMAL.getConstant());//正常结束


        double energy = jishiPrice(endTime, startTime, gymShopPO.getGymEnergy());
        //总消耗能量值 = 消耗能量值*人数
        double total = energy * gymFitnessRecordPO.getPeopleNum();
        //用户剩余能量值 = 用户能量值 - (消耗能量值*人数)total
        double userEnergy = userPO.getEnergy() - total;
        gymFitnessRecordPO.setEnergy(total);
        gymFitnessRecordDao.save(gymFitnessRecordPO);


        userPO.setEnergy(userEnergy);
        userPO.setType(SysConstant.TYPE_GYMUSER_NO_START.getConstant());//跟新状态为未健身
        userDao.save(userPO);

        GymFitnessRecordVO gymFitnessRecordVO = new GymFitnessRecordVO();

        BeanUtil.copyProperties(gymFitnessRecordPO, gymFitnessRecordVO);

        gymFitnessRecordVO.setSecond((int) ((endTime.getTime() - startTime.getTime()) / 1000));
        gymFitnessRecordVO.setType(SysConstant.TYPE_GYMUSER_NO_START.getConstant());//结束健身
        gymFitnessRecordVO.setEnergy(total); //消耗能量值

        flowRecordUtil.addExpensesRecord(userId, total, userEnergy, 0, 1, 3, "健身扣除",
                "健身记录:正常核销", gymFitnessRecordVO.getGymShopId());

        return gymFitnessRecordVO;
    }

    //核销健身接口正常核销调用
    @Override
    public GymFitnessRecordVO addTowEndGym(String userId) throws SCException {

        UserPO userPO = userDao.findByUserId(userId);

        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER);
        }

        //用户未在核销状态
        if (userPO.getType() != SysConstant.TYPE_GYMUSER_CHECK.getConstant()) {
            throw new SCException(111, "不是核销状态!");
        }

        GymFitnessRecordPO gymFitnessRecordPO = gymFitnessRecordDao.queryBySerialId(userPO.getSerialId());
        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymFitnessRecordPO.getGymShopId());

        Timestamp twoEndTime = DateUtil.getDateToTimestamp(new Date());//核销时间
        Timestamp startTime = gymFitnessRecordPO.getStartTime();
        Timestamp endTime = gymFitnessRecordPO.getEndTime();//扫码结束时间

        if (endTime == null) {
            throw new SCException(ResultEnum.GYM_NULL_ENDTIME);//请先扫码进行结束
        }
        gymFitnessRecordPO.setTowEndTime(twoEndTime);
        gymFitnessRecordPO.setType(SysConstant.TYPE_IS_NORMAL.getConstant());//正常结束


        double energy = jishiPrice(endTime, startTime, gymShopPO.getGymEnergy());
        //总消耗能量值 = 消耗能量值*人数
        double total = energy * gymFitnessRecordPO.getPeopleNum();
        //用户剩余能量值 = 用户能量值 - (消耗能量值*人数)total
        double userEnergy = userPO.getEnergy() - total;
        gymFitnessRecordPO.setEnergy(total);
        gymFitnessRecordDao.save(gymFitnessRecordPO);


        userPO.setEnergy(userEnergy);
        userPO.setType(SysConstant.TYPE_GYMUSER_NO_START.getConstant());//跟新状态为未健身
        userDao.save(userPO);

        GymFitnessRecordVO gymFitnessRecordVO = new GymFitnessRecordVO();

        BeanUtil.copyProperties(gymFitnessRecordPO, gymFitnessRecordVO);

        gymFitnessRecordVO.setSecond((int) ((endTime.getTime() - startTime.getTime()) / 1000));
        gymFitnessRecordVO.setType(SysConstant.TYPE_GYMUSER_NO_START.getConstant());//结束健身
        gymFitnessRecordVO.setEnergy(total); //消耗能量值

        flowRecordUtil.addExpensesRecord(userId, total, userEnergy, 0, 1, 3, "健身扣除",
                "健身记录:正常核销", gymFitnessRecordVO.getGymShopId());

        return gymFitnessRecordVO;
    }

    //异常核销健身
    @Override
    public GymFitnessRecordVO addUnusualTowEndGym(String userId) throws SCException {
        UserPO userPO = userDao.findByUserId(userId);

        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER);
        }

        //未开始健身的不能进行异常核销
        if (userPO.getType() == SysConstant.TYPE_GYMUSER_NO_START.getConstant()) {
            throw new SCException(5478, "未开始健身,不能进行异常核销!");
        }

        GymFitnessRecordPO gymFitnessRecordPO = gymFitnessRecordDao.queryBySerialId(userPO.getSerialId());
        GymShopPO gymShopPO = gymShopDao.findByGymShopId(gymFitnessRecordPO.getGymShopId());

        Timestamp twoEndTime = DateUtil.getDateToTimestamp(new Date());//核销时间
        Timestamp startTime = gymFitnessRecordPO.getStartTime();
//        Timestamp endTime = gymFitnessRecordPO.getEndTime();//扫码结束时间

        gymFitnessRecordPO.setTowEndTime(twoEndTime);
        gymFitnessRecordPO.setUserType(SysConstant.TYPE_GYMUSER_NO_START.getConstant());//异常结束 更新状态为未健身
        gymFitnessRecordPO.setType(SysConstant.TYPE_IS_NORMAL.getConstant());//1正常核销
        gymFitnessRecordDao.save(gymFitnessRecordPO);

        double energy = jishiPrice(twoEndTime, startTime, gymShopPO.getGymEnergy());
        //总消耗能量值 = 消耗能量值*人数
        double total = energy * gymFitnessRecordPO.getPeopleNum();
        //用户剩余能量值 = 用户能量值 - (消耗能量值*人数)total
        double userEnergy = userPO.getEnergy() - total;
        userPO.setEnergy(userEnergy);
        userPO.setType(SysConstant.TYPE_GYMUSER_NO_START.getConstant());//跟新状态为未健身

        userDao.save(userPO);

        GymFitnessRecordVO gymFitnessRecordVO = new GymFitnessRecordVO();

        gymFitnessRecordVO.setSecond((int) ((twoEndTime.getTime() - startTime.getTime()) / 1000));
        gymFitnessRecordVO.setSerialId(userPO.getSerialId());//健身流水号
        gymFitnessRecordVO.setType(SysConstant.TYPE_GYMUSER_NO_START.getConstant());//结束健身
        gymFitnessRecordVO.setEnergy(total);
        gymFitnessRecordVO.setTowEndTime(twoEndTime);

        // 健身
        flowRecordUtil.addExpensesRecord(userId, total, userEnergy, 0, 1, 3, "健身扣除",
                "健身记录:异常核销", gymFitnessRecordVO.getGymShopId());

        return gymFitnessRecordVO;
    }

    @Override
    public GymFitnessRecordVO queryGymFitnessRecordPOBySerialId(String userId) throws SCException {


        UserPO userPO = userDao.findByUserId(userId);

        if (userPO == null) {
            throw new SCException(ResultEnum.NULL_USER);
        }

        if (userPO.getType() == 0) {
            throw new SCException(ResultEnum.GYM_USER_NOBEING);
        }

        GymFitnessRecordPO gymFitnessRecordPO = gymFitnessRecordDao.queryBySerialId(userPO.getSerialId());

        Timestamp startTime = gymFitnessRecordPO.getStartTime();
        Timestamp endTime = gymFitnessRecordPO.getEndTime();
        GymFitnessRecordVO gymFitnessRecordVO = new GymFitnessRecordVO();
        String temp = "健身已完结:用时 ";
        if (endTime == null) {
            endTime = DateUtil.getDateToTimestamp(new Date());
            temp = "计时中:用时 ";
        }
        BeanUtil.copyProperties(gymFitnessRecordPO, gymFitnessRecordVO);
        List<Long> list = getDatePoor(endTime, startTime);

        gymFitnessRecordVO.setRemake(temp + list.get(0) + "天" + list.get(1) + "小时" + list.get(2) + "分钟" + list.get(3) + "秒");
        gymFitnessRecordVO.setSecond((int) ((endTime.getTime() - startTime.getTime()) / 1000));
        gymFitnessRecordVO.setType(userPO.getType());
        return gymFitnessRecordVO;
    }

    //管理使用根据健身房查询当前需要核销的用户
    @Override
    public List<GymFitnessRecordPO> queryGymFitnessRecordByGymShopId(Paging page, GymFitnessRecordVO gymFitnessRecordVO) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.Direction.DESC, "id");

        Page<GymFitnessRecordPO> gymFitnessRecordPOS = gymFitnessRecordDao.findAll(new Specification<GymFitnessRecordPO>() {
            @Override
            public Predicate toPredicate(Root<GymFitnessRecordPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<Predicate>();
                if (GeneralUtils.notEmpty(gymFitnessRecordVO.getGymShopId())) {
                    list.add(criteriaBuilder.equal(root.get("gymShopId").as(String.class), gymFitnessRecordVO.getGymShopId()));
                }

                if (GeneralUtils.notEmpty(gymFitnessRecordVO.getType())) {
                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), gymFitnessRecordVO.getType()));
                }

                if (GeneralUtils.notEmpty(gymFitnessRecordVO.getUserId())) {
                    list.add(criteriaBuilder.equal(root.get("userId").as(String.class), gymFitnessRecordVO.getUserId()));
                }
//                if (GeneralUtils.notEmpty(gymFitnessRecordVO.getPhone())) {
//                    UserPO userPO = userDao.queryByUserPhone(gymFitnessRecordVO.getPhone());
//                    list.add(criteriaBuilder.equal(root.get("userId").as(String.class), userPO.getUserId()));
//                }

                if (GeneralUtils.notEmpty(gymFitnessRecordVO.getStartTime()) && GeneralUtils.notEmpty(gymFitnessRecordVO.getEndTime())) {
                    list.add(criteriaBuilder.between(root.get("startTime"), gymFitnessRecordVO.getStartTime(), gymFitnessRecordVO.getEndTime()));
                }

//                list.add(criteriaBuilder.equal(root.get("userType").as(Integer.class), gymFitnessRecordVO.getUserType()));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);


        return gymFitnessRecordPOS.getContent();
    }

    @Override
    public Boolean addMoreGymBuilding(String bid, Integer gid) {

        return insertGymBuilding(bid, gid);
    }

    @Override
    public Boolean addMoreGymSubject(String sid, Integer gid) {

        return insertGymSubject(sid, gid);
    }

    @Override
    public Boolean delGymBuilding(Integer id) {

        GymBuildingPO gymBuildingPO = gymBuildingDao.getOne(id);

        if (ObjectUtil.isNull(id)) {
            throw new SCException(478, "建筑id不存在");
        }

        gymBuildingDao.deleteById(id);

        return true;
    }

    @Override
    public Boolean delGymSubject(Integer id) {

        GymSubjectPO gymSubjectPO = gymSubjectDao.getOne(id);

        if (ObjectUtil.isNull(id)) {
            throw new SCException(478, "科目id不存在");
        }

        gymSubjectDao.deleteById(id);

        return true;
    }

    @Override
    public Boolean updateGymBuilding(GymBuildingPO gymBuildingPO) {

        GymBuildingPO gymBuilding = gymBuildingDao.getOne(gymBuildingPO.getId());
        if (ObjectUtil.isNull(gymBuilding)) {
            throw new SCException(478, "建筑id不存在");
        }

        BeanUtil.copyProperties(gymBuildingPO, gymBuilding);

        return ObjectUtil.isNotNull(gymBuildingDao.save(gymBuilding));
    }

    @Override
    public Boolean updateGymSubject(GymSubjectPO gymSubjectPO) {

        GymSubjectPO gymSubject = gymSubjectDao.getOne(gymSubjectPO.getId());

        if (ObjectUtil.isNull(gymSubject)) {
            throw new SCException(478, "科目id不存在");
        }

        BeanUtil.copyProperties(gymSubjectPO, gymSubject);

        return ObjectUtil.isNotNull(gymSubjectDao.save(gymSubject));
    }

    @Override
    public Boolean delGymShop(Integer id) {

        if (!gymShopDao.existsById(id)) {
            throw new SCException(416117, "健身房不存在");
        }

        gymShopDao.deleteById(id);

        return true;
    }

    @Override
    public Page<GymFitnessRecordPO> queryGymFitnessRecord(Paging page, String userId, String gymShopId, DateVo dateVo) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());

        Page<GymFitnessRecordPO> fitnessRecordPOPage = gymFitnessRecordDao.findAll((Specification<GymFitnessRecordPO>)
                (root, query, criteriaBuilder) -> {

                    List<Predicate> list = new ArrayList<>();

                    if (StrUtil.isNotBlank(userId)) {

                        list.add(criteriaBuilder.equal(root.get("userId").as(String.class), userId));
                    }

                    if (StrUtil.isNotBlank(gymShopId)) {

                        list.add(criteriaBuilder.equal(root.get("gymShopId").as(String.class), gymShopId));
                    }

                    String formatDate = dateVo.formatDate();

                    if (StrUtil.isNotBlank(formatDate)) {

                        list.add(criteriaBuilder.like(root.get("startTime").as(String.class), formatDate + "%"));
                    }

                    Predicate[] p = new Predicate[list.size()];

                    return criteriaBuilder.and(list.toArray(p));
                }, pageable);

        return fitnessRecordPOPage;
    }

    public Predicate queryFilterExcute(GymShopPO gymShopPO, Root<GymShopPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> list = new ArrayList<Predicate>();

        Specification querySpecifi = new Specification<GymBuildingPO>() {
            @Override
            public Predicate toPredicate(Root<GymBuildingPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.greaterThan(root.get("bid"), 1));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };


        if (GeneralUtils.notEmpty(gymShopPO.getGymShopId())) {
            list.add(criteriaBuilder.equal(root.get("gymShopId").as(String.class), gymShopPO.getGymShopId()));
        }

        if (GeneralUtils.notEmpty(gymShopPO.getGymPhone())) {
            list.add(criteriaBuilder.equal(root.get("gymPhone").as(String.class), gymShopPO.getGymPhone()));
        }

        if (GeneralUtils.notEmpty(gymShopPO.getGymName())) {
            list.add(criteriaBuilder.equal(root.get("gymName").as(String.class), gymShopPO.getGymName()));
        }

        if (GeneralUtils.notEmpty(gymShopPO.getLabel())) {
            list.add(criteriaBuilder.equal(root.get("label").as(String.class), gymShopPO.getLabel()));
        }

        if (gymShopPO.getCityId() != null) {
            list.add(criteriaBuilder.equal(root.get("cityId").as(Integer.class), gymShopPO.getCityId()));
        }

        if (ObjectUtils.anyNotNull(gymShopPO.getDistrictId())) {
            list.add(criteriaBuilder.equal(root.get("districtId").as(Integer.class), gymShopPO.getDistrictId()));
        }

        if (GeneralUtils.notEmpty(gymShopPO.getGymBuildingPOS())) {
            Join<GymShopPO, GymBuildingPO> join = root.join("gymBuildingPOList", JoinType.INNER);
//            list.add(criteriaBuilder.equal(join.get("bid"), gymShopPO.getBuildingPO().getBid()));

            Path<Object> path = join.get("bid");
            CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
            for (GymBuildingPO buildingPO : gymShopPO.getGymBuildingPOS()) {
                in.value(buildingPO.getBid());
            }
            list.add(criteriaBuilder.and(in));
        }

//        if (gymShopPO.getLongitude() != 0 && gymShopPO.getLatitude() != 0) {
//            double lon = gymShopPO.getLongitude(), lat = gymShopPO.getLatitude(); // 千米
//            int radius = 10;
//            SpatialContext geo = SpatialContext.GEO;
//            Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(geo.makePoint(lon, lat), radius * DistanceUtils.KM_TO_DEG, geo, null);
//            System.out.println(rectangle.getMinX() + "-" + rectangle.getMaxX());// 经度范围
//            System.out.println(rectangle.getMinY() + "-" + rectangle.getMaxY());// 纬度范围
//            list.add(criteriaBuilder.between(root.get("longitude"), lon, lat));
//            list.add(criteriaBuilder.between(root.get("latitude"), lon, lat));
//        }

        if (GeneralUtils.notEmpty(gymShopPO.getGymSubjectPOS())) {
            Join<GymShopPO, GymSubjectPO> join = root.join("gymSubjectPOList", JoinType.INNER);

            Path<Object> path = join.get("sid");
            CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
            for (GymSubjectPO subjectPO : gymShopPO.getGymSubjectPOS()) {
                in.value(subjectPO.getSid());
            }

            list.add(criteriaBuilder.and(in));
        }

        criteriaQuery.groupBy(root.get("gid"));
        Predicate[] p = new Predicate[list.size()];

        return criteriaBuilder.and(list.toArray(p));
    }

    private static double jishiPrice(Timestamp endDate, Timestamp nowDate, double energy) {

        double price = 0.00;

        int sec = (int) (endDate.getTime() - nowDate.getTime()) / 1000;

        if (sec % 3600 == 0) {
            price = (sec / 3600) * energy;
        } else {
            price = (sec / 3600) * energy + energy;
        }

        return price;
    }


    public static List<Long> getDatePoor(Timestamp endDate, Timestamp nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;

        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();

        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;

        List<Long> list = new ArrayList();

        list.add(day);
        list.add(hour);
        list.add(min);
        list.add(sec);
        System.out.println(day + "天" + hour + "小时" + min + "分钟" + sec + "秒");
        return list;

    }

    public void enegryExecute(UserPO userPO, String paytype, String usertype) {

        if (paytype.equals("AA")) {
            userPO.setEnergy(userPO.getEnergy() + 20);
            userDao.save(userPO);
            ExpensesRecordPO expensesRecordPO = ExpensesRecordPO.createRecord(userPO.getUserId(), 20, userPO.getEnergy(), ExpensesRecordPO.Record_type_Return, ExpensesRecordPO.Record_type_revenue, "搭伙保证金归还", "AA开始健身归还");
            expensesRecordPO.setItemType(5);
            expensesRecordDao.save(expensesRecordPO);
        } else if (paytype.equals("A")) {

            String str = "搭伙保证金归还";

            if (usertype.equals("B")) {
                str = "搭伙保证金奖励";
            }

            userDao.save(userPO);
            ExpensesRecordPO expensesRecordPO = ExpensesRecordPO.createRecord(userPO.getUserId(), 20, userPO.getEnergy(), ExpensesRecordPO.Record_type_Return, ExpensesRecordPO.Record_type_revenue, str, "A全付开始健身归还");
            expensesRecordPO.setItemType(5);
            expensesRecordDao.save(expensesRecordPO);

        } else if (paytype.equals("B")) {

            String str = "搭伙保证金归还";

            if (usertype.equals("A")) {
                str = "搭伙保证金奖励";
            }

            userDao.save(userPO);
            ExpensesRecordPO expensesRecordPO = ExpensesRecordPO.createRecord(userPO.getUserId(), 20, userPO.getEnergy(), ExpensesRecordPO.Record_type_Return, ExpensesRecordPO.Record_type_revenue, str, "B全付开始健身归还");
            expensesRecordPO.setItemType(5);
            expensesRecordDao.save(expensesRecordPO);
        }

    }

    private Boolean insertGymBuilding(String bid, Integer gid) {

        List list = new ArrayList<>();

        if (StrUtil.isNotBlank(bid)) {
            String[] split = StrUtil.split(bid, ",");

            for (String string : split) {
                // todo
                GymBuildingPO gymBuildingPO = new GymBuildingPO();
                gymBuildingPO.setBid(Integer.parseInt(string));
                gymBuildingPO.setGid(gid);

                list.add(gymBuildingPO);
            }
            List list1 = gymBuildingDao.saveAll(list);

            return ObjectUtil.isNotEmpty(list1);
        }
        return false;
    }

    private Boolean insertGymSubject(String sid, Integer gid) {

        if (StrUtil.isNotBlank(sid)) {
            String[] split = StrUtil.split(sid, ",");

            List list = new ArrayList<>();
            for (String string : split) {

                GymSubjectPO gymSubjectPO = new GymSubjectPO();
                gymSubjectPO.setSid(Integer.parseInt(string));
                gymSubjectPO.setGid(gid);

                list.add(gymSubjectPO);
            }

            List list1 = gymSubjectDao.saveAll(list);

            return ObjectUtil.isNotEmpty(list1);
        }
        return false;
    }
}