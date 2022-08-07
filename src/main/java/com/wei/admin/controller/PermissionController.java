package com.wei.admin.controller;

import com.wei.admin.dto.*;
import com.wei.admin.service.PermissionService;
import com.wei.admin.service.UserService;
import com.wei.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Administrator
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping(value = "/admin/permission")
public class PermissionController extends BaseController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "权限列表")
    @PostMapping("/list")
    // @PreAuthorize("hasAuthority('adminPermission::list')")
    public Result list(@Valid @RequestBody PermissionListParams params) {
        return permissionService.selectAdminPermissionList(params);
    }

    @ApiOperation(value = "创建权限")
    @PostMapping("/add")
    // @PreAuthorize("hasAuthority('adminPermission::add')")
    public Result add(@Valid @RequestBody PermissionAddParams params) {
        return permissionService.addAdminPermission(params);
    }

    @ApiOperation(value = "权限详情")
    @PostMapping("/detail")
    // @PreAuthorize("hasAuthority('adminPermission::detail')")
    public Result detail(@Valid @RequestBody PermissionDetailParams params) {
        return permissionService.getAdminPermissionDetail(params);
    }

    @ApiOperation(value = "编辑权限")
    @PostMapping("/edit")
    // @PreAuthorize("hasAuthority('adminPermission::edit')")
    public Result edit(@Valid @RequestBody PermissionEditParams params) {
        return permissionService.editAdminPermission(params);
    }

    @ApiOperation(value = "启用禁用权限")
    @PostMapping("/enable")
    @PreAuthorize("hasAuthority('adminPermission::enable')")
    public Result enable(@Valid @RequestBody PermissionEnabledParams params) {
        return permissionService.enableAdminPermission(params);
    }

    @ApiOperation("删除权限")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('adminPermission::delete')")
    public Result delete(@Valid @RequestBody PermissionDeleteParams params) {
        return permissionService.deleteAdminPermission(params);
    }

    @ApiOperation("权限绑定接口")
    @PostMapping("/bindApis")
    // @PreAuthorize("hasAuthority('adminPermission::bindApis')")
    public Result bindApis(@Valid @RequestBody PermissionApisBindParams params) {
        return permissionService.bindAdminApis(params);
    }

    @ApiOperation("指定菜单创建查看编辑删除权限")
    @PostMapping("/addMenuPermissions")
    // @PreAuthorize("hasAuthority('adminPermission::addMenuPermissions')")
    public Result addMenuPermissions(@Valid @RequestBody PermissionBindMenuParams params) {
        return permissionService.addMenuPermissions(params);
    }
}
