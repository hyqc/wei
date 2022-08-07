package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "创建管理员请求参数")
public class UserAddParams {
    @ApiParam(value = "管理员账号", required = true)
    @NotNull(message = "账号不能为空")
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = PatternConstant.ADMIN_USERNAME, message = PatternConstant.ADMIN_USERNAME_MESSAGE)
    private String username;

    @ApiParam(value = "管理员昵称", required = true)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = "首尾不能为空格")
    private String nickname;

    @ApiParam(value = "密码", required = true)
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    @Pattern(regexp = PatternConstant.ADMIN_PASSWORD, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    private String password;

    @ApiParam(value = "账号启用状态", required = true)
    @NotNull(message = "账号启用状态")
    private Boolean enabled;

    @ApiParam(value = "角色ID", required = false)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    private String roleIds;
}
