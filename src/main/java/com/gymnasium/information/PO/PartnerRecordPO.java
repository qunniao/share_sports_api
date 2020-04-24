package com.gymnasium.information.PO;

import com.gymnasium.personnel.PO.UserPO;
import com.gymnasium.stadium.PO.GymShopPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: PartnerRecordPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/23 15:26
 */
@Getter
@Setter
@Entity
@Table(name = "infromation_partnerrecord", catalog = "gymnasium")
@ApiModel(value = "PartnerRecordPO搭伙记录", description = "搭伙记录")
public class PartnerRecordPO implements Serializable {

    public static final int  TYPE_INIT = 1;
    public static final int  TYPE_YES = 2;
    public static final int  TYPE_NO = 3;
    public static final int  TYPE_ARRIVE = 4;
    public static final int  TYPE_MISSING = 5;


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "搭伙记录主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "gymShopId")
    @ApiModelProperty(value = "健身房编号", name = "gymShopId", example = "SY_afa1651")
    private String gymShopId;

    @Column(name = "auserId")
    @ApiModelProperty(value = "本人编号", name = "auserId", example = "FG_046587")
    private String auserId;

    @Column(name = "buserId")
    @ApiModelProperty(value = "搭伙人编号", name = "buesrId", example = "FG_046587")
    private String buserId;

    @Column(name = "startTime")
    @ApiModelProperty(value = "预约开始时间", name = "startTime", example = "FG_046587")
    private Timestamp startTime;

    @Column(name = "endTime")
    @ApiModelProperty(value = "预约结束时间", name = "endTime", example = "FG_046587")
    private Timestamp endTime;

    @Column(name = "payType")
    @ApiModelProperty(value = "能量值付款方式AA,A,B", name = "payType", example = "FG_046587")
    private String payType;

    @Column(name = "type")
    @ApiModelProperty(value = "1发起,2同意,3拒绝", name = "type", example = "FG_046587")
    private Integer type;
    //4已到厂,5未到场 搭伙记录

    @Column(name = "atype")
    @ApiModelProperty(value = "a用户是否到场:0初始1到场,2未到场", name = "type", example = "FG_046587")
    private Integer atype;

    @Column(name = "btype")
    @ApiModelProperty(value = "b用户是否到场:0初始1到场,2未到场", name = "type", example = "FG_046587")
    private Integer btype;

    @Column(name = "cityName")
    @ApiModelProperty(value = "城市区域名称str", name = "cityName", example = "xxxxx")
    private String cityName;

    @Column(name = "createTime")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-04-02 13:28:31")
    private Timestamp createTime;

    @Column(name = "promiseTime")
    @ApiModelProperty(value = "约定的年月日", name = "promiseTime", example = "2019-04-02")
    private String promiseTime;

    @OneToOne(targetEntity = GymShopPO.class)
    @JoinColumn(name = "gymShopId",referencedColumnName = "gymShopId",insertable=false ,updatable = false)
    private GymShopPO gymShopPO;

    @ManyToOne(targetEntity = UserPO.class)
    @JoinColumn(name = "auserId",referencedColumnName = "userId",insertable=false ,updatable = false)
    private UserPO aUserPO;

    @ManyToOne(targetEntity = UserPO.class)
    @JoinColumn(name = "buserId",referencedColumnName = "userId",insertable=false ,updatable = false)
    private UserPO bUserPO;
}
