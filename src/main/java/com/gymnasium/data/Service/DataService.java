package com.gymnasium.data.Service;

import com.gymnasium.Util.PO.Result;
import com.gymnasium.data.PO.BuildingPO;
import com.gymnasium.data.PO.SubjectPO;
import com.gymnasium.data.PO.UserTypePO;
import com.gymnasium.stadium.VO.GymShopVO;

import java.util.List;

/**
 * @author 王志鹏
 * @title: DataService
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/3 17:20
 */
public interface DataService {

    List<UserTypePO> findAll();

    Boolean addSubject(SubjectPO subjectPO);

    Boolean addBuilding(BuildingPO buildingPO);

    boolean addUserType(UserTypePO userTypePO);

    GymShopVO decryptQRCode(String sgin);

    Result updateDataBuilding(BuildingPO buildingPO);

    Result updateDataSubject(SubjectPO subjectPO);


}
