package com.netease.mall.config;

import com.netease.mall.web.interceptor.LoginRequiredInterceptor;
import com.netease.mall.web.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author : francis
 * @description : web相关配置
 * @time : 2018/3/27,12:09
 * @update :
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

    @Value("${img.local.path}")
    private String imgPath;

    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;


    /**
     * 配置所有无数据的页面跳转
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/public").setViewName("public");
    }

    /**
     * 配置拦截器Interceptor
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/api/delete","/logout","/show");
    }

    /**
     * 配置静态资源路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
