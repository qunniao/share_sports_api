package com.gymnasium.personnel.PO;

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
@Entity
@Table(name = "user_manage", catalog = "gymnasium")
public class UserManagePO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Integer uid;

    @Column(name = "userId")
    private String userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "passWord")
    private String passWord;

    @Column(name = "salt")
    private String salt;

    @Column(name = "state")
    private Integer state;

    private String token;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "rid")})
    private List<Role> roleList;
}
