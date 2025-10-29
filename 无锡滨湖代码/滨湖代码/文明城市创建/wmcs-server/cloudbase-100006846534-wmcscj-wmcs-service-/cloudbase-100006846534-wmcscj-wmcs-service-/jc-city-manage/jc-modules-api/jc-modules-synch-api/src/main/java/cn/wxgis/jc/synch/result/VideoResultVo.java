package cn.wxgis.jc.synch.result;

import lombok.Data;

@Data
public class VideoResultVo {

    /**
     * 保活成功为200
     */
    private String code;

    /**
     * 提示消息
     */
    private String message;

}
