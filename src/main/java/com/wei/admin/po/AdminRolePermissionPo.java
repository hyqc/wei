package com.wei.admin.po;

import lombok.Data;

/**
 * 角色-权限对应表
 *
 * @author wlp
 */
@Data
public class AdminRolePermissionPo {
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 权限ID
     */
    private Integer permissionId;
}
