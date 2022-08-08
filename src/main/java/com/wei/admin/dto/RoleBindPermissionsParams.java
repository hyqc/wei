package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Data
public class RoleBindPermissionsParams {
    @ApiParam(value = "角色ID", required = true)
    @NotNull(message = "角色ID不能为空")
    @Range(min = 1, message = "角色ID不能为空")
    private Integer id;

    @ApiParam(value = "权限ID集合")
    @NotNull(message = "请至少选择一项权限")
    @Size(min = 1, message = "请至少选择一项权限")
    private List<Integer> permissionIds;
}
