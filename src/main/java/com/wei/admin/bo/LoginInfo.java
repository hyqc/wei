package com.wei.admin.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
}
