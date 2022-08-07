package com.wei.admin.controller;

import com.wei.admin.dto.*;
import com.wei.admin.service.ApiService;
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
@Api(tags = "接口管理")
@RestController
@RequestMapping(value = "/admin/api")
public class ApiController {
    @Autowired
    private ApiService apiService;

    @ApiOperation("接口列表")
    @PostMapping("/list")
    // @PreAuthorize("hasAuthority('adminApi::list')")
    public Result list(@Valid @RequestBody ApiListParams params) {
        return apiService.selectAdminApiList(params);
    }

    @ApiOperation("有效接口列表")
    @PostMapping("/all")
    // @PreAuthorize("hasAuthority('adminApi::all')")
    public Result all() {
        return apiService.selectAdminApiAll();
    }

    @ApiOperation("创建接口")
    @PostMapping("/add")
    // @PreAuthorize("hasAuthority('adminApi::add')")
    public Result add(@Valid @RequestBody ApiAddParams params) {
        return apiService.addAdminApi(params);
    }

    @ApiOperation("接口详情")
    @PostMapping("/detail")
    // @PreAuthorize("hasAuthority('adminApi::detail')")
    public Result detail(@Valid @RequestBody ApiDetailParams params) {
        return apiService.getAdminApiDetail(params);
    }

    @ApiOperation("编辑接口")
    @PostMapping("/edit")
    // @PreAuthorize("hasAuthority('adminApi::edit')")
    public Result edit(@Valid @RequestBody ApiEditParams params) {
        return apiService.editAdminApi(params);
    }

    @ApiOperation("启用禁用接口")
    @PostMapping("/enable")
    // @PreAuthorize("hasAuthority('adminApi::enable')")
    public Result enable(@Valid @RequestBody ApiEnabledParams params) {
        return apiService.enableAdminApi(params);
    }

    @ApiOperation("删除接口")
    @PostMapping("/delete")
    // @PreAuthorize("hasAuthority('adminApi::delete')")
    public Result delete(@Valid @RequestBody ApiDeleteParams params) {
        return apiService.deleteAdminApi(params);
    }
}
