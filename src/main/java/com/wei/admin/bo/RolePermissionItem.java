package com.wei.admin.bo;

import com.wei.admin.pe.AdminPermissionTypeEnum;
import lombok.Data;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Data
public class RolePermissionItem {
    private Integer roleId;
    private Integer permissionId;
    private String permissionKey;
    private String permissionName;
    private String permissionType;
    private String permissionTypeText;

    public void setPermissionTypeText() {
        this.permissionTypeText = AdminPermissionTypeEnum.getByValue(this.permissionType).getText();
    }
}
