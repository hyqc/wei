package com.wei.admin.dao.mysql;

import com.wei.admin.bo.ApiListItem;
import com.wei.admin.bo.MenuItem;
import com.wei.admin.bo.PermissionDetail;
import com.wei.admin.bo.PermissionsPageItem;
import com.wei.admin.dto.PermissionListParams;
import com.wei.admin.po.AdminPermissionPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 */
@Mapper
@Component
public interface AdminPermissionDao {
    /**
     * 按照管理员ID和菜单ID查询权限列表
     * @param adminId
     * @param menuId
     * @return
     */
    List<AdminPermissionPo> selectPermissions(@Param("adminId") Integer adminId, @Param("menuId") Integer menuId);

    /**
     * 查询全部有效的菜单
     * @return
     */
    List<MenuItem> selectAllValidMenus();

    /**
     * 查询超管的权限
     * @return
     */
    List<AdminPermissionPo> selectPermissionsByAdminister();

    /**
     * 获取全部可以访问的接口资源
     * @param permissionsId
     * @return
     */
    List<String> selectAllAccessApiKeys(List<Integer> permissionsId);

    /**
     * 获取全部的权限
     * @return
     */
    List<PermissionsPageItem> selectAllPermission();

    List<Integer> selectAllPermissionIdsByRoleId(Integer roleId);

    List<Integer> selectAllPermissionIdsByCondition(Integer adminId, Integer gameId, Integer roleId);

    /**
     * 创建权限
     * @param adminPermissionPo
     */
    void addAdminPermission(AdminPermissionPo adminPermissionPo);

    /**
     * 权限详情
     * @param id
     * @return
     */
    PermissionDetail findAdminPermissionDetailById(Integer id);

    /**
     * 编辑权限
     * @param adminPermissionPo
     * @return
     */
    Integer editAdminPermission(AdminPermissionPo adminPermissionPo);

    /**
     * 删除权限
     * @param id
     */
    void deleteAdminPermission(Integer id);

    /**
     * 权限列表
     * @param params
     * @return
     */
    List<PermissionDetail> selectPermissionsList(PermissionListParams params);

    /**
     * 绑定接口资源
     * @param permissionId
     * @param apiIds
     */
    void addPermissionApis(@Param("permissionId") Integer permissionId, @Param("apiIds") List<Integer> apiIds);

    /**
     * 按照权限ID删除权限接口关系表数据
     * @param permissionId
     */
    void deleteAdminPermissionApis(Integer permissionId);

    /**
     * 按照指定的菜单ID获取权限列表
     * @param menuId
     * @return
     */
    List<AdminPermissionPo> selectPermissionsByMenuId(Integer menuId);

    /**
     * 批量添加菜单的权限
     * @param permissions
     */
    void batchAddMenuPermissions(@Param("permissions") List<AdminPermissionPo> permissions);
}
