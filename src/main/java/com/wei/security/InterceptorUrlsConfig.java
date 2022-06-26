package com.wei.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于配置不需要保护的资源路径
 * @author Administrator
 */
@Data
@ConfigurationProperties(prefix = "interceptor.ignored")
@Component
public class InterceptorUrlsConfig {

    private List<String> urls = new ArrayList<>();

}