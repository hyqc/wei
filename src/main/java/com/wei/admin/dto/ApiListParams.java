package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Data
public class ApiListParams extends BaseListParams {
    @ApiParam(value = "权限ID")
    @Range(min = 0, message = "无效权限")
    private Integer permissionId;

    @ApiParam(value = "接口键名")
    private String key;

    @ApiParam(value = "接口名称")
    private String name;

    @ApiParam(value = "接口路由")
    private String path;
}
