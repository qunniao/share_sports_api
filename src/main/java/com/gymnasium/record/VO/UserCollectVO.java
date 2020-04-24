package com.gymnasium.record.VO;

import com.gymnasium.personnel.PO.CoachUserPO;
import com.gymnasium.stadium.PO.GymShopPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王志鹏
 * @title: UserCollectVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/20 14:18
 */

@Data
public class UserCollectVO implements Serializable {

    @ApiModelProperty(value = "本人用户编号", name = "userId", example = "gy_xxxx")
    private String userId;

    @ApiModelProperty(value = "健身房", name = "shopPOList", example = "1")
    private List<GymShopPO> shopPOList;

    @ApiModelProperty(value = "教练", name = "coachId", example = "1")
    private List<CoachUserPO> coachUserPOList;
}
