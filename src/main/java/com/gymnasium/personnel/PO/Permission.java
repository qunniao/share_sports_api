package com.gymnasium.personnel.PO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.GET;
import java.io.Serializable;
import java.util.List;

/**
 * @Author 王志鹏
 * @Date 2019/3/31 13:47
 * 权限
 **/
@Getter
@Setter
@Entity
@Table(name = "permission", catalog = "gymnasium")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Integer pid;

    /**
     * 菜单id
     */
    @Column(name = "mid")
    private Integer mid;

    @Column(name = "name")
    private String name;

    @Column(name = "permission")
    private String permission;

    @Column(name = "permission_col")
    private String permissionCol;

    @Column(name = "available")
    private Boolean available = Boolean.FALSE;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = {@JoinColumn(name = "pid")}, inverseJoinColumns = {@JoinColumn(name = "rid")})
    private List<Role> roleList;

}
