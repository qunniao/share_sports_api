package com.gymnasium.personnel.PO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:07
 **/

@Getter
@Setter
@Entity
@Table(name = "role", catalog = "gymnasium")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Integer rid;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "available")
    private Boolean available = Boolean.FALSE;

    @Column(name = "description")
    private String description;

    //角色 -- 权限关系：多对多关系; 1个角色可以有多个权限
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "rid")}, inverseJoinColumns = {@JoinColumn(name = "pid")})
    private List<Permission> permissionList;

    // 用户 - 角色关系定义;一个角色对应多个用户
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "rid")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<UserManagePO> userList;

}
