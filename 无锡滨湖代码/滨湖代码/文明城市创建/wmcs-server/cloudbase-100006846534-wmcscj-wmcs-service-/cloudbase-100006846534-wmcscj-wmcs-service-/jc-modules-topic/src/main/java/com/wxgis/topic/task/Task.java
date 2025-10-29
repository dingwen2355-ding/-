package com.wxgis.topic.task;

import com.wxgis.topic.influx.config.TiConfig;
import com.wxgis.topic.influx.service.TopicService;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@EnableScheduling
@Component
public class Task {


    private final TopicService topicService;

    public Task(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * 从 1分钟 开始每 5 分钟检查一次
     */
    @Scheduled(cron = "0 1/5 * * * ? ")
    public void startTopicClient() {
        log.info(".......startTopicClient.......");
        ConcurrentHashMap<String, ManagedChannel> channelConcurrentHashMap=topicService.getChannelMap();
        log.info("channel count:{}",channelConcurrentHashMap.size());
        if (!channelConcurrentHashMap.contains(TiConfig.topic1)){
            topicService.runTopic1();

        }
        log.info("channel count1:{}",channelConcurrentHashMap.size());

        if (!channelConcurrentHashMap.contains(TiConfig.topic2)){
            topicService.runTopic2();
        }
        log.info("channel count2:{}",channelConcurrentHashMap.size());
        log.info(".......end...startTopicClient....");
    }
}
