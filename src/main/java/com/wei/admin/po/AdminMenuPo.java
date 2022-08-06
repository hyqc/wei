package com.wei.admin.po;

import com.wei.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 菜单
 *
 * @author wlp
 */
@Data
public class AdminMenuPo {
    /**
     * 菜单自增ID
     */
    protected Integer id;
    /**
     * 菜单唯一键
     */
    protected String key;
    /**
     * 菜单名称
     */
    protected String name;
    /**
     * 父级菜单ID
     */
    protected Integer parentId;
    /**
     * 菜单描述
     */
    protected String describe;
    /**
     * 菜单路径
     */
    protected String path;
    /**
     * 重定向地址
     */
    protected String redirect;
    /**
     * 组件名称
     */
    protected String component;

    /**
     * 菜单排序
     */
    protected Integer sort;

    /**
     * 菜单图标
     */
    protected String icon;
    /**
     * 是否在菜单中隐藏子菜单
     */
    protected Boolean hideChildrenInMenu;
    /**
     * 是否隐藏菜单
     */
    protected Boolean hideInMenu;

    /**
     * 是否启用
     */
    protected Boolean enabled;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    protected LocalDateTime createTime;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    protected LocalDateTime modifyTime;
}
