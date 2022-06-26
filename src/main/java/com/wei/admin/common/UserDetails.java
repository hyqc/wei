package com.wei.admin.common;

import com.wei.admin.po.AdminUserPo;
import com.wei.admin.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Administrator
 */
@Component
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final AdminUserPo adminUserPo;

    private final UserService userService;

    public UserDetails(AdminUserPo adminUserPo, UserService userService) {
        this.adminUserPo = adminUserPo;
        this.userService = userService;
    }

    public AdminUserPo getAdminUsersPo() {
        return adminUserPo;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userService.getAccountPermissionApiKey(adminUserPo.getId());
    }

    @Override
    public String getPassword() {
        return adminUserPo.getPassword();
    }

    @Override
    public String getUsername() {
        return adminUserPo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return adminUserPo.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return adminUserPo.getEnabled();
    }
}
