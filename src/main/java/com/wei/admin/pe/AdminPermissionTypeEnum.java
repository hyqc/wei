package com.wei.admin.pe;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限类型页面，按钮
 *
 * @author wlp
 * @date 2022/6/19
 **/
public enum AdminPermissionTypeEnum {
    /**
     * 查看
     */
    VIEW("view", "查看"),
    /**
     * 编辑
     */
    EDIT("edit", "编辑"),
    /**
     * 删除
     */
    DELETE("delete", "删除");

    AdminPermissionTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    private String value;
    private String text;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public final static Map<String, AdminPermissionTypeEnum> MAP = new HashMap<>();

    static {
        for (AdminPermissionTypeEnum item : AdminPermissionTypeEnum.values()) {
            MAP.put(item.getValue(), item);
        }
    }

    public static AdminPermissionTypeEnum getByValue(String value) {
        return MAP.get(value);
    }
}
