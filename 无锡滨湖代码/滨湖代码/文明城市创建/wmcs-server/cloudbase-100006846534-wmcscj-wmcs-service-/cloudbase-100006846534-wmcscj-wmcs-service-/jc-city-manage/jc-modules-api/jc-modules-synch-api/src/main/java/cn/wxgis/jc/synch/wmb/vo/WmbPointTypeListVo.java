package cn.wxgis.jc.synch.wmb.vo;

import lombok.Data;

import java.util.List;

@Data
public class WmbPointTypeListVo {

    private String count;

    private List<WmbPointTypeVo> list;
}
