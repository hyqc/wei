package com.wei.admin.service;

import com.wei.admin.bo.ApiListItem;
import com.wei.admin.bo.PermissionDetail;
import com.wei.admin.dao.mysql.AdminApiDao;
import com.wei.admin.dao.mysql.AdminPermissionDao;
import com.wei.admin.dto.*;
import com.wei.admin.pe.AdminPermissionTypeEnum;
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
import java.util.*;
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
        Set<Integer> permissionIds = data.stream().map(PermissionDetail::getId).collect(Collectors.toSet());
        Map<Integer, List<ApiListItem>> apisMap = new HashMap<>();
        if (permissionIds.size() > 0) {
            List<ApiListItem> apiListItems = adminApiDao.selectApisByPermissionIds(permissionIds);
            apisMap = apiListItems.stream().collect(Collectors.groupingBy(ApiListItem::getPermissionId));
        }
        Map<Integer, List<ApiListItem>> finalApisMap = apisMap;
        data.forEach(item -> {
            item.setTypeText();
            item.setApis(finalApisMap.containsKey(item.getId()) ? finalApisMap.get(item.getId()) : new ArrayList<>());
        });
        Pager<PermissionDetail> result = Pager.restPage(data);
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
        PermissionDetail detail = adminPermissionDao.findAdminPermissionDetailById(params.getId());
        if (detail == null) {
            return Result.failed("权限不存在或已被删除");
        }
        List<ApiListItem> apiListItems = adminApiDao.selectApisByPermissionIds(new HashSet<Integer>() {{
            add(detail.getId());
        }});
        detail.setApis(apiListItems);
        detail.setTypeText();
        return Result.success(detail);
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
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result deleteAdminPermission(PermissionDeleteParams params) {
        PermissionDetail permissionDetail = adminPermissionDao.findAdminPermissionDetailById(params.getId());
        if (permissionDetail == null) {
            return Result.failed("权限不存在或已被删除");
        }
        if (permissionDetail.getEnabled()) {
            return Result.failed("权限启用状态下不能删除");
        }
        adminPermissionDao.deleteAdminPermission(params.getId());
        adminPermissionDao.deleteAdminPermissionApis(params.getId());
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

    @LogExecutionTime
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result addMenuPermissions(PermissionBindMenuParams params) {
        LocalDateTime now = LocalDateTime.now();
        params.getPermissions().forEach(item -> {
            item.setEnabled(item.getEnabled() != null && item.getEnabled());
            item.setMenuId(params.getMenuId());
            item.setCreateTime(now);
            if (item.getKey() == null || "".equals(item.getKey())) {
                throw new BusinessException("权限键名不能为空");
            }
            if (item.getName() == null || "".equals(item.getName())) {
                throw new BusinessException("权限名称不能为空");
            }
            if (AdminPermissionTypeEnum.getByValue(item.getType()) == null) {
                throw new BusinessException("权限类型错误");
            }
        });
        try {
            adminPermissionDao.batchAddMenuPermissions(params.getPermissions());
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("uk_key")) {
                return Result.failed("权限键名已存在");
            }
            if (e.getMessage().contains("uk_permission")) {
                return Result.failed("改菜单下已存在相同类型权限");
            }
            throw e;
        }
        return Result.success();
    }
}
