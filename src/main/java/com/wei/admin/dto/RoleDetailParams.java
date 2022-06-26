package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Getter
@Setter
public class RoleDetailParams {

    @ApiParam(value = "角色ID", required = true)
    @NotNull(message = "无效角色")
    @Range(min = 1, message = "无效角色")
    private Integer roleId;
}
