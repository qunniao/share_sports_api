package com.gymnasium.order.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author 王志鹏
 * @title: CouponPO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/5/23 10:35
 */
@Data
@Entity
@Table(name = "order_coupon", catalog = "gymnasium")
@ApiModel(value = "CouponPO", description = "优惠卷")
public class CouponPO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "type")
    @ApiModelProperty(value = "类型1.特权券2.礼包券3.商城券", name = "type", example = "1")
    private Integer type;

    @Column(name = "title")
    @ApiModelProperty(value = "名称", name = "title", example = "1")
    private String title;

    @Column(name = "content")
    @ApiModelProperty(value = "内容", name = "content", example = "1")
    private String content;

    @Column(name = "cover")
    @ApiModelProperty(value = "配图", name = "cover", example = "1")
    private String cover;

    @Column(name = "number")
    @ApiModelProperty(value = "数量", name = "number", example = "1")
    private Integer number;

    @Column(name = "use_way")
    @ApiModelProperty(value = "使用方式:1.直接核销2.商品减部分能量值3.商品减部分金额", name = "useWay", example = "1")
    private Integer useWay;

    @Column(name = "issue_way")
    @ApiModelProperty(value = "发放方式:1.人工2.自动(会员购买或激活)", name = "issueWay", example = "1")
    private Integer issueWay;

    @Column(name = "price")
    @ApiModelProperty(value = "优惠金额", name = "price", example = "1")
    private BigDecimal price;

    @Column(name = "member_level")
    @ApiModelProperty(value = "会员等级1.体验2.大众3.精英4.皇家", name = "memberLevel", example = "1")
    private Integer memberLevel;

    @Column(name = "validity")
    @ApiModelProperty(value = "有效期(天):0.永久365.一年", name = "validity", example = "1")
    private Integer validity;

    @Column(name = "is_hot")
    @ApiModelProperty(value = "isHot", name = "isHot", example = "1")
    private Integer isHot;

    @Column(name = "icon")
    @ApiModelProperty(value = "图标文件", name = "icon", example = "1")
    private String icon;

    @Column(name = "status")
    @ApiModelProperty(value = "状态:0.删除1.上架2.下架", name = "status", example = "1")
    private Integer status;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Timestamp updateTime;
}
