package com.znv.manage.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: xx描述xx
 * @date
 */
@Repository
public interface SystemLinkDao {

    List<Map<String, Object>> getSystemLink();

}
