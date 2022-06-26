package com.wei.admin.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Administrator
 */
@Getter
@Setter
public class PermissionListItem {
    private Integer modelId;
    private String modelName;
    private List<PageItem> pages;


    @Getter
    @Setter
    public static class PageItem {
        private Integer pageId;
        private String pageName;
        private List<PermissionItem> permissions;
    }

    @Getter
    @Setter
    public static class PermissionItem {
        private Integer permissionId;
        private String permissionName;
        private String permission;
    }
}
