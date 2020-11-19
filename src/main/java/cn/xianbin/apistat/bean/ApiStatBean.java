package cn.xianbin.apistat.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xianbin.yang
 * @date 2020/10/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiStatBean {

    private String ip;
    /**
     * 域名
     */
    private String domain;

    /**
     * 接口路径
     */
    private String path;
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    private String start_time;

    /**
     * 不持久化
     */
    private long startTime;

    /**
     * 1-成功; 0-失败
     */
    private int is_success;

    /**
     * 查询参数
     */
    private String query_param;
    /**
     * 耗时: 毫秒
     */
    private long cost_time;

    private String error;

    /**
     * 接口名
     */
    private String api_name;

}
