package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Data
public class MenuEditParams {
    @ApiParam("菜单ID")
    @NotNull(message = "无效菜单")
    @Range(min = 1, message = "无效菜单")
    private Integer id;

    @ApiParam("父级菜单ID")
    @Range(min = 0, message = "无效父级菜单")
    private Integer parentId;

    @ApiParam("菜单路由")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String path;

    @ApiParam("菜单键名")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String key;

    @ApiParam("菜单名称")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam("菜单描述")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String describe;

    @ApiParam("菜单重定向路由")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String redirect;

    @ApiParam("菜单图标")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String icon;

    @ApiParam("是否隐藏子菜单")
    private Boolean hideChildrenInMenu;

    @ApiParam("是否隐藏菜单")
    private Boolean hideInMenu;

    @ApiParam("是否启用菜单")
    private Boolean enabled;

    @ApiParam("菜单排序值")
    @Range(min = 0, message = "排序值不能小于0")
    private Integer sort;
}
