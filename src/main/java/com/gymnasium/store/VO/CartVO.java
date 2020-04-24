package com.gymnasium.store.VO;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/20 18:47
 * @Description:
 */
@Data
public class CartVO{

    private Long id;

    private Integer userId;

    private Integer productId;

    private Integer number;

    private Integer settlementType;

    private String style;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
