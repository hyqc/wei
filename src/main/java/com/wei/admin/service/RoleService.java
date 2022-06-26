package com.wei.admin.service;

import com.wei.admin.bo.RoleDetail;
import com.wei.admin.bo.RolePermissionItem;
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
    private UserService userService;

    @LogExecutionTime
    public Result selectAdminRolesList(RoleListParams params) {
        RoleListItem roleListItem = new RoleListItem();
        roleListItem.setRoleId(params.getRoleId());
        roleListItem.setRoleName(params.getRoleName());
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
        List<RoleListItem> roleListItemList = adminRoleDao.selectAdminRoleList(roleListItem, startTime, endTime);
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
        Integer roleId = params.getRoleId();
        RoleDetail roleDetail = adminRoleDao.findAdminRoleDetail(roleId);
        if (roleDetail == null) {
            return Result.failed("角色不存在");
        }
        return Result.success(roleDetail);
    }

    @LogExecutionTime
    public Result editAdminRole(RoleEditParams params) {
        // 获取角色的原始游戏ID
        AdminRolePo adminRolePo = adminRoleDao.findAdminRoleById(params.getRoleId());
        if (adminRolePo == null || adminRolePo.getId() < 1) {
            return Result.failed("角色不存在或已被删除");
        }

        // 修改角色
        AdminUserPo adminUserPo = UserService.getAdminUserDetails().getAdminUsersPo();
        AdminRolePo updateData = new AdminRolePo();
        updateData.setId(params.getRoleId());
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
        adminRolePo.setId(params.getRoleId());
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
    public Result bindRolePermissions(RoleAssignParams params) {
        List<Integer> permissionIds = params.getPermissionIds().stream().filter(id -> id > 0).distinct().collect(Collectors.toList());
        if (permissionIds.size() == 0) {
            return Result.failed("没有有效的权限");
        }
        // 获取角色的原始游戏ID
        AdminRolePo adminRolePo = adminRoleDao.findAdminRoleById(params.getRoleId());
        if (adminRolePo == null || adminRolePo.getId() < 1) {
            return Result.failed("角色不存在或已被删除");
        }
        // 删掉旧的权限
        adminRoleDao.deleteAdminRolePermissions(params.getRoleId());
        // 添加新的权限
        try {
            adminRoleDao.addAdminRolePermission(params.getRoleId(), permissionIds);
            return Result.success();
        }catch (DuplicateKeyException e){
            if (e.getMessage().contains("uk_role_permission")){
                throw new BusinessException("角色权限关系已存在");
            }
            throw new BusinessException("绑定角色权限关系失败");
        }
    }

    @LogExecutionTime
    public Result selectAdminRolePermissions(RolePermissionParams params) {
        List<RolePermissionItem> rolePermissionItems = adminRoleDao.selectAdminRolePermissionAllByRoleId(params.getRoleId());
        rolePermissionItems.forEach(RolePermissionItem::setPermissionTypeText);
        return Result.success(rolePermissionItems);
    }
}
