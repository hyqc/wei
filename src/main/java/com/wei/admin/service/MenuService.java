package com.wei.admin.service;

import com.wei.admin.bo.*;
import com.wei.admin.common.UserDetails;
import com.wei.admin.constant.CommonConstant;
import com.wei.admin.dao.mysql.AdminApiDao;
import com.wei.admin.dao.mysql.AdminMenuDao;
import com.wei.admin.dao.mysql.AdminPermissionDao;
import com.wei.admin.dto.*;
import com.wei.admin.pe.AdminPermissionTypeEnum;
import com.wei.admin.po.AdminMenuPo;
import com.wei.admin.po.AdminPermissionPo;
import com.wei.admin.po.AdminUserPo;
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
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result deleteAdminMenu(MenuDeleteParams params) {
        AdminMenuPo adminMenuPo = adminMenuDao.findAdminMenuById(params.getMenuId());
        if (adminMenuPo == null) {
            return Result.failed("菜单不存在或已被删除");
        }
        if (adminMenuPo.getEnabled()) {
            return Result.failed("菜单启用状态下不能删除");
        }
        List<AdminPermissionPo> permissionPos = adminPermissionDao.selectPermissionsByMenuId(params.getMenuId());
        List<Integer> permissionIds = permissionPos.stream().map(AdminPermissionPo::getId).collect(Collectors.toList());
        adminMenuDao.deleteAdminMenu(params.getMenuId());
        adminPermissionDao.deleteAdminPermissionByMenuId(params.getMenuId());
        if (permissionIds.size() > 0) {
            adminPermissionDao.deleteAdminPermissionApisByPermissionId(permissionIds);
        }
        return Result.success("删除成功");
    }

    @LogExecutionTime
    public Result getMenuPermissions(MenuPermissionsParams params) {
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

    @LogExecutionTime
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

    @LogExecutionTime
    public Result allMode() {
        // 全部权限ID
        List<PermissionsPageItem> permissionsPageItemList = adminPermissionDao.selectAllPermission();
        // 获取权限对应的页面信息
        LinkedHashMap<Integer, String> pagesMap = new LinkedHashMap<>();
        List<Integer> pageIds = new ArrayList<>();
        for (PermissionsPageItem permissionsPageItem : permissionsPageItemList) {
            pageIds.add(permissionsPageItem.getMenuId());
            if (!pagesMap.containsKey(permissionsPageItem.getMenuId())) {
                pagesMap.put(permissionsPageItem.getMenuId(), permissionsPageItem.getMenuName());
            }
        }
        pageIds = new ArrayList<>(new HashSet<Integer>(pageIds));
        List<MenuItem> adminMenusProps = adminMenuDao.selectAllValidMenus();
        // 获取顶级模块
        List<MenuPagesItem> menuPagesItemList = getAllTopsMenuByPageIds(adminMenusProps, pageIds);

        // 获取页面对应的权限
        LinkedHashMap<Integer, PermissionListItem.PageItem> pages = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : pagesMap.entrySet()) {
            Integer pageId = entry.getKey();
            PermissionListItem.PageItem pageItem = new PermissionListItem.PageItem();
            pageItem.setPageId(pageId);
            pageItem.setPageName(entry.getValue());
            pageItem.setPermissions(new ArrayList<>());
            for (PermissionsPageItem permissionsPageItem : permissionsPageItemList) {
                if (permissionsPageItem.getMenuId().equals(pageId)) {
                    PermissionListItem.PermissionItem permissionItem = new PermissionListItem.PermissionItem();
                    permissionItem.setPermissionId(permissionsPageItem.getPermissionId());
                    permissionItem.setPermissionName(permissionsPageItem.getPermissionName());
                    permissionItem.setPermission(permissionsPageItem.getPermission());
                    permissionItem.setPermissionText(AdminPermissionTypeEnum.getByValue(permissionsPageItem.getPermission()).getText());
                    pageItem.getPermissions().add(permissionItem);
                }
            }
            pages.put(pageId, pageItem);
        }

        LinkedHashMap<Integer, PermissionListItem> resultMap = new LinkedHashMap<>();
        for (MenuPagesItem menuPagesItem : menuPagesItemList) {
            if (pages.containsKey(menuPagesItem.getPageId())) {
                PermissionListItem permissionListItem = new PermissionListItem();
                if (!resultMap.containsKey(menuPagesItem.getMenuId())) {
                    permissionListItem.setModelId(menuPagesItem.getMenuId());
                    permissionListItem.setModelName(menuPagesItem.getAdminMenusProp().getName());
                    permissionListItem.setPages(new ArrayList<>());
                } else {
                    permissionListItem = resultMap.get(menuPagesItem.getMenuId());
                }
                permissionListItem.getPages().add(pages.get(menuPagesItem.getPageId()));
                resultMap.put(menuPagesItem.getMenuId(), permissionListItem);
            }
        }
        return Result.success(resultMap.values());
    }

    private List<MenuPagesItem> getAllTopsMenuByPageIds(List<MenuItem> menuItems, List<Integer> pageIds) {
        List<MenuPagesItem> result = new ArrayList<>();
        for (Integer pageId : pageIds) {
            MenuItem adminMenuPo = TreeUtil.getTopMenuByChildrenId(menuItems, pageId);
            if (adminMenuPo != null) {
                MenuPagesItem menuPagesItem = new MenuPagesItem();
                menuPagesItem.setAdminMenusProp(adminMenuPo);
                menuPagesItem.setMenuId(adminMenuPo.getId());
                menuPagesItem.setPageId(pageId);
                result.add(menuPagesItem);
            }
        }
        return result;
    }
}
