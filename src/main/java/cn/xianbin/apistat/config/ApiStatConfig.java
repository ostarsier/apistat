package cn.xianbin.apistat.config;

import cn.xianbin.apistat.interceptor.ApiStatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("cn.xianbin.apistat")
@Configuration
public class ApiStatConfig implements WebMvcConfigurer {

    @Autowired
    private ApiStatInterceptor apiStatHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiStatHandlerInterceptor).addPathPatterns("/**");
    }
}
