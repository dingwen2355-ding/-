package cn.wxgis.jc;

import cn.wxgis.jc.security.annotation.EnableCustomConfig;
import cn.wxgis.jc.security.annotation.EnableJcFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCustomConfig
@EnableJcFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class SyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncApplication.class, args);
        System.out.println("--- 文明城市创建同步数据服务启动成功 start ---");
    }
}
