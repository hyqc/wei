package com.wei.admin.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wlp
 */
@Data
public class AdminRolePo {
    /**
     * 自增角色ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述
     */
    private String describe;
    /**
     * 创建角色的管理员ID
     */
    private Integer createAdminId;
    /**
     * 修改角色的管理员ID
     */
    private Integer modifyAdminId;
    /**
     * 是否启用
     */
    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;
}
