package com.wei.util;

import com.wei.admin.bo.MenuItem;

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
            Integer next = level + 1;
            if (next <= maxDeep) {
                menuItem.setChildren(menuTree(menusMap, menusIds, menuItem.getId(), next, maxDeep));
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
}
