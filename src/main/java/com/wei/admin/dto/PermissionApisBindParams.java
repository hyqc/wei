package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wlp
 * @date 2022/6/27
 **/
@Data
public class PermissionApisBindParams {
    @ApiParam("权限ID")
    @NotNull(message = "权限ID不能为空")
    @Range(min = 1, message = "权限ID无效")
    private Integer permissionId;

    @ApiParam("接口ID列表")
    @Size(min = 1, message = "请至少选择一项接口资源")
    private List<Integer> apiIds;
}
