package cn.wxgis.jc.synch.wmb.vo;

import lombok.Data;

import java.util.List;

@Data
public class WmbCycleListVo {

    private String count;

    private List<WmbCycleVo> list;
}
