package cn.wxgis.jc.screen.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictDataMapper {

    /**
     * 查询字典key列表
     */
    List<String> getDictKey(@Param("code") String code);
}
