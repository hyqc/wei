package com.wei.admin.service;

import com.wei.admin.bo.MenuItem;
import com.wei.admin.dao.mysql.AdminMenuDao;
import com.wei.admin.dto.*;
import com.wei.admin.po.AdminMenuPo;
import com.wei.common.Pager;
import com.wei.common.Result;
import com.wei.core.aop.LogExecutionTime;
import com.wei.util.TreeUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Slf4j
@Service
public class MenuService extends BaseService {

    @Autowired
    private AdminMenuDao adminMenuDao;

    @LogExecutionTime
    public Result selectAdminMenuList(MenuListParams params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<MenuItem> menuItems = adminMenuDao.selectAdminMenuList(params);
        Pager data = Pager.restPage(menuItems);
        return Result.success(data);
    }

    @LogExecutionTime
    public Result selectAllAdminMenu() {
        List<MenuItem> allMenus = adminMenuDao.selectAllAdminMenu();
        // 权限对应的页面ID
        Map<Integer, List<MenuItem>> menusMap = allMenus.stream()
                .collect(Collectors.groupingBy(AdminMenuPo::getParentId));
        List<MenuItem> data = TreeUtil.menuTree(menusMap, null, 0, 0, 4);
        return Result.success(data);
    }

    @LogExecutionTime
    public Result addAdminMenu(MenuAddParams params) {
        AdminMenuPo adminMenuPo = new AdminMenuPo();
        BeanUtils.copyProperties(params, adminMenuPo);
        adminMenuPo.setCreateTime(LocalDateTime.now());
        adminMenuPo.setComponent("");
        adminMenuPo.setModifyTime(adminMenuPo.getCreateTime());
        try {
            adminMenuDao.addAdminMenu(adminMenuPo);
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("uk_path")) {
                return Result.failed("菜单路由已存在");
            }
            if (e.getMessage().contains("uk_name")) {
                return Result.failed("菜单名称已存在");
            }
            if (e.getMessage().contains("uk_key")) {
                return Result.failed("菜单键名已存在");
            }
        }
        return Result.success();
    }

    @LogExecutionTime
    public Result getAdminMenuDetail(MenuDetailParams params) {
        AdminMenuPo adminMenuPo = adminMenuDao.findAdminMenuById(params.getId());
        if (adminMenuPo == null) {
            return Result.failed("菜单不存在或已被删除");
        }
        return Result.success(adminMenuPo);
    }

    @LogExecutionTime
    public Result editAdminMenu(MenuEditParams params) {
        AdminMenuPo adminMenuPo = new AdminMenuPo();
        BeanUtils.copyProperties(params, adminMenuPo);
        adminMenuPo.setModifyTime(LocalDateTime.now());
        try {
            Integer res = adminMenuDao.editAdminMenu(adminMenuPo);
            return returnEditResult(res);
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("uk_path")) {
                return Result.failed("菜单路由已存在");
            }
            if (e.getMessage().contains("uk_name")) {
                return Result.failed("菜单名称已存在");
            }
            if (e.getMessage().contains("uk_key")) {
                return Result.failed("菜单键名已存在");
            }
            return Result.failed("编辑菜单失败");
        }
    }

    @LogExecutionTime
    public Result enableAdminMenu(MenuEnabledParams params) {
        AdminMenuPo adminMenuPo = new AdminMenuPo();
        adminMenuPo.setModifyTime(LocalDateTime.now());
        adminMenuPo.setId(params.getId());
        adminMenuPo.setEnabled(params.getEnabled());
        adminMenuDao.editAdminMenu(adminMenuPo);
        return returnEnableResult(params.getEnabled());
    }

    @LogExecutionTime
    public Result deleteAdminMenu(MenuDeleteParams params) {
        AdminMenuPo adminMenuPo = adminMenuDao.findAdminMenuById(params.getId());
        if (adminMenuPo == null) {
            return Result.failed("菜单不存在或已被删除");
        }
        if (adminMenuPo.getEnabled()) {
            return Result.failed("菜单启用状态下不能删除");
        }
        adminMenuDao.deleteAdminMenu(params.getId());
        return Result.success("删除成功");
    }
}
