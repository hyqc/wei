package com.wei.admin.bo;

import com.wei.admin.po.AdminUserPo;
import com.wei.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Administrator
 */
@Getter
@Setter
public class UserListItem {
    private Integer adminId;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private Boolean enabled;
    private String createTime;
    private String modifyTime;
    private String lastLoginTime;


    public UserListItem setAdminUsersListItem(AdminUserPo adminUserPo) {
        this.adminId = adminUserPo.getId();
        this.username = adminUserPo.getUsername();
        this.nickname = adminUserPo.getNickname();
        this.email = adminUserPo.getEmail();
        this.avatar = adminUserPo.getAvatar();
        this.enabled = adminUserPo.getEnabled();
        this.createTime = adminUserPo.getCreateTime() == null ? "" : adminUserPo.getCreateTime().format(DateTimeUtil.DATE_TIME_FORMATTER);
        this.modifyTime = adminUserPo.getModifyTime() == null ? "" : adminUserPo.getModifyTime().format(DateTimeUtil.DATE_TIME_FORMATTER);
        this.lastLoginTime = adminUserPo.getLastLoginTime() == null ? "" : adminUserPo.getLastLoginTime().format(DateTimeUtil.DATE_TIME_FORMATTER);
        return this;
    }
}
