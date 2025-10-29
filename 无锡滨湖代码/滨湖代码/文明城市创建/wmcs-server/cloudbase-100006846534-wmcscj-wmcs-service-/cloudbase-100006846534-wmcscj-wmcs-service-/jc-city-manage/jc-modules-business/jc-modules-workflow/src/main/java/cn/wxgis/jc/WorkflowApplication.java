package cn.wxgis.jc;


import cn.wxgis.jc.security.annotation.EnableCustomConfig;
import cn.wxgis.jc.security.annotation.EnableJcFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCustomConfig
@EnableJcFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class WorkflowApplication {


    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
        System.out.println("--- 工作流服务启动成功 start ---");
    }


}
