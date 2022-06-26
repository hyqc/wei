package com.wei.admin.bo;

import com.wei.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Getter
@Setter
public class RoleListItem {
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 创建角色的管理员ID
     */
    private Integer createAdminId;
    /**
     * 创建角色的管理员名称
     */
    private String createAdminName;
    /**
     * 角色是否启用
     */
    private Boolean enabled;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createDateTime;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyDateTime;

}