package com.wei.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Data
@ApiModel("管理员详情")
public class UserDetailParams {
    @ApiParam(value = "管理员ID", required = true)
    @NotNull(message = "无效账号")
    @Range(min = 1, message = "无效账号")
    private Integer adminId;
}
