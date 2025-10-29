package cn.wxgis.jc.screen.web.mapper;

import cn.wxgis.jc.civilization.data.po.DataCheckUser;
import cn.wxgis.jc.common.util.page.QueryExt;
import cn.wxgis.jc.screen.domain.vo.points.CheckPointsTypeVo;
import cn.wxgis.jc.screen.domain.vo.points.CheckPointsVo;
import cn.wxgis.jc.screen.domain.vo.points.CheckTaskVo;
import cn.wxgis.jc.screen.domain.vo.points.CheckUserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckPointsMapper {

    /**
     * 查询点位类型分页列表
     * @param queryExt
     * @param params
     * @return
     */
    IPage<CheckPointsTypeVo> getCheckPointsTypePage(QueryExt<CheckPointsTypeVo> queryExt, @Param("param") Map<String, Object> params);

    /**
     * 查询检查点位列表
     * @param queryExt
     * @param params
     * @return
     */
    IPage<CheckPointsVo> getCheckPointsPage(QueryExt<Object> queryExt, @Param("param") Map<String, Object> params);

    /**
     * 查询巡查员列表
     * @param pointsId 点位id
     */
    List<CheckUserVo> getCheckUserList(@Param("pointsId") String pointsId);

    /**
     * 查询巡查任务
     * @param pointsTypeId 点位类型id
     * @param pointsId 点位id
     */
    List<CheckTaskVo> getCheckTaskList(@Param("pointsTypeId") String pointsTypeId, @Param("pointsId") String pointsId);


    /**
     * 查询点位类型总数
     */
    int getCheckPointsTypeCount();


    /**
     * 查询点位总数
     */
    int getCheckPointsCount();

    /**
     * 查询任务数
     */
    int getEventPointsCount();

    /**
     * 查询检查员信息
     * @param id
     */
    DataCheckUser findCheckUserById(@Param("id") String id);

}
