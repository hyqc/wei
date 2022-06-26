package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Getter
@Setter
@ApiModel("AccountRegisterParams")
public class AccountRegisterParams {

    @NotNull(message = "账号不能为空")
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = PatternConstant.ADMIN_USERNAME, message = PatternConstant.ADMIN_USERNAME_MESSAGE)
    @ApiParam(value = "账号", required = true)
    private String username;

    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度为6-18位")
    @Pattern(regexp = PatternConstant.ADMIN_PASSWORD, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    @ApiParam(value = "密码", required = true)
    private String password;

    @NotNull(message = "确认密码不能为空")
    @NotBlank(message = "确认密码不能为空")
    @Length(min = 6, max = 32, message = "确认密码长度为6-18位")
    @ApiParam(value = "确认密码", required = true)
    private String confirmPassword;

    public Boolean confirmPasswordEqualPassword(){
        return getPassword().equals(getConfirmPassword());
    }
}
