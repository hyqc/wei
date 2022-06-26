package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Data
public class RoleUpdateIsEnabledParams {
    @ApiParam(value = "角色ID", required = true)
    @NotNull(message = "无效角色")
    @Range(min = 1, message = "无效角色")
    private Integer roleId;

    @ApiParam(value = "角色启用状态", required = true)
    @NotNull(message = "无效状态")
    private Boolean enabled;
}
