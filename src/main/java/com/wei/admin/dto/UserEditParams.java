package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Data
@ApiModel("编辑管理员")
public class UserEditParams {
    @ApiParam(value = "管理员ID",required = true)
    @NotNull(message = "无效账号")
    @Range(min = 2, message = "无效账号")
    private Integer adminId;

    @ApiParam(value = "管理员账号", required = true)
    @Pattern(regexp = PatternConstant.ADMIN_USERNAME, message = PatternConstant.ADMIN_USERNAME_MESSAGE)
    private String username;

    @ApiParam(value = "管理员昵称", required = true)
    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = "首尾不能为空格")
    private String nickname;

    @ApiParam(value = "密码", required = true)
    @Length(min = 6, max = 32, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    @Pattern(regexp = PatternConstant.ADMIN_PASSWORD, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    private String password;

    @ApiParam(value = "确认密码", required = true)
    @Length(min = 6, max = 32, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    @Pattern(regexp = PatternConstant.ADMIN_PASSWORD, message = PatternConstant.ADMIN_PASSWORD_MESSAGE)
    private String confirmPassword;

    @ApiParam(value = "账号启用状态", required = true)
    private Boolean enabled;

    @ApiParam(value = "邮箱", required = false)
    @Email(message = "邮箱歌手错误")
    private String email;

    @ApiParam(value = "头像地址", required = false)
    @URL(message = "头像地址格式错误")
    private String avatar;
}
