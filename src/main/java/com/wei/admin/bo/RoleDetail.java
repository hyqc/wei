package com.wei.admin.bo;

import com.wei.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wlp
 * @date 2022/6/24
 **/
@Data
public class RoleDetail {
    /**
     * 自增角色ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色的全部有效权限ID
     */
    private List<Integer> permissionIds;
    /**
     * 角色描述
     */
    private String describe;
    /**
     * 创建角色的管理员ID
     */
    private Integer createAdminId;

    private String createAdminName;

    /**
     * 修改角色的管理员ID
     */
    private Integer modifyAdminId;

    private String modifyAdminName;

    /**
     * 是否启用
     */
    private Boolean enabled;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = DateTimeUtil.DATE_TIME_FORMAT)
    private LocalDateTime modifyTime;
}
