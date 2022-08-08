package com.wei.admin.bo;

import lombok.Data;

import java.util.List;

/**
 * @author wlp
 * @date 2022/8/6
 **/
@Data
public class MenuPermissions {
    private MenuItem menu;
    private List<PermissionApiItem> permissions;
}
