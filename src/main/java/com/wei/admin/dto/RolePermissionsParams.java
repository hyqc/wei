package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Data
public class RolePermissionsParams {
    @ApiParam(value = "角色ID", required = true)
    @NotNull(message = "角色ID不能为空")
    @Range(min = 1, message = "角色ID不能为空")
    private Integer id;
}
