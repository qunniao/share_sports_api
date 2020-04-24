package com.gymnasium.store.service;

import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.store.VO.AgentRecommendVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/5 16:31
 * @Description:
 */
public interface ChartService {

    /**
     * 按年月日统计 代理团队报表
     * @param userId
     * @param year
     * @param month
     * @param type
     * @return
     */
    List<AgentRecommendVO> queryAgentChart(Integer userId, String year, String month, Integer type);

    /**
     * 按年月日统计 产品数量报表
     * @param productType
     * @param status
     * @param year
     * @param month
     * @param type
     * @return
     */
    List<Integer> productNumberChart(Integer productType, Integer status, String year,String month, Integer type);

    /**
     * 按年月日统计 代理人报表
     * @param region
     * @param year
     * @param month
     * @param type
     * @return
     */
    List queryAgentNumber(String region, String year, String month, Integer type);

    /**
     * 按年月日统计 搭伙成功人数
     * @param promiseTime
     * @param year
     * @param month
     * @param type
     * @return
     */
    List queryBoardRecord(String promiseTime, String year, String month, Integer type);

    /**
     * 按年月日统计会员消费图表
     * @param userId
     * @param year
     * @param month
     * @param type
     * @return
     */
    List queryMemberConsumption(String userId, String year, String month,Integer type);

    /**
     * 按年月日统计门店数图表
     * @param cityId
     * @param year
     * @param month
     * @param type
     * @return
     */
    List countGymShop(Integer cityId,String year, String month,Integer type);

    /**
     * 统计转卡次数图表
     * @param shopId
     * @param year
     * @param month
     * @param type
     * @return
     */
    List countTurnCardNumber(String shopId, String year, String month,Integer type);

    /**
     * 统计总消费次数
     * @param gymShopId
     * @return
     */
    Integer countConsumption(String gymShopId);

    /**
     * 统计健身房转卡历史人数图表
     * @param shopId
     * @param year
     * @param month
     * @param type
     * @return
     */
    List countGymTurnCard(String shopId, String year, String month,Integer type);

    /**
     * 统计 健身房数量
     * @param cityId
     * @return
     */
    Integer countGymShopNum(Integer cityId);

    /**
     * 统计转卡总数，转卡总人数
     * @param shopId
     * @return
     */
    Map<String, Object> countGymTurnCardNumber(String shopId);

    Integer countPartnerRecord(String gymShopId);

    /**
     * 用户搭伙数据统计(总匹配数/成功数)
     * @param userId
     * @return
     */
    Map<String, Object> countUserPartnerRecord(String userId);

    /**
     * 用户搭伙数据按年月日统计
     * @param userId
     * @param year
     * @param month
     * @param type
     * @return
     */
    List countPartnerRecordChart(String userId, String year, String month,Integer type);

    /**
     * 查询服务点
     * @param gymShopId
     * @param year
     * @param month
     * @param type
     * @return
     */
    List countServicePoint(String gymShopId, String year, String month,Integer type);

    /**
     * 分页查询搭伙记录
     * @param gymShopId
     * @param userId
     * @param paging
     * @return
     */
    Page<PartnerRecordPO> pagePartnerRecord(String gymShopId, String userId,Paging paging);

    /**
     * 查询客服历史评论总数
     * @return
     */
    Integer countServiceComment(Integer serviceId);

}
