package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Data
public class ApiDetailParams {
    @ApiParam(value = "接口ID")
    @NotNull(message = "接口ID不能为空")
    @Range(min = 1, message = "接口ID错误")
    private Integer id;
}
