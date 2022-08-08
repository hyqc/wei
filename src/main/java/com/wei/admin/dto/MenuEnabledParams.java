package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Data
public class MenuEnabledParams {
    @ApiParam(value = "菜单ID")
    @NotNull(message = "菜单ID不能为空")
    @Range(min = 1, message = "菜单ID错误")
    private Integer menuId;

    @ApiParam(value = "启用状态", required = false)
    @NotNull(message = "菜单状态不能为空")
    private Boolean enabled;
}
