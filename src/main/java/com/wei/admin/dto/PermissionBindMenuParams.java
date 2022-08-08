package com.wei.admin.dto;

import com.wei.admin.po.AdminPermissionPo;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wlp
 * @date 2022/8/6
 **/
@Data
public class PermissionBindMenuParams {
    @ApiParam("菜单ID")
    @NotNull(message = "无效菜单")
    @Range(min = 1, message = "无效菜单")
    private Integer menuId;

    @ApiParam("菜单对应的权限信息")
    @NotNull(message = "权限配置不能为空")
    private List<AdminPermissionPo> permissions;
}
