package com.wei.admin.pe;

/**
 * @author wlp
 * @date 2022/6/19
 **/
public enum AdminUserIsEnabledEnum {
    /**
     * 启用
     */
    YES(1,"启用"),
    /**
     * 禁用
     */
    NO(0,"禁用")
    ;
    private Integer value;
    private String text;
    AdminUserIsEnabledEnum(Integer value, String text){
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
