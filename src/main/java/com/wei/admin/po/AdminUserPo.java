package com.wei.admin.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wei.util.DateTimeUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author wlp
 */
@Data
@Component
public class AdminUserPo {
    /**
     * 管理员ID，自增
     */
    private Integer id;
    /**
     * 管理员名称唯一键
     */
    private String username;
    /**
     * 管理员昵称
     */
    private String nickname;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 最后登录ID
     */
    private String lastLoginIp;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 登录总次数
     */
    private Integer loginTotal;
    /**
     * 启用
     */
    private Boolean enabled;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyTime;

}
