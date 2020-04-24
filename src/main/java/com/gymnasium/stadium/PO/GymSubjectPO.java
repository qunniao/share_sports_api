package com.gymnasium.stadium.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 王志鹏
 * @Date 2019/4/6 11:37
 **/
@Data
@Entity
@Table(name = "gym_subject", catalog = "gymnasium")
@ApiModel(value = "GymBuildingPO健身房课程", description = "健身房课程")
public class GymSubjectPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "gid健身房", name = "id", example = "1")
    private Integer id;

    @Column(name = "sid")
    @ApiModelProperty(value = "健身房课程", name = "sid", example = "1")
    private Integer sid;

    @Column(name = "gid")
    @ApiModelProperty(value = "gid健身房主键", name = "gid", example = "1")
    private Integer gid;

    public GymSubjectPO setSidAndGig(int sid, int gid) {
        setSid(sid);
        setGid(gid);

        return this;
    }

}
