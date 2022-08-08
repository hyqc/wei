package com.wei.admin.bo;

import lombok.Data;

import java.util.List;

/**
 * @author wlp
 * @date 2022/8/6
 **/
@Data
public class PermissionApiItem {
    private Integer id;
    private Integer menuId;
    private String key;
    private String type;
    private String typeText;
    private String name;
    private List<ApiListItem> apis;
    private Boolean enabled;
    private String describe;
}
