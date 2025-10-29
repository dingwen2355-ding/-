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
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("--- 系统运维服务启动成功 start ---");
    }
}
