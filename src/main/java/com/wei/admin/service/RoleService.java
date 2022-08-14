package com.wei.admin.service;

import com.wei.admin.bo.RoleDetail;
import com.wei.admin.bo.RolePermissionItem;
import com.wei.admin.dao.mysql.AdminUserDao;
import com.wei.admin.po.AdminUserPo;
import com.wei.common.Result;
import com.wei.core.aop.LogExecutionTime;
import com.wei.core.exception.BusinessException;
import com.wei.core.exception.ParamException;
import com.wei.admin.bo.RoleListItem;
import com.wei.admin.dao.mysql.AdminRoleDao;
import com.wei.admin.dto.*;
import com.wei.admin.po.AdminRolePo;
import com.wei.common.Pager;
import com.wei.util.DateTimeUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class RoleService extends BaseService {

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Autowired
    private AdminUserDao adminUserDao;

    @LogExecutionTime
    public Result selectAdminRolesList(RoleListParams params) {
        RoleListItem roleListItem = new RoleListItem();
        roleListItem.setRoleId(params.getId());
        roleListItem.setRoleName(params.getName());
        if (params.getEnabled() != null) {
            if (params.getEnabled() == 0) {
                roleListItem.setEnabled(null);
            } else {
                roleListItem.setEnabled(params.getEnabled() == 1);
            }
        }
        LocalDateTime startTime = null;
        if (params.getCreateStartTime() != null && params.getCreateStartTime() > 0) {
            startTime = DateTimeUtil.parseTimeStamp2DateTime(params.getCreateStartTime());
        }
        LocalDateTime endTime = null;
        if (params.getCreateEndTime() != null && params.getCreateEndTime() > 0) {
            endTime = DateTimeUtil.parseTimeStamp2DateTime(params.getCreateEndTime());
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<RoleListItem> roleListItemList = adminRoleDao.selectAdminRoleList(params, roleListItem, startTime, endTime);
        Pager<RoleListItem> result = Pager.restPage(roleListItemList);
        return Result.success(result);
    }

    @LogExecutionTime
    public Result addAdminRole(RoleAddParams params) {
        // 添加角色
        AdminUserPo adminUserPo = UserService.getAdminUserDetails().getAdminUsersPo();
        AdminRolePo adminRolePo = new AdminRolePo();

        adminRolePo.setName(params.getName());
        adminRolePo.setDescribe(params.getDescribe());
        adminRolePo.setEnabled(params.getEnabled());
        adminRolePo.setCreateAdminId(adminUserPo.getId());
        adminRolePo.setModifyAdminId(adminUserPo.getId());
        adminRolePo.setCreateTime(LocalDateTime.now());
        adminRolePo.setModifyTime(adminRolePo.getCreateTime());
        try {
            adminRoleDao.addAdminRole(adminRolePo);
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new ParamException("角色已存在");
        }
        return Result.success("添加角色成功");
    }

    @LogExecutionTime
    public Result getAdminRoleDetail(RoleDetailParams params) {
        RoleDetail roleDetail = adminRoleDao.findAdminRoleDetail(params.getId());
        if (roleDetail == null) {
            return Result.failed("角色不存在");
        }
       Set<Integer> setIds = adminRoleDao.selectAdminRolePermissionAllByRoleId(roleDetail.getId()).stream()
               .map(RolePermissionItem::getPermissionId).collect(Collectors.toSet());
        roleDetail.setPermissionIds(new ArrayList<>(setIds));
        return Result.success(roleDetail);
    }

    @LogExecutionTime
    public Result editAdminRole(RoleEditParams params) {
        // 获取角色的原始游戏ID
        AdminRolePo adminRolePo = adminRoleDao.findAdminRoleById(params.getId());
        if (adminRolePo == null || adminRolePo.getId() < 1) {
            return Result.failed("角色不存在或已被删除");
        }

        // 修改角色
        AdminUserPo adminUserPo = UserService.getAdminUserDetails().getAdminUsersPo();
        AdminRolePo updateData = new AdminRolePo();
        updateData.setId(params.getId());
        updateData.setName(params.getName());
        updateData.setDescribe(params.getDescribe());
        updateData.setEnabled(params.getEnabled());
        updateData.setModifyAdminId(adminUserPo.getId());
        updateData.setModifyTime(LocalDateTime.now());
        try {
            Integer result = adminRoleDao.editAdminRole(updateData);
            return returnEditResult(result);
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("uk_name")) {
                return Result.failed("角色名称已存在");
            }
            throw new BusinessException("编辑角色失败");
        }
    }

    @LogExecutionTime
    public Result updateAdminRoleIsEnabled(RoleUpdateIsEnabledParams params) {
        AdminUserPo adminUserPo = UserService.getAdminUserDetails().getAdminUsersPo();
        AdminRolePo adminRolePo = new AdminRolePo();
        adminRolePo.setId(params.getId());
        adminRolePo.setEnabled(params.getEnabled());
        adminRolePo.setModifyAdminId(adminUserPo.getId());
        adminRolePo.setModifyTime(LocalDateTime.now());
        Integer total = adminRoleDao.editAdminRole(adminRolePo);
        if (total > 0) {
            String msg = params.getEnabled()
                    ? "启用成功" : "禁用成功";
            return Result.success(msg);
        }
        return Result.success("没有任何更新");
    }

    @LogExecutionTime
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result bindRolePermissions(RoleBindPermissionsParams params) {
        List<Integer> permissionIds = params.getPermissionIds().stream().filter(id -> id > 0).distinct().collect(Collectors.toList());
        if (permissionIds.size() == 0) {
            return Result.failed("没有有效的权限");
        }
        AdminRolePo adminRolePo = adminRoleDao.findAdminRoleById(params.getId());
        if (adminRolePo == null || adminRolePo.getId() < 1) {
            return Result.failed("角色不存在或已被删除");
        }
        // 删掉旧的权限
        adminRoleDao.deleteAdminRolePermissions(params.getId());
        // 添加新的权限
        try {
            adminRoleDao.addAdminRolePermission(params.getId(), permissionIds);
            return Result.success();
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("uk_role_permission")) {
                throw new BusinessException("角色权限关系已存在");
            }
            throw new BusinessException("绑定角色权限关系失败");
        }
    }

    @LogExecutionTime
    public Result selectAdminRolePermissions(RolePermissionsParams params) {
        List<RolePermissionItem> rolePermissionItems = adminRoleDao.selectAdminRolePermissionAllByRoleId(params.getId());
        rolePermissionItems.forEach(RolePermissionItem::setPermissionTypeText);
        return Result.success(rolePermissionItems);
    }

    @LogExecutionTime
    public Result selectAdminRolesAll(RoleListParams params) {
        List<RoleListItem> all = adminRoleDao.selectAdminRoleAll(params);
        return Result.success(all);
    }

    @LogExecutionTime
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result deleteRole(RoleDeleteParams params) {
        AdminRolePo adminRolePo = adminRoleDao.findAdminRoleById(params.getId());
        if (adminRolePo == null || adminRolePo.getId() < 1) {
            return Result.failed("角色不存在或已被删除");
        }
        if (adminRolePo.getEnabled()) {
            return Result.failed("启用下的角色不能删除");
        }
        adminRoleDao.deleteAdminRoleByRoleId(params.getId());
        adminRoleDao.deleteAdminRole(params.getId());
        adminUserDao.deleteAdminUserRolesByRoleId(params.getId());
        return Result.success("删除角色成功");
    }
}
