package com.wei.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wlp
 * @date 2022/6/24
 **/
@Api(tags = "管理后台接口")
@Validated
@RestController
@RequestMapping(value = "/admin")
public class BaseController {

}
