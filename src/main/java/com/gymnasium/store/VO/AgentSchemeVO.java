package com.gymnasium.store.VO;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/24 11:17
 * @Description:
 */
@Data
public class AgentSchemeVO implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer operatorId;

    private Integer productId;

    private String agentCode;

    private BigDecimal agentPrice;

    private Integer type;

    private Integer memberLevel;

    private BigDecimal agentCommission;

    private BigDecimal agentDeveloperCommission;

    private Date createTime;

    private Date updateTime;
}
