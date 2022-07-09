package com.wei.admin.controller;

import com.wei.admin.dto.*;
import com.wei.admin.service.UserService;
import com.wei.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Administrator
 */
@Api(tags = "个人中心")
@RestController
@RequestMapping(value = "/admin/account")
public class AccountController extends BaseController {
    @Autowired
    private UserService userService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody AccountRegisterParams params) {
        return userService.register(params);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@Valid @RequestBody AccountLoginParams params, HttpServletRequest request) {
        return userService.login(params, request);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }

    @ApiOperation("个人中心详情")
    @PostMapping("/detail")
    public Result detail(@RequestParam(value = "refreshToken", defaultValue = "false", required = false) Boolean refreshToken) {
        return userService.getAccountDetail(refreshToken);
    }

    @ApiOperation("修改信息")
    @PostMapping("/edit")
    public Result edit(@Valid @RequestBody AccountEditParams params) {
        return userService.editAccountDetail(params);
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(@Valid @RequestBody AccountPasswordEditParams params) {
        return userService.updateAccountPassword(params);
    }

    @ApiOperation("菜单")
    @PostMapping("/menu")
    public Result menu() {
        return userService.getAccountMenus();
    }

    @ApiOperation("个人权限")
    @PostMapping("/permission")
    public Result permission(@Valid @RequestBody AccountPermissionParams params) {
        return userService.getAccountPermission(params);
    }
}
