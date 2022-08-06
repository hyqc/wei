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
    Integer id;
    /**
     * 菜单唯一键
     */
    String key;
    /**
     * 菜单名称
     */
    String name;
    /**
     * 父级菜单ID
     */
    Integer parentId;
    /**
     * 菜单描述
     */
    String describe;
    /**
     * 菜单路径
     */
    String path;
    /**
     * 重定向地址
     */
    String redirect;
    /**
     * 组件名称
     */
    String component;

    /**
     * 菜单排序
     */
    Integer sort;

    /**
     * 菜单图标
     */
    String icon;
    /**
     * 是否在菜单中隐藏子菜单
     */
    Boolean hideChildrenInMenu;
    /**
     * 是否隐藏菜单
     */
    Boolean hideInMenu;

    /**
     * 是否启用
     */
    Boolean enabled;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    LocalDateTime createTime;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    LocalDateTime modifyTime;
}
