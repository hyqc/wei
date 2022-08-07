package com.wei.admin.bo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
}
