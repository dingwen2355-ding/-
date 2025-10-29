package com.ruoyi.system.customer.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.customer.CmBaseEntity;
import lombok.Data;

/**
 *
 * @author ruoyi
 */
@Data
public class UploadInfo
{
    /**
     * 相对路劲
     */
    private String path;
    /**
     * 新文件名
     */
    private String newFileName;

    /**
     * 绝对路劲
     */
    private String url;
    /**
     * 原始文件名
     */
    private String originalFilename;


}
