package com.gymnasium.store.service.impl;

import com.gymnasium.core.page.Paging;
import com.gymnasium.information.Dao.AttentionDao;
import com.gymnasium.information.Dao.GfriendDao;
import com.gymnasium.information.Dao.PartnerRecordDao;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.order.DAO.ActivationCodeDao;
import com.gymnasium.order.DAO.ExpensesRecordDao;
import com.gymnasium.stadium.Dao.GymShopDao;
import com.gymnasium.stadium.PO.GymShopPO;
import com.gymnasium.store.Dao.*;
import com.gymnasium.store.PO.ProductPO;
import com.gymnasium.store.VO.AgentRecommendVO;
import com.gymnasium.store.service.ChartService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/5 16:33
 * @Description:
 */
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private CommissionDao commissionDao;

    @Autowired
    private CashFlowDao cashFlowDao;

    @Autowired
    private GymShopDao gymShopDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private PartnerRecordDao partnerRecordDao;

    @Autowired
    private ExpensesRecordDao expensesRecordDao;

    @Autowired
    private ActivationCodeDao activationCodeDao;

    @Autowired
    private GfriendDao gfriendDao;

    @Autowired
    private AttentionDao attentionDao;

    @Autowired
    private ServiceCommentDao serviceCommentDao;

    @Override
    public List<AgentRecommendVO> queryAgentChart(Integer userId, String  year, String month, Integer type) {

        List<AgentRecommendVO> chartList = new ArrayList<>();

        List<String> timeList = new ArrayList<>();

        switch (type){
            case 1:{
                timeList = getTimeList(year,month,1);

            }
                break;
            case 2:{

            }
                break;
            case 3:{
                timeList = getTimeList(year,month,type);
            }
                break;
            case 4:{

                int teamSize = 0;
                BigDecimal totalCommission = new BigDecimal(0);
                int recommendNumber = 0;

                for (int i = 1; i<= 12; i++){

                    String time = "";

                    if (i<10){
                        time = year + "-0" + i;
                    }else {
                        time = year + "-" + i;
                        }

                    System.err.println("time" + time);

                    // 团队人数
                    int teamSize1 = commissionDao.countTeamSize(0, userId, userId, time);

                    // 代理团队佣金
                    BigDecimal agentCommission1 = commissionDao.countAgentCommission(userId, 0, time);

                    // 客户团队佣金
                    BigDecimal clientCommission1 = commissionDao.countClientCommission(userId, 0, time);

                    // 总推荐数
                    int recommendNumber1 = commissionDao.countBySuperior(userId,time);

                    teamSize  = teamSize + teamSize1;

                    if (ObjectUtils.anyNotNull(clientCommission1)){
                        totalCommission = clientCommission1.add(agentCommission1).add(totalCommission);
                    } else if (ObjectUtils.anyNotNull(agentCommission1)){
                        totalCommission = agentCommission1.add(totalCommission);
                    }

                    recommendNumber = recommendNumber + recommendNumber1;

                    if (i % 3 == 0){
                        System.err.println("i" + i);
                        AgentRecommendVO agentRecommendVO = new AgentRecommendVO();
                        agentRecommendVO.setRecommendNumber(recommendNumber);
                        agentRecommendVO.setTotalRevenue(totalCommission);
                        agentRecommendVO.setTeamSize(teamSize);

                        chartList.add(agentRecommendVO);

                        teamSize = 0;
                        totalCommission = new BigDecimal(0);
                        recommendNumber = 0;
                    }

                }
                return chartList;

            }
            case 5:{
                timeList = getTimeList(year,month,type);
            }
                break;
            default:
                break;
        }

        System.err.println(timeList);

        for (String time :timeList){

            AgentRecommendVO agentRecommendVO = new AgentRecommendVO();

            // 团队人数
            int teamSize = commissionDao.countTeamSize(0, userId, userId, time);

            // 客户团队佣金
            BigDecimal clientCommission = commissionDao.countClientCommission(userId, 0, time);

            // 代理团队佣金
            BigDecimal agentCommission = commissionDao.countAgentCommission(userId, 0, time);
            // 总推荐数
            int recommendNumber = commissionDao.countBySuperior(userId,time);

            Map<String,Object> map = new HashMap<>(16);

            map.put("teamSize",teamSize);

            if (ObjectUtils.anyNotNull(clientCommission)){
                map.put("totalCommission",clientCommission.add(agentCommission));
                agentRecommendVO.setTotalRevenue(clientCommission.add(agentCommission));
            }else if (ObjectUtils.anyNotNull(agentCommission)){
                map.put("totalCommission",agentCommission);
            }else {
                map.put("totalCommission",new BigDecimal(0));
            }

            map.put("date", time);
            map.put("recommendNumber", recommendNumber);

            chartList.add(agentRecommendVO);
        }

        return chartList;
    }

    @Override
    public List productNumberChart(Integer productTypeId, Integer status, String year, String month,
                                        Integer type) {

        List productNumberList = new ArrayList<>();

        List<String> timeList = new ArrayList<>();

        switch (type) {
            case 1: {

                timeList = getTimeList(year,month,1);

            }
                break;
            case 2: {

            }
                break;
            case 3: {
                timeList = getTimeList(year,month,3);
            }
                break;

            case 4: {

                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    Map<String, Object> maps = new HashMap<>(16);

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Integer productNum = productDao.quarterProductNum(productTypeId, status, startTime, endTime);


                    maps.put("date", count);
                    maps.put("productNum", productNum);
                    productNumberList.add(maps);

                    count ++;

                }
            }
                return productNumberList;

            case 5: {
                timeList = getTimeList(year,month,5);
            }
                break;

            default:
                break;
        }

        for (String times:timeList) {

            List<ProductPO> productList = productDao.findAll(new Specification<ProductPO>() {

                @Override
                public Predicate toPredicate(Root<ProductPO> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();

                    if (ObjectUtils.anyNotNull(productTypeId)) {
                        list.add(criteriaBuilder.equal(root.get("productTypeId").as(Integer.class), productTypeId));
                    }

                    if (ObjectUtils.anyNotNull(status)) {
                        list.add(criteriaBuilder.equal(root.get("putawayStatus").as(Integer.class), status));
                    }

                    list.add(criteriaBuilder.like(root.get("createTime").as(String.class), times + "%"));

                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
            });

            Map<String,Object> map = new HashMap<>(16);
            map.put("date", times);
            map.put("productNum", productList.size());
            productNumberList.add(map);

        }
        return  productNumberList;
    }

    @Override
    public List queryAgentNumber(String region, String  year, String  month, Integer type) {

        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type) {
            case 1: {

                timeList = getTimeList(year,month,1);

            }
            break;
            case 2: {

            }
            break;
            case 3: {
                timeList = getTimeList(year,month,3);
            }
            break;

            case 4: {

                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    Map<String,Object> maps = new HashMap<>(16);

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Integer oneAgent = agentDao.quarterAgentNumber(1,region, 1, startTime, endTime);

                    Integer towAgent = agentDao.quarterAgentNumber(1,region, 2, startTime, endTime);


                    maps.put("date", count);

                    maps.put("oneAgentNum", oneAgent);
                    maps.put("towAgentNum", towAgent);

                    count ++;

                    list.add(maps);
                }
            }
            return list;

            case 5: {
                timeList = getTimeList(year,month,5);
            }
            break;

            default:
                break;
        }

        for (String time:timeList) {

            Map<String, Object> map = new HashMap<>(16);

            //一级代理人数
            Integer oneAgent = agentDao.countAgentNumber(1,region,1,time);

            //二级代理人数
            Integer towAgent = agentDao.countAgentNumber(1,region,2,time);

            map.put("date", time);

            map.put("oneAgentNum", oneAgent);
            map.put("towAgentNum", towAgent);

            list.add(map);
        }
        return list;
    }

    @Override
    public List queryBoardRecord(String promiseTime, String year, String month, Integer type) {

        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type) {
            case 1: {
                timeList = getTimeList(year,month,1);

            }
                break;
            case 2: {



            }
                break;
            case 3: {
                timeList = getTimeList(year,month,3);
            }
                break;

            case 4: {

                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Integer successfulNumber = partnerRecordDao.quarterSuccessfulNumber(promiseTime, 1, startTime, endTime);

                    Map<String,Object> maps = new HashMap<>(16);

                    maps.put("date", count);

                    maps.put("partnerRecordNum", successfulNumber);

                    count ++;

                    list.add(maps);
                }
            }
            return list;

            case 5: {
                timeList = getTimeList(year,month,5);
            }
                break;

            default:
                break;
        }

        for (String times:timeList) {

            List<PartnerRecordPO> partnerRecordList = partnerRecordDao.findAll(new Specification<PartnerRecordPO>() {

                @Override
                public Predicate toPredicate(Root<PartnerRecordPO> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {

                    List<Predicate> list = new ArrayList<Predicate>();

                    list.add(criteriaBuilder.like(root.get("createTime").as(String.class), times + "%"));

                    if (ObjectUtils.anyNotNull(promiseTime)) {
                        list.add(criteriaBuilder.equal(root.get("promiseTime"), promiseTime));
                    }

                    list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), 1));

                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));
                }
            });

            Map<String,Object> map = new HashMap<>(16);

            map.put("date", times);
            map.put("successfulNumber", partnerRecordList.size());

            list.add(map);
        }

        return list;
    }

    @Override
    public List queryMemberConsumption(String userId, String year, String month, Integer type) {

        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type) {
            case 1: {
                timeList = getTimeList(year, month, 1);

            }
            break;
            case 2: {


            }
                break;
            case 3: {
                timeList = getTimeList(year, month, 3);
            }
                break;

            case 4: {

                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Double cumulativeEnergy = expensesRecordDao.statisticalEnergy(userId, 1, startTime, endTime);

                    Double freezingEnergy = expensesRecordDao.statisticalEnergy(userId, 3, startTime, endTime);

                    Map<String,Object> maps = new HashMap<>(16);

                    maps.put("date", count);

                    maps.put("totalEnergy", cumulativeEnergy);

                    maps.put("freezingEnergy", freezingEnergy);

                    count ++;

                    list.add(maps);
                }
            }
                return list;

            case 5: {
                timeList = getTimeList(year, month, 5);
            }
                break;
            default:
                break;
        }


        for (String time : timeList){

            //统计用户消费的总能量值

            Double cumulativeEnergy = expensesRecordDao.countEnergyByLike(userId, 1, time);

            // 统计用户冻结的总能量值
            Double freezingEnergy = expensesRecordDao.countEnergyByLike(userId, 3, time);

            Map<String, Object> map = new HashMap<>(16);

            map.put("date", time);

            map.put("totalEnergy", cumulativeEnergy);

            map.put("freezingEnergy", freezingEnergy);

            list.add(map);

        }

        return list;
    }

    @Override
    public List countGymShop(Integer cityId, String year, String month, Integer type) {

        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type){

            case 1:{
                timeList = getTimeList(year, month, 1);

            }
                break;
            case 2:{

            }
                break;
            case 3:{
                timeList = getTimeList(year, month, 3);
            }
                break;
            case 4:{
                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Integer countGymShop = gymShopDao.statisticalGymShop(cityId, startTime, endTime);

                    Map<String,Object> maps = new HashMap<>(16);

                    maps.put("date", count);

                    maps.put("serverPoint", countGymShop);

                    count ++;

                    list.add(maps);
                }
            }
            return list;
            case 5:{
                timeList = getTimeList(year, month, 5);
            }
                break;
            default:
                break;
        }


        for (String time : timeList){

            //门店数统计
            Integer countGymShop = gymShopDao.countGymShop(cityId, time);

            Map<String, Object> map = new HashMap<>(16);

            map.put("date", time);

            map.put("serverPoint", countGymShop);

            list.add(map);

        }

        return list;
    }

    @Override
    public List countTurnCardNumber(String shopId, String year, String month, Integer type) {

        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type){

            case 1:{
                timeList = getTimeList(year, month, 1);

            }
            break;
            case 2:{

            }
            break;
            case 3:{
                timeList = getTimeList(year, month, 3);
            }
            break;
            case 4:{
                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Integer countActivationCode = activationCodeDao.quarterActivationCode(shopId, startTime, endTime);

                    Map<String,Object> maps = new HashMap<>(16);

                    maps.put("date", count);

                    maps.put("turnCardNum", countActivationCode);

                    count ++;

                    list.add(maps);
                }
            }
            return list;
            case 5:{
                timeList = getTimeList(year, month, 5);
            }
            break;
            default:
                break;
        }

        for (String time : timeList){

            //门店数统计
            Integer count = activationCodeDao.countActivationCode(shopId, time);

            System.err.println("转卡次数 : " + count);

            Map<String, Object> map = new HashMap<>(16);

            map.put("date", time);

            map.put("turnCardNum", count);

            list.add(map);

        }

        return list;
    }

    @Override
    public Integer countConsumption(String shopId) {

        return expensesRecordDao.countByShopId(shopId);
    }

    @Override
    public List countGymTurnCard(String shopId, String year, String month, Integer type) {

        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type){

            case 1:{
                timeList = getTimeList(year, month, 1);

            }
            break;
            case 2:{

            }
            break;
            case 3:{
                timeList = getTimeList(year, month, 3);
            }
            break;
            case 4:{
                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Integer quarterGymTurnCard = activationCodeDao.quarterGymTurnCard(shopId, startTime, endTime);

                    Map<String,Object> maps = new HashMap<>(16);

                    maps.put("date", count + "季度");

                    maps.put("turnCardNumber", quarterGymTurnCard);

                    count ++;

                    list.add(maps);
                }
            }
            return list;
            case 5:{
                timeList = getTimeList(year, month, 5);
            }
            break;
            default:
                break;
        }

        for (String time : timeList){

            //门店数统计
            Integer count = activationCodeDao.countGymTurnCard(shopId,time);

            Map<String, Object> map = new HashMap<>(16);

            map.put("date", time);

            map.put("turnCardNumber", count);

            list.add(map);

        }
        return list;
    }

    @Override
    public Integer countGymShopNum(Integer cityId) {

        List<GymShopPO> cityId1 = gymShopDao.findAll(new Specification<GymShopPO>() {

            List<Predicate> list = new ArrayList<Predicate>();

            @Override
            public Predicate toPredicate(Root<GymShopPO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                if (ObjectUtils.anyNotNull(cityId)) {
                    list.add(criteriaBuilder.equal(root.get("cityId").as(Integer.class), cityId));
                }

                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        });

        return cityId1.size();
    }

    @Override
    public Map<String, Object> countGymTurnCardNumber(String shopId) {

        Map<String,Object> map = new HashMap<>(16);

        // 总人数
        Integer countHeadcount = activationCodeDao.countHeadcount(shopId);

        // 统计健身房 转卡总数
        Integer integer = activationCodeDao.countByGymShopId(shopId);

        map.put("headcount", countHeadcount);

        map.put("turnCardNumber", integer);

        return map;
    }

    @Override
    public Integer countPartnerRecord(String gymShopId) {

        return partnerRecordDao.countByGymShopId(gymShopId);
    }

    @Override
    public Map<String, Object> countUserPartnerRecord(String userId) {

        // 统计用户总匹配数
        Integer integer = partnerRecordDao.countAllByAuserIdOrBuserId(userId, userId);

        // 统计用户搭伙成功数
        Integer integer1 = partnerRecordDao.countAllByTypeAndAuserIdOrBuserId(1, userId, userId);

        Map<String, Object> map = new HashMap<>(16);

        map.put("matchingNumber", integer);

        map.put("partnerRecordNum", integer1);

        return map;
    }

    @Override
    public List countPartnerRecordChart(String userId, String year, String month, Integer type) {
        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type){

            case 1:{
                timeList = getTimeList(year, month, 1);

            }
            break;
            case 2:{

            }
            break;
            case 3:{
                timeList = getTimeList(year, month, 3);
            }
            break;
            case 4:{
                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    // 用户总匹配数
                    Integer quarterCountData = partnerRecordDao.quarterUserTotalNumber(userId,userId,startTime,endTime);

                    // 用户搭伙成功数
                    Integer successfulNumber = partnerRecordDao.quarterUserSuccessfulNumber(1,userId,userId, startTime,
                            endTime);

                    Map<String,Object> maps = new HashMap<>(16);

                    maps.put("date", count);

                    maps.put("matchingNumber", quarterCountData);

                    maps.put("partnerRecordNum", successfulNumber);

                    count ++;

                    list.add(maps);
                }
            }
            return list;

            case 5:{
                timeList = getTimeList(year, month, 5);
            }
            break;

            default:
                break;
        }

        for (String time : timeList){

            // 用户总匹配数
            Integer quarterCountData = partnerRecordDao.countUserTotalNumber(userId,userId,time);

            // 用户搭伙成功数
            Integer successfulNumber = partnerRecordDao.countUserSuccessfulNumber(1,userId,userId, time);

            Map<String, Object> map = new HashMap<>(16);

            map.put("date", time);

            map.put("matchingNumber", quarterCountData);

            map.put("partnerRecordNum", successfulNumber);

            list.add(map);

        }
        return list;
    }

    @Override
    public List countServicePoint(String gymShopId, String year, String month, Integer type) {

        List list = new ArrayList();

        List<String> timeList = new ArrayList<>();

        switch (type){

            case 1:{
                timeList = getTimeList(year, month, 1);

            }
                break;

            case 2:{

            }
                break;

            case 3:{
                timeList = getTimeList(year, month, 3);
            }
                break;

            case 4:{
                List<Map<String,String>> mapList = getTimeList(year, month, 4);

                int count = 1;

                for (Map<String, String> map:mapList) {

                    String startTime = map.get("startTime");

                    String endTime = map.get("endTime");

                    Integer quarterPartnerRecord = partnerRecordDao.quarterPartnerRecord(gymShopId, startTime, endTime);

                    Map<String,Object> maps = new HashMap<>(16);

                    maps.put("date", count);

                    maps.put("matchingNumber", quarterPartnerRecord);

                    count ++;

                    list.add(maps);
                }
            }
            return list;
            case 5:{
                timeList = getTimeList(year, month, 5);
            }
            break;
            default:
                break;
        }

        for (String time : timeList){

            //门店数统计
            Integer count = partnerRecordDao.countPartnerRecord(gymShopId,time);

            Map<String, Object> map = new HashMap<>(16);

            map.put("date", time);

            map.put("matchingNumber", count);

            list.add(map);

        }
        return list;
    }

    @Override
    public Page<PartnerRecordPO> pagePartnerRecord(String gymShopId, String userId, Paging page) {

        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize());


        Page<PartnerRecordPO> all = partnerRecordDao.findAll(new Specification<PartnerRecordPO>() {
            @Override
            public Predicate toPredicate(Root<PartnerRecordPO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (StringUtils.isNotEmpty(gymShopId)) {
                    list.add(cb.like(root.get("gymShopId"), "%" + gymShopId + "%"));
                }

                if (StringUtils.isNotEmpty(userId)) {

                    Predicate predicate = cb.like(root.get("auserId"), "%" + userId + "%");

                    Predicate predicate1 = cb.like(root.get("buserId"), "%" + userId + "%");

                    list.add(cb.or(predicate, predicate1));
                }

                Predicate[] p = new Predicate[list.size()];

                return cb.and(list.toArray(p));
            }
        }, pageable);

        return all;
    }

    @Override
    public Integer countServiceComment(Integer serviceId) {

        Integer integer = serviceCommentDao.countByServiceIdAndStatus(serviceId,1);

        return integer;
    }

    private List getTimeList(String year, String month, Integer type){

        List timeList = new ArrayList<>();

        List<Map<String, String>> list = new ArrayList();

        switch (type){
            case 1: {
                if (ObjectUtils.anyNotNull(month)){

                    String time = year + "-" + month + "-1";

                    String times = "";
                    System.out.println(time);
                    Integer days = productDao.queryDay(time);

                    System.err.println(days);

                    for (int i = 1; i <= days ; i++) {
                        if (i <10) {
                           times  = year + "-" +  month + "-0" + i;
                            System.err.println(times);
                        }else {
                            times  = year + "-" +  month + "-" + i;
                            System.err.println(times);
                        }

                        timeList.add(times);
                    }
                }
            }
                break;
            case 3: {
                for (int i = 1; i <= 12; i++) {
                    String time;

                    if (i < 10) {
                        time = year + "-0" + i;
                    } else {
                        time = year + "-" + i;
                    }

                    timeList.add(time);
                }
            }
                break;

            case 4:{

                int i = 1;

                while (i<=12){

                    Map<String, String> map = new HashMap<>(16);

                    String startTime;
                    String endTime;

                    startTime = 2019 + "-" + i + "-1";

                    if (i == 10){

                        Integer integer = productDao.queryDay("2019-12-1");

                        System.out.println(integer);

                        endTime = 2019 + "-" + 12 + "-" + integer;
                    }else {
                        endTime = 2019 + "-" + (i+3) + "-1";
                    }

                    map.put("startTime",startTime);
                    map.put("endTime",endTime);

                    list.add(map);

                    i = i + 3;
                }
            }
                return list;
            case 5:{
                Integer years = NumberUtils.createInteger(year);

                for (int i = years - 4; i <= years; i ++){

                    String time = String.valueOf(i);

                    timeList.add(time);

                }
            }
                break;
            default:
                break;
        }
        return timeList;
    }
}
