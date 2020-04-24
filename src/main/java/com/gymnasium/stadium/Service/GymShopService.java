package com.gymnasium.stadium.Service;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.Util.SCException;
import com.gymnasium.Util.vo.DateVo;
import com.gymnasium.core.page.Paging;
import com.gymnasium.file.VO.GymImagesVO;
import com.gymnasium.personnel.PO.CoachUserPO;
import com.gymnasium.stadium.PO.*;
import com.gymnasium.stadium.VO.GymFitnessRecordVO;
import com.gymnasium.stadium.VO.GymShopVO;
import com.gymnasium.stadium.VO.GymbsVO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 王志鹏
 * @title: GymShopService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/2 15:24
 */
public interface GymShopService {

    Page<GymShopPO> pageGymShop(Paging page, GymShopPO gymShopPO);

    GymShopVO queryByGymShopId(String gymShopId);

    Page<GymShopPO> searchGymShop(Paging page, GymShopPO gymShopPO);

    List<CardTypePO> queryCardTypePOByGymShopId(String gymShopId);

    List<CoachUserPO> queryCoachUserByGymShopId(String gymShopId);

    GymbsVO queryGymShopByaAttach(String gymShopId);

    /**
     * 查询开放匹配的健身房
     * @return
     */
    List<GymShopPO> queryByMatching(Integer districtId);

    //分页查询健身历史记录
    Page<GymFitnessRecordPO> pageGymFitnessRecord(Paging page, GymFitnessRecordPO gymFitnessRecordPO);

    //添加健身房
    boolean addGymShop(MultipartFile files, GymShopVO gymShopVO) throws Exception;

    //添加建筑
    boolean addGymBuilding(String gymShopId, int bid);

    //添加课程科目
    boolean addGymSubject(String gymShopId, int sid);

    //更新健身房信息
    boolean updateGymShopByGymShopId(MultipartFile files, GymShopVO gymShopVO, Integer bid);

    //更新健身房头像
    boolean updateGymShopAvatar(MultipartFile files, GymImagesVO gymImagesVO);

    boolean addStratGym(GymFitnessRecordPO gymFitnessRecordPO) throws SCException;

    Result findGymImages(Paging page, String gymShopId, Integer type);

    GymFitnessRecordVO addEndGym(String uesrId,String code) throws SCException;

    //正常核销健身
    GymFitnessRecordVO addTowEndGym(String uesrId) throws SCException;

    //正常核销健身
    GymFitnessRecordVO addUserEndGym(String uesrId, String lat, String lnt) throws SCException;

    //异常核销健身
    GymFitnessRecordVO addUnusualTowEndGym(String uesrId) throws SCException;

    //查询健身当前健身记录
    GymFitnessRecordVO queryGymFitnessRecordPOBySerialId(String uesrId) throws SCException;

    //查询健身房下的健身核销
    List<GymFitnessRecordPO> queryGymFitnessRecordByGymShopId(Paging page, GymFitnessRecordVO gymFitnessRecordVO);

    Boolean addMoreGymBuilding(String bid, Integer gid);

    Boolean addMoreGymSubject(String sid, Integer gid);

    Boolean delGymBuilding(Integer id);

    Boolean delGymSubject(Integer id);

    Boolean updateGymBuilding(GymBuildingPO gymBuildingPO);

    Boolean updateGymSubject(GymSubjectPO gymSubjectPO);

    Boolean delGymShop(Integer id);

    //分页查询健身历史记录
    Page<GymFitnessRecordPO> queryGymFitnessRecord(Paging page, String id, String gymShopId, DateVo dateVo);

}