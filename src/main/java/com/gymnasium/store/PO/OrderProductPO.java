package com.gymnasium.store.PO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/23 10:25
 * @Description:
 */
@Data
@Entity
@Table(name = "order_product", catalog ="gymnasium")
public class OrderProductPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键", name = "id", example = "1")
    private Integer id;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单id", name = "orderId", example = "1")
    private Long orderId;

    @Column(name = "product_id")
    @ApiModelProperty(value = "产品id", name = "productId", example = "1")
    private Integer productId;

    @Column(name = "style")
    @ApiModelProperty(value = "款式或规格型号", name = "style", example = "1")
    private String style;

    @Column(name = "number")
    @ApiModelProperty(value = "数量", name = "number", example = "1")
    private Integer number;

    @Column(name = "price")
    @ApiModelProperty(value = "商品单价", name = "price", example = "1")
    private BigDecimal price;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "1")
    private Date createTime;

    @OneToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id", insertable = false, updatable = false)
    private ProductPO productPO;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id", insertable = false, updatable = false)
    private AgentSchemePO agentSchemePO;

    @OneToMany(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id", insertable = false, updatable = false)
    private List<ChannelSchemePO> channelSchemePO;
}
