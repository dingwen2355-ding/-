package cn.wxgis.jc.synch.common.config;

import cn.wxgis.jc.common.util.Base64Utils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "wmb")
public class WmbConfig {

    @ApiModelProperty(value = "文明办链接地址")
    private String ip;

    @ApiModelProperty(value = "登录账号")
    private String username;

    @ApiModelProperty(value = "滨湖区密码")
    private String password;

}
