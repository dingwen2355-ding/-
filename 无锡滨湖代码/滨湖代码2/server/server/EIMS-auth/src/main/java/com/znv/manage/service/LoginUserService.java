package com.znv.manage.service;

import com.znv.manage.bean.login.LoginUser;

/**
 * @author zl
 * @ClassName: LoginService
 * @Description:
 * @date 20200206
 */
public interface LoginUserService {

	Boolean loginUserVerify(LoginUser user);
	
}
