package cn.wxgis.jc.file.web.vo;

import lombok.Data;

@Data
public class FileInfoVo {

    private String ExpireTime;

    private String RequestId;

    private String[] URLs;

    private ErrorVo Error;
}

