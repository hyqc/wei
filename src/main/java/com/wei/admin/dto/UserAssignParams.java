package com.wei.admin.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class UserAssignParams {
    @ApiParam(value = "账号ID")
    @NotNull(message = "无效账号")
    @Range(min = 2, message = "无效账号")
    private Integer adminId;

    @ApiParam(value = "角色ID列表")
    @NotNull(message = "请至少选择一个角色")
    @Size(min = 1, message = "请至少选择一个角色")
    private List<Integer> roleIds;
}
