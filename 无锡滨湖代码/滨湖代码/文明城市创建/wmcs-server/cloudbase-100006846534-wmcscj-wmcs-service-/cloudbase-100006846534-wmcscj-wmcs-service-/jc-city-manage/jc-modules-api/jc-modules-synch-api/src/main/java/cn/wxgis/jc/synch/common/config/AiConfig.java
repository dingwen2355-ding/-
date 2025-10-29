package cn.wxgis.jc.synch.common.config;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "ai")
public class AiConfig {

    @ApiModelProperty(value = "配置AI接入数量")
    private Integer count;

    @ApiModelProperty(value = "访问ip")
    private String ip;

    @ApiModelProperty(value = "secretId")
    private String secretId;

    @ApiModelProperty(value = "secretKey")
    private String secretKey;

    @ApiModelProperty(value = "获取网关地址")
    private String gatewayUrl;

    @ApiModelProperty(value = "图片下载地址")
    private String imageDownloadUrl;

}
