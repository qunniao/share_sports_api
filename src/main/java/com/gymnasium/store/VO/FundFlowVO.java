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
 * @Date: 2019/5/31 14:39
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundFlowVO implements Serializable {

    private Integer id;

    private Integer userId;

    private String flowNumber;

    private Integer transactionType;

    private Integer type;

    private Long orderId;

    private String title;

    private String content;

    private BigDecimal money;

    private Date createTime;

    private Date updateTime;

    private Integer status;
}
