package cn.wxgis.jc.synch.wmb.vo;

import lombok.Data;

import java.util.List;

@Data
public class WmbCheckPointListVo {

    private String count;

    private List<WmbCheckPointVo> list;
}
