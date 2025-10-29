package com.znv.manage.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.znv.manage.bean.login.LoginUser;
import com.znv.manage.dao.LoginUserDao;
import com.znv.manage.service.LoginUserService;

/**
 * @author znv
 * @ClassName:
 * @Description: 服务层
 * @date 2018/5/18 14:57
 */
@Service
public class LoginUserServiceImpl implements LoginUserService{

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LoginUserServiceImpl.class);

    /**
     * dao管理类
     */
    @Resource
    private LoginUserDao loginUserDao;

	@Override
	public Boolean loginUserVerify(LoginUser user) {
		int count = loginUserDao.loginUserVerify(user);
		if(count == 1){
			return true;
		}else{
			return false;
		}
	}



}
