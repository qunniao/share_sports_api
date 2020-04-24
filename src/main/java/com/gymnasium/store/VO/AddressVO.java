package com.gymnasium.store.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/5/21 09:57
 * @Description:
 */
@Data
public class AddressVO implements Serializable {

    private Integer id;

    private Integer userId;

    private String consignee;

    private String phoneNumber;

    private String area;

    private String detailedAddress;

    private Integer isDefault;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
