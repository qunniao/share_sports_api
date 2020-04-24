package com.gymnasium.store.VO;

import lombok.Data;

/**
 * @Author: 边书恒
 * @ClassName: oyoc_interface
 * @Date: 2019/6/1 18:41
 * @Description:
 */
@Data
public class FundFlowRequestVO {

    private String startTime;
    private String endTime;
    private String minMoney;
    private String maxMoney;
}
