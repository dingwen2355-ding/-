package com.wxgis.topic;


import com.wxgis.topic.influx.config.TiConfig;
import com.wxgis.topic.influx.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import sun.nio.ch.ThreadPool;

@SpringBootApplication
public class TopicApplication implements CommandLineRunner {

    @Autowired
    private TopicService topicService;

    public static void main(String[] args) {
        SpringApplication.run(TopicApplication.class, args);
        System.out.println("--- start ---");
    }

    @Override
    public void run(String... args) throws Exception {
//        topicService.runTopic1();
//        topicService.runTopic2();

        // 异步多 topic
        topicService.getTopicsAsync(new String[]{TiConfig.topic1,TiConfig.topic2});
    }

}
