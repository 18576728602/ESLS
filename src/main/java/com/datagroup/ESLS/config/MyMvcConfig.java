package com.datagroup.ESLS.config;

import com.datagroup.ESLS.component.LoginHandlerInterceptor;
import com.datagroup.ESLS.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

//使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
//@EnableWebMvc   不要接管SpringMVC
// @Configuration 申明这是一个配置类相当于xml配置文件，@Bean表示这是一个Spring管理的bean
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        //浏览器发送 /atguigu 请求来到 success
        registry.addViewController("/atguigu").setViewName("success");
        registry.addViewController("/").setViewName("upload");
        registry.addViewController("/register.html").setViewName("register");
        registry.addViewController("/tables.html").setViewName("tables");
    }
    @Bean
    public LoginHandlerInterceptor loginHandlerInterceptor() {
        return new LoginHandlerInterceptor();
    }
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //super.addInterceptors(registry);
        //静态资源；  *.css , *.js
        //SpringBoot已经做好了静态资源映射
        registry.addInterceptor(loginHandlerInterceptor())
                .excludePathPatterns("/index.html", "/", "/admin/login","/asserts/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/asserts/**")
                .addResourceLocations("classpath:/static/asserts");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean // 将组件注册在容器
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}
