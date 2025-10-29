package com.grandlynn.spa.catalogue;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy(
   proxyTargetClass = true
)
@Import({FdfsClientConfig.class})
@EnableOpenApi
public class DataCatalogueApplication {
   public static void main(String[] args) {
      SpringApplication.run(DataCatalogueApplication.class, args);
   }
}
