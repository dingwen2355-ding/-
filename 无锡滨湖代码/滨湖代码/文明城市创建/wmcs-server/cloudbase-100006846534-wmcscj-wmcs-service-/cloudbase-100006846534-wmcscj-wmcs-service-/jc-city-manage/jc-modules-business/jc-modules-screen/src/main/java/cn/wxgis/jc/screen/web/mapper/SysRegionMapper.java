package cn.wxgis.jc.screen.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRegionMapper {

    /**
     * 查询街镇列表
     */
    List<String> list(@Param("number") Integer number);

    /**
     * 查询部门列表
     */
    List<String> deptList();
}
