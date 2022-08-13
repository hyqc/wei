package com.wei.util;

import com.wei.admin.bo.MenuItem;
import com.wei.admin.bo.MenuPagesItem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wlp
 * @date 2022/6/26
 **/
public class TreeUtil {
    /**
     * 生成菜单书
     *
     * @param menusMap 菜单列表
     * @param menusIds 菜单ID集合
     * @param parentId 父级菜单ID
     * @param level    菜单级别
     * @param maxDeep  最大菜单深度
     * @return
     */
    public static List<MenuItem> menuTree(Map<Integer, List<MenuItem>> menusMap, Set<Integer> menusIds,
                                          Integer parentId, Integer level, Integer maxDeep) {
        if (!menusMap.containsKey(parentId)) {
            return new ArrayList<>();
        }
        return menusMap.get(parentId).stream().map(menuItem -> {
            menuItem.setLevel(level);
            menuItem.setChildren(null);
            Integer next = level + 1;
            if (next <= maxDeep) {
                List<MenuItem> children = menuTree(menusMap, menusIds, menuItem.getId(), next, maxDeep);
                menuItem.setChildren(children.size() > 0 ? children : null);
            }
            if (menusIds != null && menuItem.getChildren().size() == 0
                    && !menusIds.contains(parentId)
                    && !menusIds.contains(menuItem.getId())) {
                return null;
            }
            return menuItem;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static List<MenuItem> menuList(Map<Integer, List<MenuItem>> menusMap, Set<Integer> menusIds,
                                          Integer parentId, Integer level) {
        if (!menusMap.containsKey(parentId)) {
            return new ArrayList<>();
        }
        List<MenuItem> list = new ArrayList<>();
        menusMap.get(parentId).forEach(item -> {
            if (menusIds == null || menusIds.contains(item.getId())) {
                item.setChildren(null);
                item.setLevel(level);
                list.add(item);
                List<MenuItem> childrenList = menuList(menusMap, menusIds, item.getId(), level + 1);
                if (childrenList.size() > 0) {
                    list.addAll(childrenList);
                }
            }
        });
        return list;
    }

    /**
     * 根据子菜单ID获取全部的祖先菜单
     *
     * @param menuItems
     * @param childrenId
     * @return
     */
    public static MenuItem getTopMenuByChildrenId(List<MenuItem> menuItems, Integer childrenId) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(childrenId)) {
                if (item.getParentId().equals(0)) {
                    return item;
                }
                return getTopMenuByChildrenId(menuItems, item.getParentId());
            }
        }
        return null;
    }

    public static List<MenuPagesItem> getAllTopsMenuByPageIds(List<MenuItem> menuItems, List<Integer> pageIds) {
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

    /**
     * 按照叶子节点列表找出全部的祖先
     * @param result
     * @param menuItemMap
     * @param pageIds
     * @return
     */
    public static List<MenuItem> getAllRelatedMenusByPageIds(List<MenuItem> result, Map<Integer, MenuItem> menuItemMap, List<Integer> pageIds) {
        for (Integer pageId : pageIds) {
            getAllFatherMenuByChildrenId(result, menuItemMap, pageId);
        }
        return result;
    }

    public static List<MenuItem> getAllChildrenPagesByPageIds(List<MenuItem> result, List<MenuItem> allMenus, List<Integer> pageIds) {
        for (Integer parentId : pageIds) {
            getAllChildrenMenuByChildrenId(result, allMenus, parentId);
        }
        return result;
    }

    /**
     * 找出单个叶子节点的祖先列表
     * @param result
     * @param menuItemMap
     * @param menuId
     * @return
     */
    private static List<MenuItem> getAllFatherMenuByChildrenId(List<MenuItem> result, Map<Integer, MenuItem> menuItemMap, Integer menuId) {
        if (menuItemMap.containsKey(menuId)) {
            MenuItem menu = menuItemMap.get(menuId);
            result.add(menu);
            getAllFatherMenuByChildrenId(result, menuItemMap, menu.getParentId());
        }
        return result;
    }

    /**
     * 找出单个节点的所有后代页面节点列表
     * @param result
     * @param allMenus
     * @param parentId
     * @return
     */
    private static List<MenuItem> getAllChildrenMenuByChildrenId(List<MenuItem> result, List<MenuItem> allMenus, Integer parentId) {
        for (MenuItem item: allMenus){
            if(item.getParentId().equals(parentId)){
                result.add(item);
                List<MenuItem> tmpMens = allMenus.stream().filter(prop-> !prop.getParentId().equals(parentId)).collect(Collectors.toList());
                getAllChildrenMenuByChildrenId(result,tmpMens, item.getId());
            }
        }
        return result;
    }

}
