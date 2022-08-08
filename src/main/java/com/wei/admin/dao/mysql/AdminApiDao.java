package com.wei.admin.dao.mysql;

import com.wei.admin.bo.ApiListItem;
import com.wei.admin.dto.ApiListParams;
import com.wei.admin.po.AdminApiPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


/**
 * @author wlp
 * @date 2022/6/25
 **/
@Mapper
@Component
public interface AdminApiDao {
    /**
     * 查询API列表
     * @param params
     * @return
     */
    List<ApiListItem> selectAdminApisList(ApiListParams params);

    /**
     * 添加API
     * @param adminApiPo
     */
    void addAdminApi(AdminApiPo adminApiPo);

    /**
     * 按照接口ID查询接口详情
     * @param id
     * @return
     */
    AdminApiPo findAdminApiById(Integer id);

    /**
     * 更新接口
     * @param adminApiPo
     * @return
     */
    Integer editAdminApi(AdminApiPo adminApiPo);

    /**
     * 删除接口
     * @param id
     */
    void deleteAdminApi(Integer id);

    /**
     * 按照接口ID查询全部的列表
     * @param ids
     * @param enabled
     * @return
     */
    List<AdminApiPo> selectAdminApiAllByIds(@Param("ids")List<Integer> ids,@Param("enabled") Boolean enabled);

    /**
     * 按照权限ID查询接口资源列表
     * @param permissionIds
     * @return
     */
    List<ApiListItem> selectApisByPermissionIds(Set<Integer> permissionIds);

    /**
     * 查询所有的接口列表
     * @return
     */
    List<AdminApiPo> selectAdminApiAll();
}
