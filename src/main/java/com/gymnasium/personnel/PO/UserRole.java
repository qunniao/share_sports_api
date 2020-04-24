package com.gymnasium.personnel.PO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:17
 **/
@Data
@Entity
@Table(name = "user_role", catalog = "gymnasium")
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "urid")
    private Integer urid;


    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    @Column(name = "uid")
    private Integer uid;

    @Column(name = "rid")
    private Integer rid;


}
