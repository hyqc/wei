package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Data
public class ApiAddParams {
    @ApiParam(value = "接口路由", required = true)
    @NotNull(message = "接口路由不能为空")
    @NotBlank(message = "接口路由不能为空")
    @Pattern(regexp = PatternConstant.API_PATH, message = PatternConstant.API_PATH_MESSAGE)
    private String path;

    @ApiParam(value = "接口路由", required = true)
    @NotNull(message = "接口路由键名不能为空")
    @NotBlank(message = "接口路由键名不能为空")
    @Pattern(regexp = PatternConstant.API_KEY, message = PatternConstant.API_KEY_MESSAGE)
    private String key;

    @ApiParam(value = "接口路由", required = true)
    @NotNull(message = "接口名称不能为空")
    @NotBlank(message = "接口名称不能为空")
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam(value = "接口描述", required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String describe;

    @ApiParam(value = "启用状态", required = false)
    private Boolean enabled = false;
}
