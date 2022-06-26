package com.wei.admin.controller;

import com.wei.admin.dto.*;
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
@Api(tags = "账号管理")
@RestController
@RequestMapping(value = "/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation("账号列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('adminUser::list')")
    public Result list(@Valid @RequestBody UserListParams params) {
        params.handleListParams();
        return userService.selectAdminUserList(params);
    }

    @ApiOperation("账号详情")
    @PostMapping("/detail")
    @PreAuthorize("hasAuthority('adminUser::detail')")
    @ResponseBody
    public Result detail(@Valid @RequestBody UserDetailParams params) {
        return userService.getAdminUserDetail(params);
    }

    @ApiOperation("添加账号")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('adminUser::add')")
    @ResponseBody
    public Result add(@Valid @RequestBody UserAddParams params) {
        return userService.addAdminUser(params);
    }

    @ApiOperation("账号编辑")
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('adminUser::edit')")
    @ResponseBody
    public Result edit(@Valid @RequestBody UserEditParams params) {
        return userService.editAdminUser(params);
    }

    @ApiOperation("账号状态更改")
    @PostMapping("/enable")
    @PreAuthorize("hasAuthority('adminUser::enable')")
    @ResponseBody
    public Result enable(@Valid @RequestBody UserUpdateEnabledParams params) {
        return userService.updateAdminUserIsEnabled(params);
    }

    @ApiOperation("账号的角色列表")
    @PostMapping("/role")
    @PreAuthorize("hasAuthority('adminUser::role')")
    @ResponseBody
    public Result role(@Valid @RequestBody UserRoleDetailParams params) {
        return userService.selectAdminUserRoles(params);
    }

    @ApiOperation("账号添加角色")
    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('adminUser::assign')")
    @ResponseBody
    public Result assign(@Valid @RequestBody UserAssignParams params) {
        return userService.assignAdminUserRoles(params);
    }
}