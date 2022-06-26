package com.wei.admin.bo;

import com.wei.admin.po.AdminPermissionPo;
import com.wei.admin.pe.AdminPermissionTypeEnum;
import com.wei.util.DateTimeUtil;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class AccountPermissionItem {
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限所属菜单ID
     */
    private Integer menuId;
    /**
     * 权限唯一标识
     */
    private String key;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限描述
     */
    private String describe;
    /**
     * 权限类型
     */
    private String type;
    private String typeText;
    /**
     * 重定向地址
     */
    private String redirect;

    private String createTime;
    private String modifyTime;


    public void setTypeText() {
        this.typeText = AdminPermissionTypeEnum.getByValue(this.type).getText();
    }

    public AccountPermissionItem setAccountPermissionItem(AdminPermissionPo adminPermissionPo) {
        this.setId(adminPermissionPo.getId());
        this.setMenuId(adminPermissionPo.getMenuId());
        this.setKey(adminPermissionPo.getKey());
        this.setName(adminPermissionPo.getName());
        this.setDescribe(adminPermissionPo.getDescribe());
        this.setType(adminPermissionPo.getType());
        this.setTypeText();
        this.setRedirect(adminPermissionPo.getRedirect());
        this.setCreateTime(adminPermissionPo.getCreateTime().format(DateTimeUtil.DATE_TIME_FORMATTER));
        this.setModifyTime(adminPermissionPo.getModifyTime().format(DateTimeUtil.DATE_TIME_FORMATTER));
        return this;
    }
}
