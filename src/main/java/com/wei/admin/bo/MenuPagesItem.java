package com.wei.admin.bo;

import com.wei.admin.po.AdminMenuPo;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class MenuPagesItem {
    private Integer menuId;
    private Integer pageId;
    private AdminMenuPo adminMenusProp;
}
