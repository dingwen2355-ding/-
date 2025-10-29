package com.wxgis.topic.influx.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.util.DateUtils;
import com.google.common.util.concurrent.ListenableFuture;
import com.wxgis.topic.ReadMsgGrpc;
import com.wxgis.topic.TiMatrixOfficialAppSubscribeSvr;
import com.wxgis.topic.constants.WarnTypeConstant;
import com.wxgis.topic.influx.config.TiConfig;
import com.wxgis.topic.influx.util.JsonUtils;
import com.wxgis.topic.influx.util.Md5Util;
import com.wxgis.topic.vo.Object;
import com.wxgis.topic.vo.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TopicServiceImpl implements TopicService {


    private ConcurrentHashMap<String, ManagedChannel> contains = new ConcurrentHashMap<>();


    @Autowired
    ExecutorService executorService;
    @Resource
    InfluxdbService influxdbService;

    @Async
    @Override
    public void runTopic1() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("=============执行方法1: {}", DateUtils.format(new Date()));
                    getTopic(TiConfig.topic1);
                } catch (Exception e) {
                    log.error("执行方法1 : {}", e);
                }
            }
        });
    }

    @Async
    @Override
    public void runTopic2() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                log.info("=============执行方法2:", DateUtils.format(new Date()));
                try {
                    getTopic(TiConfig.topic2);
                } catch (Exception e) {
                    log.error("执行方法2 : {}", e);
                }
            }
        });
    }

    @Override
    public ConcurrentHashMap<String, ManagedChannel> getChannelMap() {
        return this.contains;
    }


    private void getTopic(String topic) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(TiConfig.ip, TiConfig.port).usePlaintext().build();

        // 存储 channel
        if (!contains.contains(topic)) {
            contains.put(topic, channel);
        }


        ReadMsgGrpc.ReadMsgBlockingStub blockingStub = ReadMsgGrpc.newBlockingStub(channel);
        String username = TiConfig.username;
        String secretId = TiConfig.secretId;
        String secretKey = TiConfig.secretKey;
        String timestamp = TiConfig.timestamp;

        String Signature = Md5Util.md5(Md5Util.md5(secretKey) + timestamp + secretId);

        TiMatrixOfficialAppSubscribeSvr.Msg msg = TiMatrixOfficialAppSubscribeSvr.Msg.newBuilder().setUserName(username).setSecretId(secretId).setSignature(Signature)
                .setTimestamp(timestamp).addTopics(topic).setGroupId("group_id_" + timestamp).setReadHistory(true)
                .build();
        Iterator<TiMatrixOfficialAppSubscribeSvr.Msg> iterator = blockingStub.readTopic(msg);

        while (iterator.hasNext()) {

            // 如果客户端已经关闭 就去除
            if (channel.isShutdown() || channel.isTerminated()) {
                contains.remove(topic);
                return;
            }

            TiMatrixOfficialAppSubscribeSvr.Msg msgData = iterator.next();
            // 解析buff的数据
            ByteBuffer buffer = msgData.getBuffer().asReadOnlyByteBuffer();
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(ByteBuffer.wrap(msgData.getBuffer().toByteArray()));
            buffer.flip();
            // 转buff实体类
            BufferData data = JSONObject.parseObject(charBuffer.toString(), BufferData.class);
            ResultData resultData = data.getData();
            String content = "";
//            String alarmInfo[] = {WarnTypeConstant.jdcwt, WarnTypeConstant.fjdcwt,
//                    WarnTypeConstant.ljtmy, WarnTypeConstant.dw, WarnTypeConstant.wgls, WarnTypeConstant.ztxgg, WarnTypeConstant.ljbl, WarnTypeConstant.sldj, WarnTypeConstant.ltsb};
//            if (StringUtils.isEmpty(resultData) || !Arrays.asList(alarmInfo).contains(resultData.getAlarmEvent())) continue;
            log.info("========" + resultData.getCaptureTime());
            log.info(JSON.toJSONString(resultData));
            if (!StringUtils.isEmpty(resultData.getAlarmInfo())) {
                Map<String, String> alarmInfoKey = resultData.getAlarmInfo();
                String temp = alarmInfoKey.get(resultData.getAlarmEvent());
                Map<String, String> tempMap = JsonUtils.jsonToStringMap(temp);
                if (null == tempMap) continue;
                Alarm alarm = new Alarm();
                alarm.setText(tempMap.get("Text"));
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(tempMap.get("Text"));
                // 处理机动车违停
                if (resultData.getAlarmEvent().equals(WarnTypeConstant.jdcwt)) {
                    List<Object> objectList = JSON.parseArray(JSON.toJSONString(tempMap.get("ObjectList")), com.wxgis.topic.vo.Object.class);
                    if (!StringUtils.isEmpty(tempMap) && !StringUtils.isEmpty(objectList)) {
                        alarm.setObjectList(objectList);
                        com.wxgis.topic.vo.Object obj = objectList.get(0);
                        stringBuffer.append("; 车辆信息: 车牌-" + obj.getCarPlate() + ",车辆类型-" + obj.getCarType() + "车辆颜色-" + obj.getCarColor() + ",车辆品牌-" + obj.getCarBrand());
                    }
                }
                content = stringBuffer.toString();
                resultData.setAlarm(alarm);
            }
            resultData.setOverlying(JSONObject.parseObject(resultData.getOverlyingInfo(), Overlying.class));
            String dataStr = JSON.toJSONString(data);
            System.out.println(dataStr);
            log.info("===" + dataStr);
            if (!StringUtils.isEmpty(resultData)) {
                if (getExist(data.getTimestamp())) continue;
                // 将数据存入influx
                WmcsWarnVo wmcsWarn = new WmcsWarnVo();
                String dataSourceId = resultData.getDataSourceId();
                String channelId = dataSourceId.substring(dataSourceId.indexOf("_") + 1, dataSourceId.length());
                wmcsWarn.setChannelId(channelId);
                wmcsWarn.setContent(content);
                wmcsWarn.setType(resultData.getAlarmEvent());   //算法类型
                wmcsWarn.setTypeName(StringUtils.isEmpty(resultData.getAlarm()) ? "" : resultData.getAlarm().getText());
                wmcsWarn.setCreateTime(resultData.getCaptureTime());    //发生时间
                wmcsWarn.setImages(resultData.getCaptureImage());   //图片
                wmcsWarn.setTimestamp(data.getTimestamp());     //时间戳
                setInfluxDataByTopic(wmcsWarn);
            }

            // for 循环休眠 2 秒
            TimeUnit.SECONDS.sleep(2l);
        }

    }

    /**
     * 异步获取消息
     *
     * @param topics
     * @throws Exception
     */
    public void getTopicsAsync(String[] topics) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(TiConfig.ip, TiConfig.port).usePlaintext().build();
        ReadMsgGrpc.ReadMsgStub readMsgStub = ReadMsgGrpc.newStub(channel);

        String username = TiConfig.username;
        String secretId = TiConfig.secretId;
        String secretKey = TiConfig.secretKey;
        String timestamp;

        String Signature;
        TiMatrixOfficialAppSubscribeSvr.Msg msg;
        List<ListenableFuture<TiMatrixOfficialAppSubscribeSvr.Msg>> listenableFutures = new ArrayList<>();
        for (String topic : topics) {
            timestamp = TiConfig.timestamp;
            Signature = Md5Util.md5(Md5Util.md5(secretKey) + timestamp + secretId);
            msg = TiMatrixOfficialAppSubscribeSvr.Msg.newBuilder().setUserName(username).setSecretId(secretId).setSignature(Signature)
                    .setTimestamp(timestamp).addTopics(topic).setGroupId("group_id_" + timestamp).setReadHistory(true)
                    .build();

            readMsgStub.readTopic(msg, new StreamObserver<TiMatrixOfficialAppSubscribeSvr.Msg>() {


                @Override
                public void onNext(TiMatrixOfficialAppSubscribeSvr.Msg msgData) {
                    log.error("接收到消息了...{}....");

                    try {
                        // 解析buff的数据
                        ByteBuffer buffer = msgData.getBuffer().asReadOnlyByteBuffer();
                        Charset charset = Charset.forName("UTF-8");
                        CharsetDecoder decoder = charset.newDecoder();
                        CharBuffer charBuffer = decoder.decode(ByteBuffer.wrap(msgData.getBuffer().toByteArray()));
                        buffer.flip();
                        // 转buff实体类
                        BufferData data = JSONObject.parseObject(charBuffer.toString(), BufferData.class);
                        ResultData resultData = data.getData();
                        String content = "";
                        log.info("========" + resultData.getCaptureTime());
                        log.info(JSON.toJSONString(resultData));
                        if (!StringUtils.isEmpty(resultData.getAlarmInfo())) {
                            Map<String, String> alarmInfoKey = resultData.getAlarmInfo();
                            String temp = alarmInfoKey.get(resultData.getAlarmEvent());
                            Map<String, String> tempMap = JsonUtils.jsonToStringMap(temp);
                            if (null != tempMap) {
                                Alarm alarm = new Alarm();
                                alarm.setText(tempMap.get("Text"));
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append(tempMap.get("Text"));
                                // 处理机动车违停
                                if (resultData.getAlarmEvent().equals(WarnTypeConstant.jdcwt)) {
                                    List<Object> objectList = JSON.parseArray(JSON.toJSONString(tempMap.get("ObjectList")), com.wxgis.topic.vo.Object.class);
                                    if (!StringUtils.isEmpty(tempMap) && !StringUtils.isEmpty(objectList)) {
                                        alarm.setObjectList(objectList);
                                        com.wxgis.topic.vo.Object obj = objectList.get(0);
                                        stringBuffer.append("; 车辆信息: 车牌-" + obj.getCarPlate() + ",车辆类型-" + obj.getCarType() + "车辆颜色-" + obj.getCarColor() + ",车辆品牌-" + obj.getCarBrand());
                                    }
                                }
                                content = stringBuffer.toString();
                                resultData.setAlarm(alarm);
                            }
                            ;

                        }
                        resultData.setOverlying(JSONObject.parseObject(resultData.getOverlyingInfo(), Overlying.class));
                        String dataStr = JSON.toJSONString(data);
                        System.out.println(dataStr);
                        log.info("===" + dataStr);
                        if (!StringUtils.isEmpty(resultData)) {
                            if (!getExist(data.getTimestamp())) {
                                // 将数据存入influx
                                WmcsWarnVo wmcsWarn = new WmcsWarnVo();
                                String dataSourceId = resultData.getDataSourceId();
                                String channelId = dataSourceId.substring(dataSourceId.indexOf("_") + 1, dataSourceId.length());
                                wmcsWarn.setChannelId(channelId);
                                wmcsWarn.setContent(content);
                                wmcsWarn.setType(resultData.getAlarmEvent());   //算法类型
                                wmcsWarn.setTypeName(StringUtils.isEmpty(resultData.getAlarm()) ? "" : resultData.getAlarm().getText());
                                wmcsWarn.setCreateTime(resultData.getCaptureTime());    //发生时间
                                wmcsWarn.setImages(resultData.getCaptureImage());   //图片
                                wmcsWarn.setTimestamp(data.getTimestamp());     //时间戳
                                setInfluxDataByTopic(wmcsWarn);
                            }
                        }
                    } catch (CharacterCodingException e) {
                        log.error("error {}", e);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    log.error("onError:  {}", throwable);
                }

                @Override
                public void onCompleted() {
                    log.info("onCompleted");
                }
            });
        }
    }
    @Override
    public void setInfluxDataByTopic(WmcsWarnVo wmcsWarn) {
        String measurement = "wmcs";
        Map<String, String> tags = new HashMap<>();
        tags.put("type", wmcsWarn.getType());
        tags.put("channelId", wmcsWarn.getChannelId());
        Map<String, java.lang.Object> fields = new HashMap<>();
        fields.put("images", wmcsWarn.getImages());
        fields.put("typeName", wmcsWarn.getTypeName());
        fields.put("content", wmcsWarn.getContent());
        fields.put("createTime", wmcsWarn.getCreateTime());
        fields.put("timestamp", wmcsWarn.getTimestamp());
        long time = DateUtils.parseMillis(DateUtils.format(DateUtils.parseDate(wmcsWarn.getCreateTime())));
        influxdbService.insert(measurement, tags, fields, time);
    }

    private boolean getExist(String timestamp) {
        String sql = "SELECT * FROM \"wmcs\" WHERE timestamp = '%s'";
        QueryResult query = influxdbService.query(String.format(sql, timestamp));
        List<Map<String, java.lang.Object>> maps = influxdbService.queryResultProcess(query);
        return maps.size() > 0;
    }

    public static void main(String[] args) {
//        String str = "32021100002000000001_32021158001311209687";
//        System.out.println(str.substring(str.indexOf("_") + 1, str.length()));
//        System.out.println(System.currentTimeMillis());
//        System.out.println(DateUtils.parseMillis(DateUtils.format(DateUtils.parseDate("2023-10-25 19:39:37"))));

        String alarmInfo[] = {WarnTypeConstant.jdcwt, WarnTypeConstant.fjdcwt,
                WarnTypeConstant.ljtmy, WarnTypeConstant.dw, WarnTypeConstant.wgls, WarnTypeConstant.ztxgg, WarnTypeConstant.ljbl, WarnTypeConstant.sldj, WarnTypeConstant.ltsb};

        String temp = "5001";
        System.out.println(Arrays.asList(alarmInfo).contains(temp));
    }


}
