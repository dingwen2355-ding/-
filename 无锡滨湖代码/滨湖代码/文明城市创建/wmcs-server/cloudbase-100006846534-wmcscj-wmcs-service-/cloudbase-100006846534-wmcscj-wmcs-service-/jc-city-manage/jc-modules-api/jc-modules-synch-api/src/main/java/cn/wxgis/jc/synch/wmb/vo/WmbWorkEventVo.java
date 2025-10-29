package cn.wxgis.jc.synch.wmb.vo;

import cn.wxgis.jc.synch.wmb.po.WorkEventFile;
import lombok.Data;

import java.util.List;


@Data
public class WmbWorkEventVo {

    private String address;

    private String canRelpy;

    private String checkDate;

    private String checkInfo;

    private String checkItemName;

    private String checkItemSort;

    private String checkItemid;

    private String checkName;

    private String checkUserid;

    private Integer checkValue;

    private String checkgid;

    private String checkid;

    private String cscore;

    private String departName;

    private String departid;

    private String detailid;

    private String groupName;

    private String groupid;

    private String hasRcheckInfo;

    private String id;

    private String isPublic;

    private String lat;

    private String lng;

    private String planDate;

    private String pointName;

    private String posttime;

    private String recheckDate;

    private String recheckInfo;

    private String recheckUserid;

    private String recheckgid;

    private String regionName;

    private String regionid;

    private String replyDate;

    private String replydate2;

    private String rownumber;

    private String score;

    private String state;

    private String typeName;

    private String typeid;

    private String unrecheckCounts;

    private List<WorkEventFile> files;
}
