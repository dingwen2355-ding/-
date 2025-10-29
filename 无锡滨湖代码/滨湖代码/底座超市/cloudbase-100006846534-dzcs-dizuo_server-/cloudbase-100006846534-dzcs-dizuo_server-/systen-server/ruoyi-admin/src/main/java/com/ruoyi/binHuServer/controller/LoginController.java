package com.ruoyi.binHuServer.controller;

import com.ruoyi.common.config.RestTemplateUtils;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @title: LoginController
 * @date 2023/5/17 9:51
 */
@Controller
@RequestMapping(value = "/api")
@Slf4j
public class LoginController {
    //登录状态，0是未登录状态（根据自己的业务做判断）
    static int loginSatatus = 0;
//    private int loginSatatus = 0;


    private final RestTemplateUtils restTemplateUtils;

    @Autowired
    private TokenService tokenService;

    public LoginController(RestTemplateUtils restTemplateUtils) {
        this.restTemplateUtils = restTemplateUtils;
    }

    @GetMapping("/login")
    public ModelAndView login(String code, HttpServletRequest request) {
        log.error("123123123:"+loginSatatus);
        ModelAndView modelAndView = new ModelAndView();
        //判断用户是否登录系统
        if (loginSatatus == 0) {
            //判断请求是否有授权码，有授权码使用授权码获取token，无授权码重定向到单点服务系统获取授权码
            if (StringUtils.isNotBlank(code)) {
                //根据授权码获取token，授权码只能用一次
                //client_id、client_secret、redirect_uri替换成自己的系统，code为授权码，（详情参考单点登录文档）
                String url = "http://2.22.51.26:30002/oauth/token?grant_type=authorization_code&client_id=c3piaGdldHRva2Vu&client_secret=10470c3b4b1fed12c3baac014be15fac67c6e815&code=" + code + "&redirect_uri=https://2.21.138.89:11511/binhudsj&response_type=code&scope=all";
                // 发起请求获取token
                String token = restTemplateUtils.get(url, String.class);
                System.out.println(token);
                //根据自己的业务处理token信息及保存用户信息供系统使用
                request.getSession().setAttribute("access_token", token);
                //设置登录状态未一登录状态
                loginSatatus = 1;
                modelAndView.setView(new RedirectView("https://2.21.138.89:11511/binhudsj"));
            } else {
                System.out.println("modelAndView.setViewName(\"redirect:http......");
                //重定向到单点登录认证中心获取授权码
                //client_id、client_secret、redirect_uri替换成自己的系统，（详情参考单点登录文档）
                modelAndView.setViewName("redirect:http://2.22.51.26:30002/oauth/authorize?client_id=c3piaGdldHRva2Vu&client_secret=10470c3b4b1fed12c3baac014be15fac67c6e815&redirect_uri=https://2.21.138.89:11511/binhudsj&response_type=code&scope=all");
            }
        } else {
            //用户一登录进入自己的业务系统
            System.out.println("modelAndView.setViewName(\"redirect:toIndex\");......");
            modelAndView.setView(new RedirectView("https://2.21.138.89:11511/binhudsj"));
        }
        return modelAndView;
    }

    @PostMapping("/onLogoutSuccess")
    public void onLogoutSuccess()  {
        log.error("jinlaile:");
        ModelAndView modelAndView = new ModelAndView();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        log.error("renwu:"+loginUser);
        if (com.ruoyi.common.utils.StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            log.error("username:"+userName);
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            log.error("121212:");
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
            log.error("121213:");
        }
        log.error("tiaozhuan111");
        //modelAndView.setView(new RedirectView("http://2.21.137.206:11511/api/login"));
        //return modelAndView;
//        response.sendRedirect("http://2.21.137.206:11511/api/login");
//        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("退出成功")));
    }
}
