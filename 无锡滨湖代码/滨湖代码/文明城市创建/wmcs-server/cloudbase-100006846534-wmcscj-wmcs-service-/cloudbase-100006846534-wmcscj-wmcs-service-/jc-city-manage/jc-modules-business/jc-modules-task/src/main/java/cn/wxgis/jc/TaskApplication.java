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
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
        System.out.println("--- 文明城市创建服务-Task 启动成功 start ---");
    }
}
