package com.gymnasium.personnel.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gymnasium.personnel.PO.Role;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 12:43
 **/
@Data
public class UserManageVO implements Serializable {

    private Integer uid;
    private String userId;
    @JsonIgnore
    private String userName;
    @JsonIgnore
    private String passWord;
    @JsonIgnore
    private String salt;
    private Integer state;
    @JsonIgnore
    private String token;
    private Date createTime;
    private Date updateTime;
    @JsonIgnore
    private Integer role;

    public UserManageVO() {
    }

    public UserManageVO(String userId, String userName, String passWord, Integer role) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
    }

}
