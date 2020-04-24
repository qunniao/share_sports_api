package com.gymnasium.store.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gymnasium.store.PO.ProductPO;
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
public class ChannelSchemeVO implements Serializable {

    private Integer id;

    private Integer userId;

    private Integer operatorId;

    private Integer type;

    private Integer memberLevel;

    private Integer productId;

    private String channelCode;

    private BigDecimal channelPrice;

    private Integer level;

    private BigDecimal profit;

    private Date createTime;

    private Date updateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductPO productPO;

}
