package com.gymnasium.order.VO;

import com.gymnasium.card.PO.ActivationPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author 王志鹏
 * @title: OrderVO
 * @projectName gymnasium
 * @description: TODO
 * @date 2019/4/8 15:30
 */
@Data
public class OrderVO implements Serializable {

    private Integer id;
    private String userId;
    private String name;
    private Integer level;
    private String phone;
    private BigDecimal price;
    private String productItem;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String orderNumber;
    private BigDecimal energy;
    private Integer type;
    private Integer orderState;
    private Integer number;
    private String address;
    private String courierNumber;
    private String deliveryWay;
    private Date payTime;
    private Date deliveryTime;
    private Date receivingTime;
    private BigDecimal memberPrice;

}
