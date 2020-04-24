package com.gymnasium.personnel.VO;

import lombok.Data;


/**
 * @author 边书恒
 * @Title: PermissionVO
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 17:24
 */
@Data
public class PermissionVO {

    private Integer pid;

    private Integer mid;

    private String name;

    private String permission;

    private String permissionCol;

    private Boolean available;
}
