package com.wei.admin.bo;

import com.wei.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
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
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyTime;

}
