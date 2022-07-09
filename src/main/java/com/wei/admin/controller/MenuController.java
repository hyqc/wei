package com.wei.admin.controller;

import com.wei.admin.dto.*;
import com.wei.admin.service.MenuService;
import com.wei.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Api(tags = "菜单管理")
@RestController
@RequestMapping(value = "/admin/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "菜单列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('adminMenu::list')")
    public Result list(@Valid @RequestBody MenuListParams params) {
        params.handleListParams();
        return menuService.selectAdminMenuList(params);
    }

    @ApiOperation(value = "全部菜单")
    @PostMapping("/tree")
    @PreAuthorize("hasAuthority('adminMenu::tree')")
    public Result tree() {
        return menuService.selectAllAdminMenu();
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('adminMenu::add')")
    public Result add(@Valid @RequestBody MenuAddParams params) {
        return menuService.addAdminMenu(params);
    }

    @ApiOperation(value = "菜单详情")
    @PostMapping("/detail")
    @PreAuthorize("hasAuthority('adminMenu::detail')")
    public Result detail(@Valid @RequestBody MenuDetailParams params) {
        return menuService.getAdminMenuDetail(params);
    }

    @ApiOperation(value = "菜单编辑")
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('adminMenu::edit')")
    public Result edit(@Valid @RequestBody MenuEditParams params) {
        return menuService.editAdminMenu(params);
    }

    @ApiOperation(value = "启用禁用菜单")
    @PostMapping("/enable")
    @PreAuthorize("hasAuthority('adminMenu::enable')")
    public Result enable(@Valid @RequestBody MenuEnabledParams params) {
        return menuService.enableAdminMenu(params);
    }

    @ApiOperation("删除菜单")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('adminMenu::delete')")
    public Result delete(@Valid @RequestBody MenuDeleteParams params) {
        return menuService.deleteAdminMenu(params);
    }
}
