package com.wei.admin.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wei.admin.po.AdminRolePo;
import com.wei.util.DateTimeUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Administrator
 */
@Data
public class UserListItem {
    private Integer adminId;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private String roleIds;
    private List<UserRoleItem> roles;
    private Boolean enabled;
    private Integer loginTotal;
    private String lastLoginIp;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime lastLoginTime;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyTime;
}
