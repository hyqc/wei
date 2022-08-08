package com.wei.admin.bo;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 */
@Data
public class PermissionListItem {
    private Integer modelId;
    private String modelName;
    private List<PageItem> pages;


    @Data
    public static class PageItem {
        private Integer pageId;
        private String pageName;
        private List<PermissionItem> permissions;
    }

    @Data
    public static class PermissionItem {
        private Integer permissionId;
        private String permissionName;
        private String permission;
    }
}
