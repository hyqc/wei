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
public class ApiEditParams {
    @ApiParam(value = "接口ID")
    @NotNull(message = "接口ID不能为空")
    @Range(min = 1, message = "接口ID错误")
    private Integer id;

    @ApiParam(value = "接口路由")
    @NotNull(message = "接口路由不能为空")
    @NotBlank(message = "接口路由不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String path;

    @ApiParam(value = "接口键名")
    @NotNull(message = "接口键名不能为空")
    @NotBlank(message = "接口键名不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String key;

    @ApiParam(value = "接口名称")
    @NotNull(message = "接口名称不能为空")
    @NotBlank(message = "接口名称不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam(value = "接口描述", required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String describe;

    @ApiParam(value = "启用状态", required = false)
    @NotNull(message = "接口状态不能为空")
    private Boolean enabled;
}
