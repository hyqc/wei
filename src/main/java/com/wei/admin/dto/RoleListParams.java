package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Data
public class RoleListParams extends BaseListParams {

    @ApiParam(value = "角色ID", required = false)
    @Range(min = 1, message = "无效角色")
    private Integer id;

    @ApiParam(value = "角色名称", required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String name;

    @ApiParam(value = "启用状态，0：全部，1：启用，2：禁用", required = false)
    @Range(min = 0, max = 2, message = "无效角色启用状态")
    private Integer enabled;
}
