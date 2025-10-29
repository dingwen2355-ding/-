package cn.wxgis.jc.sync.web.service;

public interface SynchWmbService {

    /**
     * 同步周期数据
     */
    void synchCycle();

    /**
     * 同步点位类型
     */
    void synchCheckPointsType();

    /**
     * 同步点位类型检查项数据
     */
    void synchCheckItem();

    /**
     * 同步检查点位
     */
    void synchCheckPoints();

    /**
     * 事件列表
     */
    void synchEvent();

}
