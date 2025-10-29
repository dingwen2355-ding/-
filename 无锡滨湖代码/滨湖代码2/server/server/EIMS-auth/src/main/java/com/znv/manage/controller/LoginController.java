package com.znv.manage.controller;

import com.znv.manage.bean.login.LoginUser;
import com.znv.manage.bean.user.Module;
import com.znv.manage.bean.user.User;
import com.znv.manage.common.bean.Constant;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.common.utils.CusAccessUtil;
import com.znv.manage.common.utils.LoginErrorTimes;
import com.znv.manage.common.utils.VerifyCode;
import com.znv.manage.configuration.LoginConfiguration;
import com.znv.manage.redis.RedisService;
import com.znv.manage.service.AccessGovWeChatService;
import com.znv.manage.service.LogService;
import com.znv.manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lilingling
 * @ClassName: PersonManageController
 * @Description: xxx描述该类主要说明
 * @date 20200206
 */
@Api(tags = "登录控制器")
@RestController
@Slf4j
public class LoginController {

    @Resource
    private AccessGovWeChatService accessGovWeChatService;
    @Resource
    UserService userService;
    @Resource
    RedisService redisService;
    @Resource
    LoginConfiguration loginConfiguration;
    @Resource
    LogService logService;

    protected static final Map<String, String> verifyMap = new ConcurrentHashMap<>();

    @ApiOperation("用户登录")
    @RequestMapping(value = "user/login", method = RequestMethod.POST, consumes = "application/json")
    public ResultBean<Map<String, Object>> login(
            @RequestBody LoginUser user, HttpServletRequest request,
            HttpServletResponse response
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        // 判断错误次数是否大于阈值
//		if (LoginErrorTimes.isMoreThan(10)){
//			ret.setResult(ResultBean.FAILED);
//			ret.setRemark("登录失败:用户已锁定");
//			return ret;
//		}

        // 用户不为空，用户名也不为空
        if (user == null || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("登录失败:用户信息不能为空");
            return ret;
        }

        List<User> users = userService.queryUserByName(user.getUserName());

        // 登录失败添加错误次数
//		if (users == null || users.size()<=0){
//			LoginErrorTimes.addTimes();
//		}else {
//			User userTmp = users.get(0);
//			if (!userTmp.getPassword().equals(user.getPassword())){
//				LoginErrorTimes.addTimes();
//			}
//		}

        if (users != null && users.size() > 0) {
            User userTmp = users.get(0);
            if ((userTmp.getErrLoginTimes() != null && userTmp.getErrLoginTimes() > 20) || userTmp.getUserState().equals(Constant.USER_STATE.get("已锁定"))) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("登录失败:用户已锁定");
                logService.insertLog("login", userTmp.getUserId(), userTmp.getUserName(), CusAccessUtil.getIpAddress(request), new Date(), null, null, "1", null);
                return ret;
            }
            if (userTmp.getPassword().equals(user.getPassword())) {
                //非超级管理员账号登陆的时候streetid不能为空
                if (!"1".equals(userTmp.getRoleId()) && StringUtils.isEmpty(userTmp.getStreetId())) {
                    ret.setResult(ResultBean.FAILED);
                    ret.setRemark("登录失败:当前账号未关联到具体组织，请联系管理员分配！");
                    logService.insertLog("login", userTmp.getUserId(), userTmp.getUserName(), CusAccessUtil.getIpAddress(request), new Date(), null, null, "1", null);
                    return ret;
                }
                //登录成功了就清零错误次数,用户状态改成0
                userService.updateUser(userTmp.getUserId(), null, null, null, null, null, null, null, null, null
                        , null, null, null, null, 0, null, null, null,
                        null, null, null, null, null, null, null);
                String token = user.creatToken();

                // 判断用户是否已经登录，如果已经登录，则踢出上次登录的用户
                Set<String> lastLoginTokenSet = redisService.validateUserLoginState(userTmp.getUserId());
                log.info(String.format("lastLoginToken is %s", lastLoginTokenSet));

                //if (!userTmp.getUserName().equals("admin") && lastLoginTokenSet.size() >= 0)去掉admin用户可以多次登录
                if (!lastLoginTokenSet.isEmpty()) {
                    if (!loginConfiguration.isEnableLoginAtSameTime()) {//lvjingxu add at 20180712
                        for (String key : lastLoginTokenSet) {
                            redisService.logoutByTocken(key);
                            log.info(String.format("logout success: lastLoginToken is %s", key));
                        }
                    }
                }

                response.addHeader("token", token);

                redisService.rememberUserByTocken(token, userTmp.getUserId());
                //admin用户不超时
                if (loginConfiguration.getPersistTokenUsers().contains(userTmp.getUserName())) {
                    redisService.persistToken(token);
                }
                //将用户权限放入redis
                List<Module> modules = userService.queryRelatedModules(userTmp.getUserId());
                Set<String> urls = new HashSet<>();
                for (Module module : modules) {
                    urls.add(module.getModuleUrl());
                }
                redisService.setUserModuleUrlsForcely(userTmp.getUserId(), urls);
                List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("userMessage", userTmp);
//				retMap.put("userName",userTmp.getUserName());
                retMap.put("token", token);
                retList.add(retMap);
                ret.setResult(ResultBean.SUCESS);
                ret.setRemark("登录成功");
                ret.setData(retList);
                LoginErrorTimes.clearTimes();
                logService.insertLog("login", userTmp.getUserId(), userTmp.getUserName(), CusAccessUtil.getIpAddress(request), new Date(), null, null, "200", null);
                return ret;
            } else {
                ret.setResult(ResultBean.FAILED);
                int count = userTmp.getErrLoginTimes() + 1;
                userService.updateUser(userTmp.getUserId(), null, null, null, null, null, null, null, null, null
                        , null, null, null, null, count, null, null, null,
                        null, null, null, null, null, null, null);
                //密码错误20次
                if (count == 20) {
//					Timer timer = new Timer();
//					timer.schedule(new TimerTask() {
//						public void run() {
//							//20分钟清零失败次数
//							userService.updateUser(userTmp.getUserId(),null, null, null, null,null,null, null ,null ,null
//									,null ,null ,null ,null,0,null ,null ,null ,
//									null ,null ,null ,null ,null ,null,null );
//						}
//					}, 1000L * 60 * 20);
//					ret.setRemark("登录失败:用户锁定，密码错误超过5次，请20分钟后再试");
                    userService.updateUser(userTmp.getUserId(), null, null, null, null, null, null, null, null, null
                            , null, null, null, "1", count, null, null, null,
                            null, null, null, null, null, null, null);
                    ret.setRemark("登录失败:用户锁定，密码错误超过20次");
                    logService.insertLog("login", userTmp.getUserId(), userTmp.getUserName(), CusAccessUtil.getIpAddress(request), new Date(), null, null, "1", null);
                } else {
                    ret.setRemark("登录失败:用户密码错误");
                    logService.insertLog("login", userTmp.getUserId(), userTmp.getUserName(), CusAccessUtil.getIpAddress(request), new Date(), null, null, "1", null);

                }
                return ret;
            }
        }
        ret.setResult(ResultBean.FAILED);
        ret.setRemark("登录失败:用户不存在");
        return ret;
    }

    /**
     * logout
     *
     * @param request
     * @return return string logoutSuccess,just remove session user data
     */
    @RequestMapping(value = "user/logout", method = {RequestMethod.POST})
    public ResultBean<Object> logout(HttpServletRequest request) {
        ResultBean<Object> ret = new ResultBean<>();
        String token = request.getHeader("token");
        if (redisService.hasTocken(token)) {
            redisService.logoutByTocken(token);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("退出成功");
            String userId = redisService.getUserId(token);
            List<User> users = userService.queryUsers(userId, null, null, null, null, null, null,
                    null, null, null
                    , null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null);

            if (users != null && users.size() > 0) {
                User userTmp = users.get(0);
                logService.insertLog("logout", userTmp.getUserId(), userTmp.getUserName(), CusAccessUtil.getIpAddress(request), new
                        Date(), null, null, "0", null);
            }

        } else {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("用户未登录");
        }
        return ret;
    }

    /**
     * 安全测试：后端生成验证码
     *
     * @param response
     * @return
     */
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
    public Result getVerificationCode(HttpServletResponse response, HttpServletRequest request) {

        //获取文件输出流
        try (OutputStream os = response.getOutputStream()) {
            int width = 200;
            int height = 69;
            String verificationId = VerifyCode.createToken();
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            String randomText = VerifyCode.drawRandomText(width, height, verifyImg);
            verifyMap.put(verificationId, randomText);
            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别

            File file = new File("D:\\demo\\httpImg\\img.png");
            ImageIO.write(verifyImg, "png", file);
            ImageIO.write(verifyImg, "png", os);//输出图片流
            os.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new Result();

    }

    /**
     * 获取微信端token-》通过code获取用户ticket-》通过ticket获取用户电话-》通过电话获取我方库用户信息-》登录
     *
     * @return com.znv.manage.common.bean.ResultBean
     */
    @ApiOperation("政务微信端登录")
    @GetMapping("/loginByGWC")
    public ResultBean<Map<String, Object>> loginByGWC(@RequestParam(value = "code") String code,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            String userId = accessGovWeChatService.getUserIdByCode(code);
            if (!StringUtils.isEmpty(userId)) {
                String mobile = accessGovWeChatService.getUserMobile(userId);
                if (!StringUtils.isEmpty(mobile)) {
                    //查询到多个时，返回状态为启用的
                    List<User> users = userService.queryUsers(null, null, null, null, null, null, null, null,
                            null, mobile, null, null, null, null, null, null, null,
                            null, null, null, null, null, null, null, null, null);
                    if (!CollectionUtils.isEmpty(users)) {
                        User user = users.get(0);
                        LoginUser loginUser = new LoginUser();
                        loginUser.setUserId(user.getUserId());
                        loginUser.setUserName(user.getUserName());
                        loginUser.setPassword(user.getPassword());
                        return login(loginUser, request, response);
                    } else {
                        ret.setResult(ResultBean.FAILED);
                        ret.setRemark("用户不存在");
                    }
                } else {
                    ret.setResult(ResultBean.FAILED);
                    ret.setRemark("通过对方userId获取手机号失败！");
                }
            } else {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("您暂无权限，请联系管理员！");
            }
        } catch (Exception e) {
            log.error("throw Ex: ", e);
            ret.setRemark("登录失败: 应用接口请求异常！");
        }
        ret.setResult(ResultBean.FAILED);
        return ret;
    }
}
