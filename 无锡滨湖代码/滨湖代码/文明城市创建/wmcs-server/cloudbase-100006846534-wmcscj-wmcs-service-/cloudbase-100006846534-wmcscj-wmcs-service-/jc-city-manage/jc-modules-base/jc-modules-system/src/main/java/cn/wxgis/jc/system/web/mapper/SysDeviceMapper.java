package cn.wxgis.jc.system.web.mapper;

import cn.wxgis.jc.common.web.dao.BaseDao;
import cn.wxgis.jc.system.po.SysDevice;
import cn.wxgis.jc.system.resp.SysDeviceResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysDeviceMapper extends BaseDao<SysDevice> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param queryWrapper 搜索条件
     */
    IPage<SysDeviceResponse> selectPageCustom(IPage<SysDevice> page, @Param(Constants.WRAPPER) Wrapper<SysDeviceResponse> queryWrapper);

}
