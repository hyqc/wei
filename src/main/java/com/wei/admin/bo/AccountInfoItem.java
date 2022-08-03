package com.wei.admin.bo;

import com.wei.admin.po.AdminMenuPo;
import com.wei.admin.po.AdminUserPo;
import com.wei.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Getter
@Setter
public class AccountInfoItem {
    private Integer adminId;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    private String createTime;
    private String modifyTime;
    private String lastLoginTime;
    private String lastLoginIp;
    private Integer loginTotal;
    private Boolean enabled;
    private String token;
    private Long expire;
    private Map<String, MenuItem> menus;
    private Map<String,String> permissions;

    public AccountInfoItem setAccountInfoItem(AdminUserPo adminUserPo) {
        this.setAdminId(adminUserPo.getId());
        this.setUsername(adminUserPo.getUsername());
        this.setNickname(adminUserPo.getNickname());
        this.setAvatar(adminUserPo.getAvatar());
        this.setEmail(adminUserPo.getEmail());
        this.setEnabled(adminUserPo.getEnabled());
        this.setLoginTotal(adminUserPo.getLoginTotal());
        this.setLastLoginIp(adminUserPo.getLastLoginIp());
        this.setCreateTime(adminUserPo.getCreateTime().format(DateTimeUtil.DATE_TIME_FORMATTER));
        this.setModifyTime(adminUserPo.getModifyTime().format(DateTimeUtil.DATE_TIME_FORMATTER));
        if (adminUserPo.getLastLoginTime() != null) {
            this.setLastLoginTime(adminUserPo.getLastLoginTime().format(DateTimeUtil.DATE_TIME_FORMATTER));
        } else {
            this.setLastLoginTime("");
        }
        return this;
    }
}
