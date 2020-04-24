package com.gymnasium.store.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/6/5 18:55
 * @Description:
 */
@Data
@ApiModel(value = "AgentRecommendVO", description = "报表返回数据")
public class AgentRecommendVO implements Serializable {

    @ApiModelProperty(value = "时间", name = "times", example = "2019-06-05")
    private String times;

    @ApiModelProperty(value = "团队总人数", name = "teamSize", example = "1")
    private Integer teamSize;

    @ApiModelProperty(value = "推荐总次数", name = "recommendNumber", example = "1")
    private Integer recommendNumber;

    @ApiModelProperty(value = "代理总收益", name = "totalRevenue", example = "1")
    private BigDecimal totalRevenue;
}
