package com.wei.admin.bo;

import com.wei.admin.po.AdminMenuPo;
import lombok.Data;

import java.util.List;

/**
 * @author wlp
 * @date 2022/6/26
 **/
@Data
public class MenuItem extends AdminMenuPo {
    private Integer level;
    private List<MenuItem> children;
}
