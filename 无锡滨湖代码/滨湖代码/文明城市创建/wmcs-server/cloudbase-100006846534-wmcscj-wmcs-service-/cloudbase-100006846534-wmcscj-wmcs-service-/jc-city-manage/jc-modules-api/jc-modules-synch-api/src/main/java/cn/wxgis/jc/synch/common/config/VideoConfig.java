package cn.wxgis.jc.synch.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 视频汇聚配置
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "video.config")
public class VideoConfig {

    /**
     * 访问地址
     */
    private String addr;

    /**
     * winpc
     */
    private String clientType = "winpc";

    /**
     * 测试账号
     */
    private String userName;

    private String password;

}
