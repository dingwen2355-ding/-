package cn.wxgis.jc.common.result;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 分页
 * @param <T>
 */
@Data
public class PageBean<T> implements Serializable {

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNum = 1;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = 10;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总记录条数
     */
    private Integer totalRecord;

    /**
     * 数据集合
     */
    private List<T> list;

    /**
     * 当前页开始查询下标
     */
    private Integer start;

    /**
     * 当前页结束查询下标
     */
    private Integer end;


    public PageBean(){}

    public PageBean(Integer pageNum, Integer pageSize, Integer totalRecord){
        this.pageNum = pageNum == 0 ? 1 : pageNum;
        this.pageSize = pageSize == 0 ? 10 : pageSize;
        this.totalRecord = totalRecord;
        // 计算总页数
        this.totalPage = this.totalRecord%this.pageSize == 0 ? (this.totalRecord/this.pageSize) : (this.totalRecord/this.pageSize+1);
        // 计算每页的起始下标
        this.start = (this.pageNum - 1) * this.pageSize;
        this.end = this.start + this.pageSize;
    }
}
