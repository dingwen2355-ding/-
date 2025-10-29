package com.grandlynn.spa.catalogue.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {
   private static final Integer CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
   private static final Integer MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;
   private static final Integer MAX_WORK_QUEUE_SIZE = 3000;
   private static final String THREAD_NAME = "data-catalogue-thread-%d";

   @Bean
   public ThreadFactory threadFactory() {
      return (new BasicThreadFactory.Builder()).namingPattern("data-catalogue-thread-%d").daemon(false).build();
   }

   @Bean
   public Executor asyncExecutorService() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(CORE_POOL_SIZE);
      executor.setMaxPoolSize(MAXIMUM_POOL_SIZE);
      executor.setQueueCapacity(MAX_WORK_QUEUE_SIZE);
      executor.setThreadNamePrefix("data-catalogue-thread-%d");
      executor.setThreadFactory(this.threadFactory());
      executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
      executor.initialize();
      return executor;
   }
}
