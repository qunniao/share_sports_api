package com.gymnasium.store.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 边书恒
 * @Description: TODO
 * @projectName gymnasium
 * @createTime 2019/5/18 14:53
 *
 */
@Data
public class ProductTypeVO implements Serializable {

    private Integer id;

    private String title;

    private Integer status;

    private String image;

    private Integer settlementType;

    private Date createTime;

    private Date updateTime;

}
