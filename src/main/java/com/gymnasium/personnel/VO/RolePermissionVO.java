package com.gymnasium.personnel.VO;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author 边书恒
 * @Title: RolePermission
 * @ProjectName oyoc_java
 * @Description: TODO
 * @date 2019/8/21 17:26
 */
@Data
public class RolePermissionVO {

    private Integer rpid;

    private Integer rid;

    private Integer pid;
}
