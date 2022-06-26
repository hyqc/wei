package com.wei.admin.common;

import com.wei.common.CodeEnum;
import com.wei.admin.po.AdminUserPo;
import com.wei.common.ErrorCode;

/**
 * @author wlp
 * @date 2022-05-24 19:12
 **/
public class UserCommon {
    /**
     * 检测账号有效性，不校验密码，并返回对应的错误码
     *
     * @param adminUserPo 账号信息
     * @return long
     */
    public static ErrorCode checkAccountValidByAccount(AdminUserPo adminUserPo) {
        // 账号不存在
        boolean isAccountNotExist = adminUserPo == null || adminUserPo.getId() == null || adminUserPo.getId() < 1;
        if (isAccountNotExist) {
            return CodeEnum.ACCOUNT_NOT_EXIST;
        }
        // 账号已被禁用
        if (!adminUserPo.getEnabled()) {
            return CodeEnum.ACCOUNT_LOCKED;
        }
        return CodeEnum.SUCCESS;
    }
}
