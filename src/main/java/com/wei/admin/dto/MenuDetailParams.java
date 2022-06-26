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
public class MenuDetailParams {
    @ApiParam("菜单ID")
    @NotNull(message = "无效菜单")
    @Range(min = 1, message = "无效菜单")
    private Integer id;
}
