package com.znv.manage.dao;


import com.znv.manage.common.bean.ZdzskjcsjBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface ZdzskjcsjDao {

    int insertZdzskjcsjList(@Param("list") List<ZdzskjcsjBean> resultList);

    int updateZdzskjcsjList(@Param("list") List<ZdzskjcsjBean> resultList);

    int deleteZdzskjcsjList();

    int queryZdzskjcsjList(@Param("SJWXHXPMC") String SJWXHXPMC, @Param("RKSJ") String RKSJ);
}
