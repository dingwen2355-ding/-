package com.znv.manage.service.impl;


import com.znv.manage.bean.user.User;
import com.znv.manage.dao.LogServiceMapper;
import com.znv.manage.dao.UserServiceMapper;
import com.znv.manage.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    LogServiceMapper logServiceMapper;
    
    @Resource
    UserServiceMapper userServiceMapper;

    @Override
    public List<Map<String, Object>> queryLog(String id,
                                              String operator,
                                              String operUserId,
                                              String operUserName,
                                              String ip,
                                              Date time,
                                              String operatedUserId,
                                              String operatedUserName,
                                              String result,
                                              String detail,
                                              String userId) {
    	List<User> list = userServiceMapper.queryUsers(userId,null,null,null, null, null,
    			null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,
                null,null,null, null);
    	String roleId = list.get(0).getRoleId();
    	String userName = list.get(0).getUserName();
        List<String> userNameList = null;
		if ("2".equals(roleId)) {
			userNameList = userServiceMapper.querySameStreetUser(userId);
		} else if ("4".equals(roleId)) {
			userNameList = userServiceMapper.queryBelongToAreaUser(userId);
			userNameList.add(userName);
		} else if ("1".equals(roleId)){
			userNameList = null;
		} else {
			userNameList = new ArrayList();
			userNameList.add(userName);
		}
    	
        return logServiceMapper.queryLog(id, operator,operUserId,operUserName,ip,time,operatedUserId,operatedUserName,result,detail,userNameList);
    }

    @Override
    public void insertLog(String operator,
                          String operUserId,
                          String operUserName,
                          String ip,
                          Date time,
                          String operatedUserId,
                          String operatedUserName,
                          String result,
                          String detail) {
        logServiceMapper.insertLog(operator,operUserId,operUserName,ip,time,operatedUserId,operatedUserName,result,detail);
    }

    public void multiInsertLog(
            String operator,
            String operUserId,
            String operUserName,
            String ip,
            Date time,
            List<User> users,
            String result,
            String detail
    ) {
        logServiceMapper.multiInsertLog(operator,operUserId,operUserName,ip,time,users,result,detail);
    }

}
