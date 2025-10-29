package cn.wxgis.jc.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    /**
     * 服务地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 地址路径
     */
    private String endpoint;

    /**
     * 存储文件桶
     */
    private String bucketName;

    /**
     * 存储富文本桶
     */
    private String bucketNameText;

    @Bean
    public MinioClient getMinioClientFile() {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }

}
