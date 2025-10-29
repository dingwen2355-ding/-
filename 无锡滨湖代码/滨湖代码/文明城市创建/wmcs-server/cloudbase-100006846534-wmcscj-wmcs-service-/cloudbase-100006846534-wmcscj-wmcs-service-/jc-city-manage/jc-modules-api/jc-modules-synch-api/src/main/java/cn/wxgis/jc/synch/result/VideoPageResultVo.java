package cn.wxgis.jc.synch.result;

import lombok.Data;

@Data
public class VideoPageResultVo {

    private String code;

    private String message;

    /**
     * 总条数
     */
    private Integer totalCount;

    /**
     * 下一页编号，-1表示没有下一页
     */
    private Integer nextPage;

    /**
     * 数组成员的类型不同，根据nodeType的值进行解析
     */
    private String results;

}
