package com.gymnasium.information.Service;

import com.gymnasium.Util.SCException;
import com.gymnasium.Util.vo.DateVo;
import com.gymnasium.core.page.Paging;
import com.gymnasium.information.PO.MatchRecord;
import com.gymnasium.information.PO.PartnerRecordPO;
import com.gymnasium.information.PO.PartnerpoolPO;
import com.gymnasium.information.VO.PartnerUserVO;
import com.gymnasium.information.VO.PartnerpoolVO;
import com.gymnasium.personnel.PO.UserPO;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author 王志鹏
 * @title: PartnerService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/18 10:15
 */
public interface PartnerService {

    Page<PartnerRecordPO> PagePartnerRecord(Paging page, String userId);

    List<PartnerRecordPO> queryByABuserId(String auserid, String buserId);

    PartnerpoolPO queryPartnerpoolByUserId(String userId);

    //查询好友列表
    List<UserPO> queryGoodFriendList(String auserId);

    boolean addPartnerpool(PartnerpoolPO partnerpoolPO);

    List<PartnerUserVO> pagePartnerpool(Paging page, PartnerpoolVO partnerpoolVO);

    boolean addGoogFriend(String auserId, String buserId) throws SCException;

    Integer addPartnerRecord(PartnerRecordPO partnerRecordPO, String startTime);

    //同意搭伙
    boolean partnerYES(int id);

    boolean partnerNO(int id);

    boolean partnerSTOP(String userId);

    MatchRecord queryMatchRecord(Integer userId);

    Page<PartnerRecordPO> queryPartnerRecord(Paging page, PartnerRecordPO partnerRecordPO, DateVo dateVo);

}
