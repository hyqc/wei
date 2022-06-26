package com.wei.security;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务类
 * @author Administrator
 */
public interface DynamicSecurityService {

    /**
     * 加载资源ANT通配符和资源对应MAP
     * @return
     */
    Map<String, ConfigAttribute> loadDataSource();
}