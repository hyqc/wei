package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import com.wei.validator.PermissionTypeValidator;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * @author wlp
 * @date 2022/6/27
 **/
@Data
public class PermissionListParams extends BaseListParams {

    @ApiParam("权限键名")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String key;

    @ApiParam("权限名称")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam("权限类型")
    @PermissionTypeValidator(includeAll = true)
    private String type;

    @ApiParam("菜单ID")
    @Range(min = 0, message = "无效菜单ID")
    private Integer menuId;
}
