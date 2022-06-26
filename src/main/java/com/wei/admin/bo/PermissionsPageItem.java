package com.wei.admin.bo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class PermissionsPageItem {
    private Integer permissionId;
    private String permissionName;
    private String permission;
    private Integer menuId;
    private String menuName;
    private Integer menuParentId;
    private Integer modelId;
    private String modelName;
}
