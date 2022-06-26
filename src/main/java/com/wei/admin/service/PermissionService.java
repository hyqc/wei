package com.wei.admin.service;

import com.wei.admin.bo.PermissionDetail;
import com.wei.admin.dao.mysql.AdminApiDao;
import com.wei.admin.dao.mysql.AdminPermissionDao;
import com.wei.admin.dto.*;
import com.wei.admin.po.AdminApiPo;
import com.wei.admin.po.AdminPermissionPo;
import com.wei.common.Pager;
import com.wei.common.Result;
import com.wei.core.aop.LogExecutionTime;
import com.wei.core.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Slf4j
@Service
public class PermissionService extends BaseService {
    @Autowired
    private AdminPermissionDao adminPermissionDao;

    @Autowired
    private AdminApiDao adminApiDao;

    @LogExecutionTime
    public Result selectAdminPermissionList(PermissionListParams params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<PermissionDetail> data = adminPermissionDao.selectPermissionsList(params);
        data.forEach(PermissionDetail::setTypeText);
        Pager result = Pager.restPage(data);
        return Result.success(result);
    }

    @LogExecutionTime
    public Result addAdminPermission(PermissionAddParams params) {
        AdminPermissionPo adminPermissionPo = new AdminPermissionPo();
        BeanUtils.copyProperties(params, adminPermissionPo);
        adminPermissionPo.setCreateTime(LocalDateTime.now());
        adminPermissionPo.setModifyTime(adminPermissionPo.getCreateTime());
        try {
            adminPermissionDao.addAdminPermission(adminPermissionPo);
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("uk_key")) {
                return Result.failed("权限键名已存在");
            }
            if (e.getMessage().contains("uk_permission")) {
                return Result.failed("该菜单下权限已存在");
            }
            return Result.failed("添加权限失败");
        }
        return Result.success("添加权限成功");
    }

    @LogExecutionTime
    public Result getAdminPermissionDetail(PermissionDetailParams params) {
        PermissionDetail permissionDetail = adminPermissionDao.findAdminPermissionDetailById(params.getId());
        if (permissionDetail == null) {
            return Result.failed("权限不存在或已被删除");
        }
        permissionDetail.setTypeText();
        return Result.success(permissionDetail);
    }

    @LogExecutionTime
    public Result editAdminPermission(PermissionEditParams params) {
        PermissionDetail permissionDetail = adminPermissionDao.findAdminPermissionDetailById(params.getId());
        if (permissionDetail == null) {
            return Result.failed("权限不存在或已被删除");
        }
        AdminPermissionPo adminPermissionPo = new AdminPermissionPo();
        BeanUtils.copyProperties(params, adminPermissionPo);
        adminPermissionPo.setModifyTime(LocalDateTime.now());
        try {
            Integer res = adminPermissionDao.editAdminPermission(adminPermissionPo);
            return returnEditResult(res);
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("uk_key")) {
                return Result.failed("权限键名已存在");
            }
            if (e.getMessage().contains("uk_permission")) {
                return Result.failed("该菜单下权限已存在");
            }
            return Result.failed("权限失败");
        }
    }

    @LogExecutionTime
    public Result enableAdminPermission(PermissionEnabledParams params) {
        AdminPermissionPo adminPermissionPo = new AdminPermissionPo();
        adminPermissionPo.setModifyTime(LocalDateTime.now());
        adminPermissionPo.setId(params.getId());
        adminPermissionPo.setEnabled(params.getEnabled());
        adminPermissionDao.editAdminPermission(adminPermissionPo);
        return returnEnableResult(params.getEnabled());
    }

    @LogExecutionTime
    public Result deleteAdminPermission(PermissionDeleteParams params) {
        PermissionDetail permissionDetail = adminPermissionDao.findAdminPermissionDetailById(params.getId());
        if (permissionDetail == null) {
            return Result.failed("权限不存在或已被删除");
        }
        if (permissionDetail.getEnabled()) {
            return Result.failed("权限启用状态下不能删除");
        }
        adminPermissionDao.deleteAdminPermission(params.getId());
        return Result.success("删除成功");
    }

    @LogExecutionTime
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result bindAdminApis(PermissionApisBindParams params) {
        List<Integer> apiIds = params.getApiIds().stream().filter(id -> id > 0).distinct().collect(Collectors.toList());
        if (apiIds.size() == 0) {
            return Result.failed("没有有效的接口资源");
        }
        PermissionDetail permissionDetail = adminPermissionDao.findAdminPermissionDetailById(params.getPermissionId());
        if (permissionDetail == null) {
            return Result.failed("权限不存在或已被删除");
        }
        // 查询接口是否还存在
        List<AdminApiPo> adminApiPos = adminApiDao.selectAdminApiAllByIds(apiIds, true);
        if (adminApiPos == null || adminApiPos.size() == 0) {
            return Result.failed("没有有效的接口资源");
        }
        adminPermissionDao.deleteAdminPermissionApis(params.getPermissionId());
        try {
            adminPermissionDao.addPermissionApis(params.getPermissionId(), params.getApiIds());
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            if (e.getMessage().contains("uk_permission_api")) {
                throw new BusinessException("已存在对应关系");
            }
            throw new BusinessException("绑定接口资源失败");
        }
        return Result.success("绑定接口资源成功");
    }
}
