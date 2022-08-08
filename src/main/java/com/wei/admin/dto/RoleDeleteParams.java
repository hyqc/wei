package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author wlp
 * @date 2022/8/7
 **/
@Data
public class RoleDeleteParams {
    @ApiParam(value = "角色ID", required = true)
    @NotNull(message = "无效角色")
    @Range(min = 1, message = "无效角色")
    private Integer id;
}
