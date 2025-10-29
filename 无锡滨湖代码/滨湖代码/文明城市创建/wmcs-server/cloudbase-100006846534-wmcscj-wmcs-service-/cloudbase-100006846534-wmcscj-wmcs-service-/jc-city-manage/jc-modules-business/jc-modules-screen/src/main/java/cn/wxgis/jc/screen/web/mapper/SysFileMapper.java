package cn.wxgis.jc.screen.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysFileMapper {

    /**
     * 根据id数组查询附件列表
     * @param ids
     * @return
     */
    List<String> listUrlByIds(@Param("ids") List<String> ids);
}
