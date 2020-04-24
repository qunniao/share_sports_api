package com.gymnasium.store.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 10:57
 * @Description:
 */

@Data
@Entity
@Table(name = "agent_scheme")
@DynamicUpdate
@ApiModel(value = "AgentSchemePO", description = "代理方案")
public class AgentSchemePO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "设置人id", name = "userId", example = "1")
    private Integer userId;

    @Column(name = "operator_id")
    @ApiModelProperty(value = "操作人id", name = "operatorId", example = "1")
    private Integer operatorId;

    @Column(name = "product_id")
    @ApiModelProperty(value = "产品id", name = "productId", example = "1")
    private Integer productId;

    @Column(name = "agent_code")
    @ApiModelProperty(value = "代理编码", name = "agentCode", example = "1")
    private String agentCode;

    @Column(name = "agent_price")
    @ApiModelProperty(value = "代理价", name = "agentPrice", example = "1")
    private BigDecimal agentPrice;

    @Column(name = "agent_commission")
    @ApiModelProperty(value = "代理人佣金", name = "agentCommission", example = "1")
    private BigDecimal agentCommission;

    @Column(name = "agent_developer_commission")
    @ApiModelProperty(value = "代理人发展佣金", name = "agentDeveloperCommission", example = "1")
    private BigDecimal agentDeveloperCommission;

    @Column(name = "type")
    @ApiModelProperty(value = "类型", name = "type", example = "1")
    private Integer type;

    @Column(name = "member_level")
    @ApiModelProperty(value = "会员等级", name = "memberLevel", example = "1")
    private Integer memberLevel;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "1")
    private Date updateTime;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id",insertable = false, updatable = false)
    private ProductPO productPO;
}
