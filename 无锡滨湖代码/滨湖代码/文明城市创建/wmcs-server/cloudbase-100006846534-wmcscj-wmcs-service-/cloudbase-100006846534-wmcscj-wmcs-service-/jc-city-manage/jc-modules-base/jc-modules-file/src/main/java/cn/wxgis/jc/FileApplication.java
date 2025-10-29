package cn.wxgis.jc;

import cn.wxgis.jc.security.annotation.EnableJcFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 文件服务
 */
@EnableJcFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class FileApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(FileApplication.class, args);
        System.out.println("--- 文件服务启动成功 start ---");
    }
}
