package cn.wxgis.jc.system.po;

import cn.wxgis.jc.jpa.domain.comment.ColumnComment;
import cn.wxgis.jc.jpa.domain.comment.TableComment;
import cn.wxgis.jc.jpa.domain.po.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sys_file")
@TableComment("文件表")
public class SysFile extends BasePO {

    @Column(length = 100)
    @ColumnComment("附件类型")
    private String contentType;

    @Column(length = 100)
    @ColumnComment("文件后缀")
    private String fileSuffix;

    @Column(length = 100)
    @ColumnComment("原名称")
    private String originalName;

    @Column(length = 100)
    @ColumnComment("现文件名")
    private String fileName;

    @Column(length = 100)
    @ColumnComment("文件夹名")
    private String folderName;

    @Column(length = 200)
    @ColumnComment("附件存储位置")
    private String url;

    @Column(length = 50)
    @ColumnComment("业务模块")
    private String businessModule;

    @Column(length = 50)
    @ColumnComment("字段名")
    private String fieldName;

    @Column(length = 50)
    @ColumnComment("附件大小")
    private Long fileSize;

}
