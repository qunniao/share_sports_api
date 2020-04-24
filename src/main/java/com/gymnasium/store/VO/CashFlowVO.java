package com.gymnasium.store.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/31 09:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFlowVO implements Serializable {

    private Integer id;

    private Integer userId;

    private String orderNumber;

    private String flowNumber;

    private String title;

    private String content;

    private BigDecimal money;

    private Integer type;

    private Integer role;

    private Long orderId;

    private Integer superior;

    private Integer secondLevel;

    private Integer way;

    private BigDecimal clientCommission;

    private BigDecimal AgentCommission;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
