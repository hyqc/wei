package com.wei.admin.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel("获取管理员的角色列表")
public class UserRoleDetailParams {

    @ApiParam(value = "管理员ID", required = true)
    @NotNull(message = "账号ID不能为空")
    @Range(min = 2, message = "无效账号ID")
    private Integer adminId;
}
