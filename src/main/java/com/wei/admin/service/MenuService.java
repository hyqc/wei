package com.wei.admin.service;

import com.wei.admin.bo.ApiListItem;
import com.wei.admin.bo.MenuItem;
import com.wei.admin.bo.MenuPermissions;
import com.wei.admin.bo.PermissionApiItem;
import com.wei.admin.dao.mysql.AdminApiDao;
import com.wei.admin.dao.mysql.AdminMenuDao;
import com.wei.admin.dao.mysql.AdminPermissionDao;
import com.wei.admin.dto.*;
import com.wei.admin.pe.AdminPermissionTypeEnum;
import com.wei.admin.po.AdminMenuPo;
import com.wei.admin.po.AdminPermissionPo;
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
import java.util.*;
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

    @Autowired
    private AdminPermissionDao adminPermissionDao;

    @Autowired
    private AdminApiDao adminApiDao;

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
        List<MenuItem> data = TreeUtil.menuTree(menusMap, null, 0, 1, 4);
        List<MenuItem> result = new ArrayList<>();
        result.add(setTopMenu(data));
        return Result.success(result);
    }

    public static MenuItem setTopMenu(List<MenuItem> children) {
        MenuItem topMenu = new MenuItem();
        topMenu.setId(0);
        topMenu.setLevel(0);
        topMenu.setName("顶级菜单");
        topMenu.setParentId(0);
        topMenu.setPath("/");
        topMenu.setDescribe("顶级菜单");
        topMenu.setHideInMenu(false);
        topMenu.setHideChildrenInMenu(false);
        topMenu.setEnabled(true);
        topMenu.setChildren(children);
        return topMenu;
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
        AdminMenuPo adminMenuPo = adminMenuDao.findAdminMenuById(params.getMenuId());
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
        adminMenuPo.setId(params.getMenuId());
        adminMenuPo.setEnabled(params.getEnabled());
        adminMenuDao.editAdminMenu(adminMenuPo);
        return returnEnableResult(params.getEnabled());
    }

    @LogExecutionTime
    public Result deleteAdminMenu(MenuDeleteParams params) {
        AdminMenuPo adminMenuPo = adminMenuDao.findAdminMenuById(params.getMenuId());
        if (adminMenuPo == null) {
            return Result.failed("菜单不存在或已被删除");
        }
        if (adminMenuPo.getEnabled()) {
            return Result.failed("菜单启用状态下不能删除");
        }
        adminMenuDao.deleteAdminMenu(params.getMenuId());
        return Result.success("删除成功");
    }

    @LogExecutionTime
    public Result addPermissions(MenuPermissionsParams params) {
        AdminMenuPo adminMenuPo = adminMenuDao.findAdminMenuById(params.getMenuId());
        if (adminMenuPo == null) {
            return Result.failed("菜单不存在或已被删除");
        }
        MenuPermissions result = new MenuPermissions();
        MenuItem menu = new MenuItem();
        BeanUtils.copyProperties(adminMenuPo, menu);
        result.setMenu(menu);

        List<AdminPermissionPo> permissionPos = adminPermissionDao.selectPermissionsByMenuId(params.getMenuId());
        if (permissionPos.size() == 0) {
            List<PermissionApiItem> permissionApiItems = new ArrayList<>();
            for (AdminPermissionTypeEnum item : AdminPermissionTypeEnum.values()) {
                PermissionApiItem tmpPermission = new PermissionApiItem();
                tmpPermission.setApis(new ArrayList<>());
                tmpPermission.setEnabled(true);
                tmpPermission.setId(null);
                tmpPermission.setMenuId(params.getMenuId());
                tmpPermission.setType(item.getValue());
                tmpPermission.setKey(adminMenuPo.getKey() + item.getValue().substring(0, 1).toUpperCase() + item.getValue().substring(1));
                tmpPermission.setName(adminMenuPo.getName() + item.getText());
                tmpPermission.setDescribe(tmpPermission.getName());
                permissionApiItems.add(tmpPermission);
            }
            result.setPermissions(permissionApiItems);
            return Result.success(result);
        }
        Set<Integer> permissionIds = permissionPos.stream().map(AdminPermissionPo::getId).collect(Collectors.toSet());
        List<ApiListItem> apis = permissionIds.size() > 0 ? adminApiDao.selectApisByPermissionIds(permissionIds) : new ArrayList<>();
        Map<Integer, List<ApiListItem>> permissionsApisMap = apis.stream().collect(Collectors.groupingBy(ApiListItem::getPermissionId));

        result.setPermissions(permissionPos.stream().map(item -> {
            PermissionApiItem tmp = new PermissionApiItem();
            BeanUtils.copyProperties(item, tmp);
            if (permissionsApisMap.containsKey(item.getId())) {
                tmp.setApis(permissionsApisMap.get(item.getId()));
            }
            return tmp;
        }).collect(Collectors.toList()));

        return Result.success(result);
    }

    public Result pageMenus(MenuPagesParams params) {
        List<MenuItem> allMenus = new ArrayList<>();
        List<MenuItem> menus = adminMenuDao.selectAllAdminPageMenus();
        if (params.getAll()) {
            MenuItem allItem = new MenuItem();
            allItem.setId(0);
            allItem.setPath("");
            allItem.setLevel(0);
            allItem.setParentId(0);
            allItem.setName("全部");
            allItem.setKey("All");
            allMenus.add(allItem);
        }
        allMenus.addAll(menus);
        return Result.success(allMenus);
    }
}
