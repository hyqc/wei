package com.wei.admin.service;

import com.wei.admin.bo.ApiListItem;
import com.wei.admin.dao.mysql.AdminApiDao;
import com.wei.admin.dto.*;
import com.wei.admin.po.AdminApiPo;
import com.wei.common.Pager;
import com.wei.common.Result;
import com.wei.core.aop.LogExecutionTime;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author wlp
 * @date 2022/6/25
 **/
@Slf4j
@Service
public class ApiService extends BaseService {
    @Autowired
    private AdminApiDao adminApiDao;

    @LogExecutionTime
    public Result selectAdminApiList(ApiListParams params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<ApiListItem> listItems = adminApiDao.selectAdminApisList(params);
        Pager data = Pager.restPage(listItems);
        return Result.success(data);
    }

    @LogExecutionTime
    public Result addAdminApi(ApiAddParams params) {
        AdminApiPo adminApiPo = new AdminApiPo();
        adminApiPo.setCreateTime(LocalDateTime.now());
        adminApiPo.setModifyTime(adminApiPo.getCreateTime());
        adminApiPo.setPath(params.getPath());
        adminApiPo.setKey(params.getKey());
        adminApiPo.setName(params.getName());
        adminApiPo.setEnabled(params.getEnabled());
        adminApiPo.setDescribe(params.getDescribe() == null ? "" : params.getDescribe());
        try {
            adminApiDao.addAdminApi(adminApiPo);
        } catch (DuplicateKeyException e) {
            if (Objects.requireNonNull(e.getMessage()).contains("uk_name")) {
                return Result.failed("接口名称已存在");
            } else if (e.getMessage().contains("uk_path")) {
                return Result.failed("接口路由已存在");
            } else if (e.getMessage().contains("uk_key")) {
                return Result.failed("接口键名已存在");
            }
            return Result.failed("接口配置已存在");
        }
        return Result.success();
    }

    @LogExecutionTime
    public Result getAdminApiDetail(ApiDetailParams params) {
        AdminApiPo adminApiPo = adminApiDao.findAdminApiById(params.getId());
        if (adminApiPo == null) {
            return Result.failed("接口不存在或已被删除");
        }
        return Result.success(adminApiPo);
    }

    @LogExecutionTime
    public Result editAdminApi(ApiEditParams params) {
        AdminApiPo adminApiPo = new AdminApiPo();
        adminApiPo.setId(params.getId());
        adminApiPo.setModifyTime(LocalDateTime.now());
        adminApiPo.setPath(params.getPath());
        adminApiPo.setKey(params.getKey());
        adminApiPo.setName(params.getName());
        adminApiPo.setEnabled(params.getEnabled());
        adminApiPo.setDescribe(params.getDescribe());
        try {
            Integer total = adminApiDao.editAdminApi(adminApiPo);
            return returnEditResult(total);
        } catch (DuplicateKeyException e) {
            if (Objects.requireNonNull(e.getMessage()).contains("uk_name")) {
                return Result.failed("接口名称已存在");
            }
            if (e.getMessage().contains("uk_path")) {
                return Result.failed("接口路由已存在");
            }
            if (e.getMessage().contains("uk_key")) {
                return Result.failed("接口键名已存在");
            }
            return Result.failed("接口配置已存在");
        }
    }

    @LogExecutionTime
    public Result enableAdminApi(ApiEnabledParams params) {
        AdminApiPo adminApiPo = new AdminApiPo();
        adminApiPo.setId(params.getId());
        adminApiPo.setModifyTime(LocalDateTime.now());
        adminApiPo.setEnabled(params.getEnabled());
        adminApiDao.editAdminApi(adminApiPo);
        return returnEnableResult(params.getEnabled());
    }

    @LogExecutionTime
    public Result deleteAdminApi(ApiDeleteParams params) {
        AdminApiPo adminApiPo = adminApiDao.findAdminApiById(params.getId());
        if (adminApiPo == null) {
            return Result.failed("接口不存在或已被删除");
        }
        if (adminApiPo.getEnabled()) {
            return Result.failed("接口启用状态下不能删除");
        }
        adminApiDao.deleteAdminApi(params.getId());
        return Result.success("删除成功");
    }
}
