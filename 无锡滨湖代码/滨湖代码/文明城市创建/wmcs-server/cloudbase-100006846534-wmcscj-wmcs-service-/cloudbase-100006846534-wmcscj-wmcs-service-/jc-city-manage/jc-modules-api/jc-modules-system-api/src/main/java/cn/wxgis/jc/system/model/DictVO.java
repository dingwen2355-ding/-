package cn.wxgis.jc.system.model;

import lombok.Data;

import java.util.List;

@Data
public class DictVO {

    private String code;

    private String name;

    private List<DictDataVO> dict;
}
