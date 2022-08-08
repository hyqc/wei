package com.wei.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("AdminUserDeleteQueryParams")
public class UserDeleteParams {

    @NotNull(message = "无效账号")
    @ApiParam(value = "账号ID", required = true)
    @Range(min = 1, message = "无效账号")
    private Integer adminId;
}
