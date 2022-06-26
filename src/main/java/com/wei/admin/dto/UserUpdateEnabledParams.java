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
@ApiModel("启用禁用")
public class UserUpdateEnabledParams {

    @ApiParam(value = "管理员ID", required = true)
    @NotNull(message = "无效账号")
    @Range(min = 2, message = "无效账号")
    private Integer adminId;

    @ApiParam(value = "管理员启用状态", required = true)
    @NotNull(message = "无效启用状态")
    private Boolean enabled;
}
