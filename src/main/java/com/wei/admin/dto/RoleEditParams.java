package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Data
public class RoleEditParams {
    @ApiParam(value = "无效角色", required = true)
    @NotNull(message = "无效角色")
    @Range(min = 1, message = "无效角色")
    private Integer id;

    @ApiParam(value = "角色名称", required = false)
    @NotBlank(message = "角色名称不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam(value = "角色描述", required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String describe;

    @ApiParam(value = "角色状态", required = true)
    @NotNull(message = "角色状态无效")
    private Boolean enabled;
}
