package com.wei.admin.dao.mysql;

import com.wei.admin.bo.RoleDetail;
import com.wei.admin.bo.RoleListItem;
import com.wei.admin.bo.RolePermissionItem;
import com.wei.admin.bo.UserRoleItem;
import com.wei.admin.po.AdminRolePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Component
public interface AdminRoleDao {
    void addAdminUserRoles(@Param("roleIds") List<Integer> roleIds, @Param("adminId") Integer adminId);

    List<Integer> getAdminUserRolesByAdminId(Integer adminId);

    void deleteAdminUserRolesByCondition(@Param("roleIds") List<Integer> roleIds, @Param("adminId") Integer adminId);

    /**
     * 获取管理员的角色列表
     *
     * @param adminId
     * @return
     */
    List<UserRoleItem> selectAllRolesByAdminId(Integer adminId);

    /**
     * 查询角色列表
     *
     * @param item
     * @param startTime
     * @param endTime
     * @return
     */
    List<RoleListItem> selectAdminRoleList(@Param("item") RoleListItem item,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);

    /**
     * 添加角色
     *
     * @param adminRolePo
     */
    void addAdminRole(AdminRolePo adminRolePo);

    /**
     * 添加角色权限
     *
     * @param roleId
     * @param permissionIds
     */
    void addAdminRolePermission(@Param("roleId") Integer roleId,@Param("permissionIds") List<Integer> permissionIds);

    /**
     * 按角色ID查找角色详情
     *
     * @param id
     * @return
     */
    RoleDetail findAdminRoleDetail(Integer id);

    /**
     * 查询角色详情
     *
     * @param id
     * @return
     */
    AdminRolePo findAdminRoleById(Integer id);

    /**
     * 编辑角色
     *
     * @param adminRolePo
     * @return
     */
    Integer editAdminRole(AdminRolePo adminRolePo);

    /**
     * 删除角色的权限
     *
     * @param roleId
     */
    void deleteAdminRolePermissions(@Param("roleId") Integer roleId);

    List<Integer> selectRolesByRoleIds(@Param("roleIds") List<Integer> roleIds);

    void deleteAdminUserRolesByAdminId(Integer adminId);

    /**
     * 查询指定角色的全部的权限
     * @param roleId
     * @return
     */
    List<RolePermissionItem> selectAdminRolePermissionAllByRoleId(Integer roleId);
}
