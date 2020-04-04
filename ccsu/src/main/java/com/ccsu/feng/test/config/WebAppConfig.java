package com.ccsu.feng.test.config;

import com.ccsu.feng.test.filter.ReplaceStreamFilter;
import com.ccsu.feng.test.interceptor.AdminUserLoginInterceptor;
import com.ccsu.feng.test.interceptor.AntiBrushInterceptor;
import com.ccsu.feng.test.interceptor.IntercepterMobile;
import com.ccsu.feng.test.interceptor.UserLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.Filter;

/**
 * @author admin
 * @create 2020-02-09-13:11
 */
@Configuration
@Slf4j
public class WebAppConfig  implements WebMvcConfigurer {


    @Bean
    public HandlerInterceptor getAdminUserLoginInterceptor(){
        return new AdminUserLoginInterceptor();
    }

    @Bean
    public HandlerInterceptor getUserLoginInterceptor(){
        return new UserLoginInterceptor();
    }

    @Bean
    public HandlerInterceptor getAntiBrushInterceptor(){
        return new AntiBrushInterceptor();
    }

    @Bean
    public HandlerInterceptor getIntercepterMobile(){
        return new IntercepterMobile();
    }


    /**
     * 注册过滤器
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(replaceStreamFilter());
        registration.addUrlPatterns("/user/page/login");
        registration.setName("streamFilter");
        return registration;
    }

    /**
     * 实例化StreamFilter
     *
     * @return Filter
     */
    @Bean(name = "replaceStreamFilter")
    public Filter replaceStreamFilter() {
        return new ReplaceStreamFilter();
    }

    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController("/error/404").setViewName("error/404");
        registry.addViewController("/error/500").setViewName("error/500");
        registry.addViewController("/error/mobile").setViewName("error/mobile");

        registry.addViewController("/admin").setViewName("admin/index");
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/login").setViewName("admin/login");
        registry.addViewController("/admin/home").setViewName("admin/home");
        registry.addViewController("/page/login").setViewName("page/login");
        registry.addViewController("/").setViewName("page/index");
        registry.addViewController("/page/register").setViewName("page/register");
        registry.addViewController("/page/loginOut").setViewName("page/loginOut");

        registry.addViewController("/admin/xi/person").setViewName("admin/xi/person");
        registry.addViewController("/admin/xi/weapon").setViewName("admin/xi/weapon");
        registry.addViewController("/admin/xi/place").setViewName("admin/xi/place");
        registry.addViewController("/admin/xi/deeds").setViewName("admin/xi/deeds");
        registry.addViewController("/admin/xi/relation").setViewName("admin/xi/relation");

        registry.addViewController("/admin/san/person").setViewName("admin/san/person");
        registry.addViewController("/admin/san/weapon").setViewName("admin/san/weapon");
        registry.addViewController("/admin/sam/place").setViewName("admin/san/place");
        registry.addViewController("/admin/san/deeds").setViewName("admin/san/deeds");
        registry.addViewController("/admin/san/relation").setViewName("admin/san/relation");

        registry.addViewController("/admin/shui/person").setViewName("admin/shui/person");
        registry.addViewController("/admin/shui/weapon").setViewName("admin/shui/weapon");
        registry.addViewController("/admin/shui/place").setViewName("admin/shui/place");
        registry.addViewController("/admin/shui/deeds").setViewName("admin/shui/deeds");
        registry.addViewController("/admin/shui/relation").setViewName("admin/shui/relation");

        registry.addViewController("/admin/hong/person").setViewName("admin/hong/person");
        registry.addViewController("/admin/hong/weapon").setViewName("admin/hong/weapon");
        registry.addViewController("/admin/hong/place").setViewName("admin/hong/place");
        registry.addViewController("/admin/hong/deeds").setViewName("admin/hong/deeds");
        registry.addViewController("/admin/hong/relation").setViewName("admin/hong/relation");

        //common
        registry.addViewController("/page/common/footer").setViewName("page/common/footer");

        //page
        registry.addViewController("/page/person").setViewName("page/person");
        registry.addViewController("/page/weapon").setViewName("page/weapon");
        registry.addViewController("/page/san/withPicture").setViewName("page/withPicture");

        registry.addViewController("/page/xi/deeds").setViewName("page/xi/deeds");
        registry.addViewController("/page/xi/process").setViewName("page/xi/process");
        registry.addViewController("/page/san/deeds").setViewName("page/san/deeds");
        registry.addViewController("/page/san/place").setViewName("page/san/place");
        registry.addViewController("/page/shui/108").setViewName("page/shui/108");

        registry.addViewController("/page/collect").setViewName("page/collect");
        registry.addViewController("/page/place").setViewName("page/place");
        registry.addViewController("/page/xi/deedsDetail").setViewName("page/xi/deedsDetail");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration loginRegistry = registry.addInterceptor(getAdminUserLoginInterceptor());
        // 拦截路径
        loginRegistry.addPathPatterns("/admin/**");
        loginRegistry.excludePathPatterns("/admin/login");
        loginRegistry.excludePathPatterns("/admin/loginOut");
        loginRegistry.excludePathPatterns("/admin/file/**");

        //page用戶登录
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(getUserLoginInterceptor());
        interceptorRegistration.addPathPatterns("/user/page/getPageUser");
        interceptorRegistration.addPathPatterns("/page/login");

        InterceptorRegistration interceptorRegistration1 = registry.addInterceptor(getAntiBrushInterceptor());
        interceptorRegistration1.addPathPatterns("/**");

        InterceptorRegistration interceptorRegistration2 = registry.addInterceptor(getIntercepterMobile());
        interceptorRegistration2.addPathPatterns("/**");
        interceptorRegistration2.excludePathPatterns("/error/**");

//        // 排除路径
//        loginRegistry.excludePathPatterns("/");
//        loginRegistry.excludePathPatterns("/login");
//        loginRegistry.excludePathPatterns("/register");
//        loginRegistry.excludePathPatterns("/loginout");
//        // 排除资源请求
//        loginRegistry.excludePathPatterns("/common/**");
//        loginRegistry.excludePathPatterns("/admin/**");
    }

}
