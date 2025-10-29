package cn.wxgis.jc.sync.web.service;

import cn.wxgis.jc.synch.result.VideoResultVo;
import cn.wxgis.jc.synch.video.vo.*;

import java.util.List;

public interface SynchVideoService {

    /**
     * 第二次鉴权：获取token
     * @return
     */
    AuthorizeTokenVo authorizeToken();

    /**
     * 会话保活
     */
    VideoResultVo keepalive();

    /**
     * 查询组织结构
     * @param id
     * @return
     */
    List<DeviceTreeVo> deviceTree(String id);

    /**
     * 查询设备列表
     * @param id
     * @return
     */
    List<DeviceVo> deviceList(String id);

    /**
     * 查询通道列表
     * @param id
     * @return
     */
    List<ChannelVo> channelList(String id);

    /**
     * 获取实时监控
     * @param channelId
     * @return
     */
    RealmonitorVo rtsp(String channelId);

    /**
     * 获取实时监控
     * @param channelId
     * @return
     */
    RealmonitorVo flv(String channelId);

    /**
     * 获取实时监控
     * @param channelId
     * @return
     */
    RealmonitorVo  hls(String channelId);



    /**
     * 同步组织数据
     * @param id
     */
    int synchDevice(String id);

    /**
     * 同步通道数据
     * @return
     */
    int synchChannel();


}
