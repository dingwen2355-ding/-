package com.znv.manage.service;


import com.znv.manage.bean.user.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LogService {
    public List<Map<String, Object>> queryLog(
            String id,
            String operator,
            String operUserId,
            String operUserName,
            String ip,
            Date time,
            String operatedUserId,
            String operatedUserName,
            String result,
            String detail,
            String userId
    );

    public void insertLog(
            String operator,
            String operUserId,
            String operUserName,
            String ip,
            Date time,
            String operatedUserId,
            String operatedUserName,
            String result,
            String detail
    );
    public void multiInsertLog(
            String operator,
            String operUserId,
            String operUserName,
            String ip,
            Date time,
            List<User> users,
            String result,
            String detail
    );
}
