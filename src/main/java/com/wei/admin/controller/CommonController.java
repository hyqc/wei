package com.wei.admin.controller;

import com.wei.admin.dto.CommonUploadFileParam;
import com.wei.admin.service.CommonService;
import com.wei.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(tags = "公共配置方法")
@Validated
@RestController
@RequestMapping(value = "/admin/common")
public class CommonController extends BaseController {

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "上传")
    @PostMapping(value = "/upload")
    public Result upload(@Valid CommonUploadFileParam param, @RequestParam("file") MultipartFile multipartFile) {
        Map<String, Object> data = commonService.upload(param, multipartFile);
        return Result.success(data);
    }
}
