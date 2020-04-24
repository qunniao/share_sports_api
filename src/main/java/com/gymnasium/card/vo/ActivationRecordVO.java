package com.gymnasium.card.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: 边书恒
 * @ClassName: gymnasium
 * @Date: 2019/7/3 15:04
 * @Description:
 */
@Data
public class ActivationRecordVO {

    private Integer id;

    private Integer cid;

    private String cardNum;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cardPassword;

    private String buyUserId;

    private String useUserId;

    private String name;

    private String phone;

    private Integer payType;

    private Integer type;

    private Timestamp operTime;
}
