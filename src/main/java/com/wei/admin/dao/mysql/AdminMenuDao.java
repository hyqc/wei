package com.wei.admin.dao.mysql;

import com.wei.admin.bo.MenuItem;
import com.wei.admin.dto.MenuListParams;
import com.wei.admin.po.AdminMenuPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author wlp
 * @date 2022/6/25
 **/
@Mapper
@Component
public interface AdminMenuDao {

    /**
     * 查询菜单
     *
     * @param params
     * @return
     */
    List<MenuItem> selectAdminMenuList(MenuListParams params);

    /**
     * 获取全部的菜单
     *
     * @return
     */
    List<MenuItem> selectAllAdminMenu();

    /**
     * 添加菜单
     *
     * @param adminMenuPo
     */
    void addAdminMenu(AdminMenuPo adminMenuPo);

    /**
     * 按照菜单ID查询菜单详情
     *
     * @param menuId
     * @return
     */
    AdminMenuPo findAdminMenuById(Integer menuId);

    /**
     * 编辑菜单
     * @param adminMenuPo
     * @return
     */
    Integer editAdminMenu(AdminMenuPo adminMenuPo);

    /**
     * 删除菜单
     * @param id
     */
    void deleteAdminMenu(Integer id);
}
