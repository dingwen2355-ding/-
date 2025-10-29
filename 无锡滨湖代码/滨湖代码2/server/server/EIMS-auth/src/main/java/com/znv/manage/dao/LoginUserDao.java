package com.znv.manage.dao;

import org.springframework.stereotype.Repository;

import com.znv.manage.bean.login.LoginUser;

/**
 * @author znv
 * @ClassName:
 * @Description: 登录验证dao
 * @date 2018/5/17 11:14
 */
@Repository
public interface LoginUserDao {

    /**
     * 登录验证
     */
	Integer loginUserVerify(LoginUser user);



}
