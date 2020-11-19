package cn.xianbin.apistat.service;


import cn.xianbin.apistat.bean.ApiStatBean;
import cn.xianbin.apistat.utils.IpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class ApiStatService {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static ThreadLocal<ApiStatBean> threadLocal = new ThreadLocal<>();

    @Resource(name = "apiStatKafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 业务代码调用
     */
    public static void apiName(String apiName) {
        ApiStatBean apiStatBean = threadLocal.get();
        if(apiStatBean == null){
            log.warn("api stat not init");
            return;
        }
        apiStatBean.setApi_name(apiName);
    }

    public void before(HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        ApiStatBean apiStatBean = ApiStatBean.builder()
                .ip(IpUtil.getIP(request))
                .domain(domain(request))
                .path(request.getRequestURI())
                .query_param(request.getQueryString())
                .startTime(System.currentTimeMillis())
                .start_time(now.format(dateTimeFormatter))
                .build();
        threadLocal.set(apiStatBean);
    }

    public void after(Exception ex) {
        ApiStatBean apiStatBean = threadLocal.get();
        apiStatBean.setCost_time(System.currentTimeMillis() - apiStatBean.getStartTime());
        if (ex == null) {
            apiStatBean.setIs_success(1);
        } else {
            apiStatBean.setError(ex.getMessage());
            apiStatBean.setIs_success(0);
        }
        log();
    }

    public void log() {
        String invokeLog = JSONObject.toJSONString(threadLocal.get());
        log.debug("asyncSend={}", invokeLog);
        kafkaTemplate.send("api_stat_test", invokeLog);
    }

    private String domain(HttpServletRequest request) {
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
    }

}
