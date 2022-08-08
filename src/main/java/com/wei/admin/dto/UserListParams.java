package com.wei.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * @author Administrator
 */
@Data
@ApiModel("AdminUserListQueryParams")
public class UserListParams extends BaseListParams {

    @ApiParam(value = "账号", required = false)
    @Length(max = 50, message = "账号不能超过50个字符")
    private String username;

    @ApiParam(value = "昵称", required = false)
    @Length(max = 50, message = "昵称不能超过50个字符")
    private String nickname;

    @ApiParam(value = "角色", required = false)
    private String roleId;

}
