package com.wei.admin.dao.mysql;

import com.wei.admin.bo.UserListItem;
import com.wei.admin.dto.UserListParams;
import com.wei.admin.bo.LoginInfo;
import com.wei.admin.po.AdminUserPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
@Component
public interface AdminUserDao {

    /**
     * 注册管理员账号
     *
     * @param adminUserPo
     */
    void registerAdminUser(AdminUserPo adminUserPo);

    /**
     * 按照管理员名称查询管理员信息
     *
     * @param username
     * @return
     */
    AdminUserPo findAdminUserInfoByUsername(@Param("username") String username);

    /**
     * 更新密码
     *
     * @param adminId
     * @param password
     */
    void updatePassword(@Param("adminId") Integer adminId, @Param("password") String password);

    /**
     * 更新账号信息
     *
     * @param adminUserPo
     */
    void updateAccountInfo(AdminUserPo adminUserPo);

    /**
     * 管理员列表
     *
     * @param params
     * @return
     */
    List<UserListItem> selectAdminUserList(UserListParams params);

    /**
     * 更新最后登录信息
     *
     * @param username
     * @param info
     */
    void updateLastLoginInfo(@Param("username") String username, @Param("lastLoginInfo") LoginInfo info);

    /**
     * 按照管理员ID查询管理员详情
     *
     * @param adminId
     * @return
     */
    AdminUserPo findAdminUserDetailByAdminId(Integer adminId);

    /**
     * 添加管理员
     *
     * @param adminUserPo
     * @return
     */
    Integer addAdminUser(AdminUserPo adminUserPo);

    /**
     * 编辑管理员
     *
     * @param adminUserPo
     */
    void editAdminUser(AdminUserPo adminUserPo);

    /**
     * 编辑管理员账号启用状态
     *
     * @param adminId
     * @param enabled
     */
    void updateAdminUserIsEnabled(@Param("adminId") Integer adminId, @Param("enabled") Boolean enabled);

    /**
     * 添加管理员角色
     * @param adminId
     * @param roleIds
     */
    void addAdminUserRoles(@Param("adminId") Integer adminId,@Param("roleIds") List<Integer> roleIds);
}
