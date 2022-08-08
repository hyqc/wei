package com.wei.admin.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wlp
 * @date 2022/8/6
 **/
public enum SortTypeEnum {
    /**
     * 降序
     */
    DESC("descend", "降序", "desc"),
    ASC("ascend", "升序", "asc");

    private String key;
    private String text;
    private String value;

    SortTypeEnum(String key, String text, String value) {
        this.key = key;
        this.text = text;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String getKey() {
        return this.key;
    }

    public String getText() {
        return this.text = text;
    }

    public static Map<String, SortTypeEnum> MAP = new HashMap<>();

    static {
        for (SortTypeEnum item : SortTypeEnum.values()) {
            MAP.put(item.getKey(), item);
        }
    }
}
