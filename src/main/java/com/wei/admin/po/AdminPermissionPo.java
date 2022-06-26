package com.wei.admin.po;

import com.wei.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 管理后台权限表
 *
 * @author wlp
 */
@Getter
@Setter
public class AdminPermissionPo {
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限对应的菜单ID
     */
    private Integer menuId;
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
}
