package com.wei.core.config;

import com.wei.core.component.UserAttributeResolver;
import com.wei.security.InterceptorUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Administrator
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorUrlsConfig interceptorUrlsConfig;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserAttributeResolver());
    }
}
