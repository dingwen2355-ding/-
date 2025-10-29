package cn.wxgis.jc.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "SysFileUploadRequest", description = "图片上传 [传参]")
@Data
public class SysFileUploadRequest {

    @NotEmpty(message = "文件不能为空")
    @ApiModelProperty(value = "文件")
    private MultipartFile file;

    @ApiModelProperty(value ="业务模块")
    private String businessModule;

    @ApiModelProperty(value ="字段名")
    private String fieldName;

}
