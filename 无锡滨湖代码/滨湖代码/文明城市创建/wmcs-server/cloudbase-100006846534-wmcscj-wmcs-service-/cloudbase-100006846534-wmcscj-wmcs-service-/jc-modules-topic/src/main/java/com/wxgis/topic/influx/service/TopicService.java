package com.wxgis.topic.influx.service;

import com.wxgis.topic.vo.WmcsWarnVo;
import io.grpc.ManagedChannel;

import java.util.concurrent.ConcurrentHashMap;

public interface TopicService {

    void setInfluxDataByTopic(WmcsWarnVo warn);

    void runTopic1();

    void runTopic2();

    ConcurrentHashMap<String, ManagedChannel> getChannelMap();

    void getTopicsAsync(String[] topics) throws Exception;
}
