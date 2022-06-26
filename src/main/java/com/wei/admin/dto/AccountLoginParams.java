package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Data
@ApiModel("AccountLoginParams")
public class AccountLoginParams {
    @ApiParam(value = "管理员名称", required = true)
    @NotNull(message = "管理员名称不能为空")
    @NotBlank(message = "管理员名称不能为空")
    @Pattern(regexp = PatternConstant.ADMIN_USERNAME, message = PatternConstant.ADMIN_USERNAME_MESSAGE)
    private String username;

    @ApiParam(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度为6-32位")
    @Pattern(regexp = PatternConstant.ADMIN_PASSWORD, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    private String password;
}
