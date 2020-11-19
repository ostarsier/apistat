package cn.xianbin.apistat.interceptor;

import cn.xianbin.apistat.service.ApiStatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class ApiStatInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiStatService apiStatService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        apiStatService.before(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        apiStatService.after(ex);
        ApiStatService.threadLocal.remove();
    }

}
