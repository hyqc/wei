package com.wei.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("AdminUserDeleteQueryParams")
public class UserDeleteParams {

    // 要删除的管理员ID集合，英文逗号隔开
    @NotNull(message = "无效账号")
    @NotBlank(message = "无效账号")
    @ApiParam(value = "账号ID集合，英文逗号隔开", required = true)
    private String adminIds;
}
