package com.jslc.common.config;

import io.minio.MinioClient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "minio")
@Slf4j
@Configuration
public class MinioProperties {
    @ApiModelProperty("endPoint是一个URL，域名，IPv4或者IPv6地址")
    private String server;

    @ApiModelProperty("accessKey类似于用户ID，用于唯一标识你的账户")
    private String accessKey;

    @ApiModelProperty("secretKey是你账户的密码")
    private String secretKey;

    @ApiModelProperty("默认存储桶")
    private String bucket;

    @ApiModelProperty("访问地址前缀")
    private String prefix;

    /**
     * 注入minio 客户端
     * @return
     */
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(server)
                .credentials(accessKey, secretKey)
                .build();
    }
}
