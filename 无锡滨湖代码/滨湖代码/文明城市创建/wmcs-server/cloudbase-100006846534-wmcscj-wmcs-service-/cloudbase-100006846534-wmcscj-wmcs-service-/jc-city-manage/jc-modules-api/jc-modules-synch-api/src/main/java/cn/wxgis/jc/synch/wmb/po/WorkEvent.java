package cn.wxgis.jc.synch.wmb.po;

import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "work_event")
@TableComment("原历史事件")
public class WorkEvent extends BasePO {

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String canRelpy;

    @Column(length = 100)
    private String checkDate;

    @Column(length = 500)
    private String checkInfo;

    @Column(length = 500)
    private String checkItemName;

    @Column(length = 100)
    private String checkItemSort;

    @Column(length = 100)
    private String checkItemid;

    @Column(length = 500)
    private String checkName;

    @Column(length = 100)
    private String checkUserid;

    @Column(length = 500)
    private Integer checkValue;

    @Column(length = 100)
    private String checkgid;

    @Column(length = 100)
    private String checkid;

    @Column(length = 100)
    private String cscore;

    @Column(length = 100)
    private String departName;

    @Column(length = 100)
    private String departid;

    @Column(length = 100)
    private String detailid;

    @Column(length = 100)
    private String groupName;

    @Column(length = 100)
    private String groupid;

    @Column(length = 500)
    private String hasRcheckInfo;

    @Column(length = 100)
    private String id;

    @Column(length = 100)
    private String isPublic;

    @Column(length = 100)
    private String lat;

    @Column(length = 100)
    private String lng;

    @Column(length = 100)
    private String planDate;

    @Column(length = 200)
    private String pointName;

    @Column(length = 100)
    private String posttime;

    @Column(length = 100)
    private String recheckDate;

    @Column(length = 500)
    private String recheckInfo;

    @Column(length = 100)
    private String recheckUserid;

    @Column(length = 100)
    private String recheckgid;

    @Column(length = 200)
    private String regionName;

    @Column(length = 100)
    private String regionid;

    @Column(length = 100)
    private String replyDate;

    @Column(length = 100)
    private String replydate2;

    @Column(length = 100)
    private String rownumber;

    @Column(length = 100)
    private String score;

    @Column(length = 100)
    private String state;

    @Column(length = 200)
    private String typeName;

    @Column(length = 100)
    private String typeid;

    @Column(length = 100)
    private String unrecheckCounts;


}
