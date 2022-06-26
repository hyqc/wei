package com.wei.admin.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author Administrator
 */
@Data
public class AccountPermissionParams {

    @Range(min = 1, message = "无效页面")
    private Integer menuId;
}
