package com.gymnasium.stadium.PO;

import com.gymnasium.data.PO.BuildingPO;
import com.gymnasium.data.PO.SubjectPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 王志鹏
 * @Date 2019/4/6 11:36
 **/
@Data
@Entity
@Table(name = "gym_building", catalog = "gymnasium")
@ApiModel(value = "GymBuildingPO健身房建筑设施", description = "健身房建筑设施")
public class GymBuildingPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "bid")
    @ApiModelProperty(value = "bid设施建筑id", name = "bid", example = "1")
    private Integer bid;


    @Column(name = "gid")
    @ApiModelProperty(value = "gid健身房主键", name = "gid", example = "1")
    private Integer gid;

    public GymBuildingPO setBidAndGig(int bid, int gid) {
        setBid(bid);
        setGid(gid);

        return this;
    }
}
