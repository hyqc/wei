package com.wei.core.component;

import com.wei.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 注入service
 * @author Administrator
 */
@Component
public class ApiInterceptorAdapter extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;
}
