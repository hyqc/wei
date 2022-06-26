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
public class PermissionDetailParams {
    @ApiParam("权限ID")
    @NotNull(message = "权限ID不能为空")
    @Range(min = 1, message = "权限ID无效")
    private Integer id;
}
