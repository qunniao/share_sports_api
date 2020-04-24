package com.gymnasium.personnel.PO;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:56
 **/
@Data
@Entity
@Table(name = "role_permission", catalog = "gymnasium")
public class RolePermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rpid;

    @Column(name = "rid")
    private Integer rid;

    @Column(name = "pid")
    private Integer pid;

    @OneToOne
    @JoinColumn(name = "rid",referencedColumnName = "rid",insertable = false,updatable = false)
    private Role role;
}