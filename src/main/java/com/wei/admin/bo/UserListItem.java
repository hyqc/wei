package com.wei.admin.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wei.util.DateTimeUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


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
