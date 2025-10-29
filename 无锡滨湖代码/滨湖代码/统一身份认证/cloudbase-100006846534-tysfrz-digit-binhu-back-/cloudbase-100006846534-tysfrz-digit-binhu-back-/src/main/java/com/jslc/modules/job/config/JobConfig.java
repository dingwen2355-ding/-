package com.jslc.modules.job.config;

import com.jslc.modules.job.service.impl.ScheduleJobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author lc
 * @title: JobConfig
 * @projectName digit-binhu-back
 * @description: TODO
 * @date 2023/9/14 9:18
 */
@Configuration
public class JobConfig {
    @Autowired
    ScheduleJobServiceImpl scheduleJobService;

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,@Qualifier("updateJobAdapter") MessageListenerAdapter updateJobAdapter,@Qualifier("deleteJobAdapter") MessageListenerAdapter deleteJobAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //可以添加多个 messageListener
        container.addMessageListener(updateJobAdapter,new PatternTopic("updateJob"));
        container.addMessageListener(deleteJobAdapter,new PatternTopic("deleteJob"));
        return container;
    }


    @Bean("updateJobAdapter")
    public MessageListenerAdapter updateJobListenerAdapter(){
        return new MessageListenerAdapter(scheduleJobService,"updateJob");
    }

    @Bean("deleteJobAdapter")
    public MessageListenerAdapter deleteJobListenerAdapter(){
        return new MessageListenerAdapter(scheduleJobService,"deleteJob");
    }
}
