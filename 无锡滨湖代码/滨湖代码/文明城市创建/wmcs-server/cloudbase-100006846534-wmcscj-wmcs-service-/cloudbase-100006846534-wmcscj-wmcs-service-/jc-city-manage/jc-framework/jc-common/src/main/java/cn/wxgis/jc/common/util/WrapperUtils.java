package cn.wxgis.jc.common.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


/**
 * @ClassName WrapperUtils
 * @Description 查询工具
 * @Version 1.0
 */
public class WrapperUtils {

    public static QueryWrapper<?> constructCreateTimeQueryWrapper(String beginTime, String endTime, String selectParam) {
        QueryWrapper queryWrapper = new QueryWrapper();
        boolean beginDate = StringUtils.isNotBlank(beginTime) && beginTime.length() > 0;
        boolean endDate = StringUtils.isNotBlank(endTime) && endTime.length() > 0;
        queryWrapper.ge(beginDate, selectParam, StringUtils.isNotBlank(beginTime) ? DateUtils.parseStringToBeginDate(beginTime) : beginTime)
                .le(endDate, selectParam, StringUtils.isNotBlank(endTime) ? DateUtils.parseStringToEndDate(endTime) : endTime);
        return queryWrapper;
    }

}
