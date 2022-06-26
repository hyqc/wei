package com.wei.admin.dto;

import com.wei.constant.PatternConstant;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Pattern;

/**
 * @author Administrator
 */
@Getter
@Setter
public class AccountEditParams {

    @Pattern(regexp = PatternConstant.TRIM_BLANK_STRING, message = PatternConstant.TRIM_BLANK_STRING_MESSAGE)
    @Length(max = 50, message = "姓名长度不能超过50个字符")
    @ApiParam(value = "姓名", required = false)
    private String nickname;

    @URL(message = "无效链接地址")
    @ApiParam(value = "头像", required = false)
    private String avatar;
}
