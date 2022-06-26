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
@ApiModel("AccountPasswordEditParams")
public class AccountPasswordEditParams {


    @NotNull(message = "旧密码不能为空")
    @NotBlank(message = "旧密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度为6-18位")
    @Pattern(regexp = PatternConstant.ADMIN_PASSWORD, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    @ApiParam(value = "旧密码", required = true)
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度为6-18位")
    @Pattern(regexp = PatternConstant.ADMIN_PASSWORD, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    @ApiParam(value = "新密码", required = true)
    private String newPassword;

    @NotNull(message = "确认密码不能为空")
    @NotBlank(message = "确认密码不能为空")
    @Length(min = 6, max = 32, message = "确认密码长度为6-18位")
    @ApiParam(value = "密码", required = true)
    private String confirmPassword;

    /**
     * 两次输入的密码必须一致
     *
     * @return
     */
    public Boolean confirmPasswordEqualPassword() {
        return getNewPassword().equals(getConfirmPassword());
    }

    /**
     * 新密码和旧密码不能相同
     *
     * @return
     */
    public Boolean checkUpdatePassword() {
        return getOldPassword().equals(getNewPassword());
    }
}
