package cn.wxgis.jc.common.domain.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value = "分页参数")
@Data
public class PageParam {

    @ApiModelProperty(value = "当前页面")
    private Integer pageNum = 0;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序字段")
    private String orderField = "createTime";

    @ApiModelProperty(value = "是否升序")
    private boolean orderAsc = false;


    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params = new HashMap<>();

}
