package com.wei.admin.constant;

/**
 * @author wlp
 * @date 2022-05-24 18:55
 **/
public class CommonConstant {
    /**
     * 超管ID值
     */
    public final static Integer ADMIN_ID = 1;

    /**
     * 是否是超级管理员
     *
     * @param adminId 管理员ID
     * @return [不]是超级管理员
     */
    public static boolean isAdministrator(Integer adminId) {
        return ADMIN_ID.equals(adminId);
    }
}
