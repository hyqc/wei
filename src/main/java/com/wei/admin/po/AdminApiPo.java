package com.wei.admin.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wei.util.DateTimeUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 管理后台接口表
 * @author wlp
 */
@Data
public class AdminApiPo {
    /**
     * 自增记录ID
     */
    private Integer id;
    /**
     * 权限ID
     */
    private Integer permissionId;
    /**
     * 接口路由路径
     */
    private String path;
    /**
     * 接口路由路径唯一键：首路由::小驼峰方法名，示例：adminUser::list
     */
    private String key;
    /**
     * 接口名称
     */
    private String name;
    /**
     * 接口描述
     */
    private String describe;
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
