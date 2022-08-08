package com.wei.admin.controller;

import com.wei.admin.dto.*;
import com.wei.common.Result;
import com.wei.admin.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Administrator
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping(value = "/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("角色列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('adminRole::list')")
    public Result list(@Valid @RequestBody RoleListParams params) {
        switch (params.getSortField()) {
            case "createTime":
                params.setSortField("ar.create_time");
                break;
            case "modifyTime":
                params.setSortField("ar.modify_time");
                break;
            default:
                params.setSortField("ar.id");
        }
        params.handleParams();
        return roleService.selectAdminRolesList(params);
    }

    @ApiOperation("有效角色列表")
    @PostMapping("/all")
    @PreAuthorize("hasAuthority('adminRole::all')")
    public Result all(@Valid @RequestBody RoleListParams params) {
        return roleService.selectAdminRolesAll(params);
    }

    @ApiOperation("创建角色")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('adminRole::add')")
    public Result add(@Valid @RequestBody RoleAddParams params) {
        return roleService.addAdminRole(params);
    }

    @ApiOperation("角色详情")
    @PostMapping("/detail")
    @PreAuthorize("hasAuthority('adminRole::detail')")
    public Result detail(@Valid @RequestBody RoleDetailParams params) {
        return roleService.getAdminRoleDetail(params);
    }

    @ApiOperation("编辑角色")
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('adminRole::edit')")
    public Result edit(@Valid @RequestBody RoleEditParams params) {
        return roleService.editAdminRole(params);
    }


    @ApiOperation("启用禁用角色")
    @PostMapping("/enable")
    @PreAuthorize("hasAuthority('adminRole::enable')")
    public Result enable(@Valid @RequestBody RoleUpdateIsEnabledParams params) {
        return roleService.updateAdminRoleIsEnabled(params);
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('adminRole::delete')")
    public Result delete(@Valid @RequestBody RoleDeleteParams params) {
        return roleService.deleteRole(params);
    }

    @ApiOperation("角色绑定权限")
    @PostMapping("/bindPermissions")
    @PreAuthorize("hasAuthority('adminRole::bindPermissions')")
    public Result bindPermissions(@Valid @RequestBody RoleBindPermissionsParams params) {
        return roleService.bindRolePermissions(params);
    }

    @ApiOperation("角色权限列表")
    @PostMapping("/permissions")
    @PreAuthorize("hasAuthority('adminRole::permissions')")
    public Result permissions(@Valid @RequestBody RolePermissionsParams params) {
        return roleService.selectAdminRolePermissions(params);
    }

}
