package com.wei.admin.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件类型
 */
public enum UploadFileTypeEnum {

    /**
     * 图片
     */
    IMAGE(1, "图片");

    private final Integer type;
    private final String desc;

    UploadFileTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
    private static final Map<Integer, UploadFileTypeEnum> codeIndexes = new HashMap<>();

    static {
        for (UploadFileTypeEnum uploadFileTypeEnum : UploadFileTypeEnum.values()) {
            codeIndexes.put(uploadFileTypeEnum.type, uploadFileTypeEnum);
        }
    }

    public static UploadFileTypeEnum getByCode(Integer code) {
        return codeIndexes.get(code);
    }

    public static final Map<Integer, String> map = new HashMap<Integer, String>();

    public static String getText(Integer num) {
        return map.get(num);
    }

    static {
        for (UploadFileTypeEnum uploadFileTypeEnum : UploadFileTypeEnum.values()) {
            map.put(uploadFileTypeEnum.getType(), uploadFileTypeEnum.getDesc());
        }
    }
}
