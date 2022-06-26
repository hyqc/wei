package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Data;

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
    @Pattern(regexp = PatternConstant.PERMISSION_TYPE, message = PatternConstant.PERMISSION_TYPE_MESSAGE)
    private String type;
}
