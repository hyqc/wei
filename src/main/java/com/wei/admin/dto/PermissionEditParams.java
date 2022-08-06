package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import com.wei.validator.PermissionTypeValidator;
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
public class PermissionEditParams {
    @ApiParam("权限ID")
    @NotNull(message = "权限ID不能为空")
    @Range(min = 1, message = "权限ID无效")
    private Integer id;

    @ApiParam("所属权限ID")
    @NotNull(message = "所属权限ID不能为空")
    @Range(min = 1, message = "所属权限ID无效")
    private Integer menuId;

    @ApiParam("权限键名")
    @NotNull(message = "权限键名不能为空")
    @NotBlank(message = "权限键名不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String key;

    @ApiParam("权限名称")
    @NotNull(message = "权限名称不能为空")
    @NotBlank(message = "权限名称不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam("权限描述")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String describe= "";


    @ApiParam("权限类型")
    @NotNull(message = "权限类型不能为空")
    @PermissionTypeValidator(includeAll = false)
    private String type;

    @ApiParam("启用状态")
    @NotNull(message = "启用状态不能为空")
    private Boolean enabled = false;
}
