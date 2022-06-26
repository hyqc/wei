package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Data
public class RoleAddParams {

    @ApiParam(value = "角色名称", required = true)
    @NotNull(message = "角色名称不能为空")
    @NotBlank(message = "角色名称不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam(value = "角色描述", required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String describe;

    @ApiParam(value = "角色启用状态", required = true)
    @NotNull(message = "角色启用状态无效")
    private Boolean enabled;
}
