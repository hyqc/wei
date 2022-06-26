package com.wei.admin.bo;

import com.wei.admin.po.AdminMenuPo;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Administrator
 */
@Getter
@Setter
public class MenuPagesItem {
    private Integer menuId;
    private Integer pageId;
    private AdminMenuPo adminMenusProp;
}
