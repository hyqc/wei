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
@Api(tags = "账号角色管理")
@RestController
@RequestMapping(value = "/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("角色列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('adminRole::list')")
    @ResponseBody
    public Result list(@Valid @RequestBody RoleListParams params) {
        params.handleListParams();
        return roleService.selectAdminRolesList(params);
    }

    @ApiOperation("添加角色")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('adminRole::add')")
    @ResponseBody
    public Result add(@Valid @RequestBody RoleAddParams params) {
        return roleService.addAdminRole(params);
    }

    @ApiOperation("角色详情")
    @PostMapping("/detail")
    @PreAuthorize("hasAuthority('adminRole::detail')")
    @ResponseBody
    public Result detail(@Valid @RequestBody RoleDetailParams params) {
        return roleService.getAdminRoleDetail(params);
    }

    @ApiOperation("角色编辑")
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('adminRole::edit')")
    @ResponseBody
    public Result edit(@Valid @RequestBody RoleEditParams params) {
        return roleService.editAdminRole(params);
    }


    @ApiOperation("更新角色启用状态")
    @PostMapping("/enable")
    @PreAuthorize("hasAuthority('adminRole::enable')")
    @ResponseBody
    public Result enable(@Valid @RequestBody RoleUpdateIsEnabledParams params) {
        return roleService.updateAdminRoleIsEnabled(params);
    }

    @ApiOperation("角色绑定权限")
    @PostMapping("/bind")
    @PreAuthorize("hasAuthority('adminRole::bind')")
    @ResponseBody
    public Result bind(@Valid @RequestBody RoleAssignParams params) {
        return roleService.bindRolePermissions(params);
    }

    @ApiOperation("角色权限列表")
    @PostMapping("/permission")
    @PreAuthorize("hasAuthority('adminRole::permission')")
    @ResponseBody
    public Result permission(@Valid @RequestBody RolePermissionParams params) {
        return roleService.selectAdminRolePermissions(params);
    }
}
