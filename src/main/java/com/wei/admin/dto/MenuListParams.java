package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Data
public class MenuListParams extends BaseListParams{
    @ApiParam(value = "菜单键名",required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String key;

    @ApiParam(value = "菜单名称",required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam(value = "菜单路由",required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String path;

    @ApiParam(value = "父级菜单",required = false)
    @Range(min = 0, message = "无效父级菜单ID")
    private Integer parentId = 0;
}
