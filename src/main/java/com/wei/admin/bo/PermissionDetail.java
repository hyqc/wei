package com.wei.admin.bo;

import com.wei.admin.pe.AdminPermissionTypeEnum;
import com.wei.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Data
public class PermissionDetail {
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限对应的菜单ID
     */
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单路由
     */
    private String menuPath;
    /**
     * 权限唯一标识符
     */
    private String key;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限描述
     */
    private String describe;
    /**
     * 权限类型
     */
    private String type;
    private String typeText;
    /**
     * 重定向地址
     */
    private String redirect;
    /**
     * 是否启用该权限
     */
    private Boolean enabled;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyTime;

    public String getTypeText() {
        return this.typeText;
    }

    public void setTypeText() {
        this.typeText = AdminPermissionTypeEnum.getByValue(this.type).getText();
    }
}
