package com.wei.admin.dto;

import lombok.Data;

/**
 * @author wlp
 * @date 2022/8/7
 **/
@Data
public class AccountDetailParams {
    /**
     * 是否刷新token
     */
    private Boolean refreshToken = false;
}
