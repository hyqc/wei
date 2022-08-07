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
    // @PreAuthorize("hasAuthority('adminUser::list')")
    public Result list(@Valid @RequestBody UserListParams params) {
        switch (params.getSortField()) {
            case "createTime":
                params.setSortField("c.`create_time`");
                break;
            case "modifyTime":
                params.setSortField("c.`modify_time`");
                break;
            case "loginTotal":
                params.setSortField("c.`login_total`");
                break;
            case "lastLoginTime":
                params.setSortField("c.`last_login_time`");
                break;
            case "username":
                params.setSortField("c.`username`");
                break;
            default:
                params.setSortField("c.`id`");
        }
        params.handleParams();
        return userService.selectAdminUserList(params);
    }

    @ApiOperation("账号详情")
    @PostMapping("/detail")
    // @PreAuthorize("hasAuthority('adminUser::detail')")
    public Result detail(@Valid @RequestBody UserDetailParams params) {
        return userService.getAdminUserDetail(params);
    }

    @ApiOperation("添加账号")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('adminUser::add')")
    public Result add(@Valid @RequestBody UserAddParams params) {
        return userService.addAdminUser(params);
    }

    @ApiOperation("账号编辑")
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('adminUser::edit')")
    public Result edit(@Valid @RequestBody UserEditParams params) {
        return userService.editAdminUser(params);
    }

    @ApiOperation("账号状态更改")
    @PostMapping("/enable")
    @PreAuthorize("hasAuthority('adminUser::enable')")
    public Result enable(@Valid @RequestBody UserUpdateEnabledParams params) {
        return userService.enableAdminUser(params);
    }

    @ApiOperation("账号绑定角色")
    @PostMapping("/bindRoles")
    @PreAuthorize("hasAuthority('adminUser::bindRoles')")
    public Result bindRoles(@Valid @RequestBody UserBindRolesParams params) {
        return userService.bindAdminRoles(params);
    }
}
