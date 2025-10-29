package com.znv.manage.dao;


import com.znv.manage.bean.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LogServiceMapper {
    public List<Map<String, Object>> queryLog(
            @Param(value = "id") String id,
            @Param(value = "operator") String operator,
            @Param(value = "operUserId") String operUserId,
            @Param(value = "operUserName") String operUserName,
            @Param(value = "ip") String ip,
            @Param(value = "time") Date time,
            @Param(value = "startTime") String startTime,
            @Param(value = "endTime") String endTime,
            @Param(value = "operatedUserId") String operatedUserId,
            @Param(value = "operatedUserName") String operatedUserName,
            @Param(value = "result") String result,
            @Param(value = "detail") String detail,
            @Param("userNameList") List<String> userNameList
    );

    public void insertLog(
            @Param(value = "operator") String operator,
            @Param(value = "operUserId") String operUserId,
            @Param(value = "operUserName") String operUserName,
            @Param(value = "ip") String ip,
            @Param(value = "time") Date time,
            @Param(value = "operatedUserId") String operatedUserId,
            @Param(value = "operatedUserName") String operatedUserName,
            @Param(value = "result") String result,
            @Param(value = "detail") String detail
    );

    public void multiInsertLog(
            @Param(value = "operator") String operator,
            @Param(value = "operUserId") String operUserId,
            @Param(value = "operUserName") String operUserName,
            @Param(value = "ip") String ip,
            @Param(value = "time") Date time,
            @Param(value = "users") List<User> users,
            @Param(value = "result") String result,
            @Param(value = "detail") String detail
    );

    List<Map<String, String>> queryLogKinds();

    List<Map<String, Object>> queryLogExport(
            @Param("operUserName") String operUserName,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("operator") String operator
    );

}
