package com.gymnasium.store.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/5/29 20:17
 * @Description:
 */
@Data
public class CommissionVO implements Serializable {

    private Integer id;

    private Integer role;

    private Integer userId;

    private Integer superior;

    private Integer secondLevel;

    private BigDecimal balance;

    private BigDecimal profits;

    private BigDecimal canCarry;

    private BigDecimal waitCashed;

    private BigDecimal applied;

    private BigDecimal cashed;

    private BigDecimal invalid;

    private BigDecimal waitReceive;

    private BigDecimal noSettlement;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal clientCommission;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal agentCommission;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal channelCommission;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String agentCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String agentDeveloper;
}
