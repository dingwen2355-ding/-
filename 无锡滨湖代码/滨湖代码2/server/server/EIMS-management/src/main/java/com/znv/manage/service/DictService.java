package com.znv.manage.service;

import com.znv.manage.common.bean.Dict;
import com.znv.manage.common.bean.Result;

import java.util.List;

/**
 * TCfgDict表服务接口
 *
 * @author 0049003788
 * @since 2022-11-11 13:40:06
 */
public interface DictService {

    Result insert(Dict dict);

    Result update(Dict dict);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String dictName, Integer dictOrder, String dictType, Integer parentId, Integer page, Integer pageSize);

    List<Dict> dictTree(Integer parentId,Integer typeId);

}
