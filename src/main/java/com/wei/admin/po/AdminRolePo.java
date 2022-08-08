package com.wei.admin.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wei.util.DateTimeUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyTime;
}
