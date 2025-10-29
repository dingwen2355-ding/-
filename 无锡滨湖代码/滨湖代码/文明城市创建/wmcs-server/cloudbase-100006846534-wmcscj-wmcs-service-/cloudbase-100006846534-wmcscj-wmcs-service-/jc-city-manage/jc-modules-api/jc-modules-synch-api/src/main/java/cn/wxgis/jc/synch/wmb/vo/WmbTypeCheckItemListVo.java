package cn.wxgis.jc.synch.wmb.vo;

import lombok.Data;

import java.util.List;

@Data
public class WmbTypeCheckItemListVo {

    private String count;

    private List<WmbTypeCheckItemVo> list;
}
