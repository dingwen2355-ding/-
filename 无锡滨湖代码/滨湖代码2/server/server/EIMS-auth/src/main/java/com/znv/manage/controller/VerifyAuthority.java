package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.bean.user.AuthToken;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.bean.user.User;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.redis.RedisService;
import com.znv.manage.service.PrivilegeService;
import com.znv.manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by lilingling on 2020/5/8.
 */
@Api(tags = "对外鉴权接口")
@Slf4j
@RestController
@RequestMapping("/verifyAuthority")
public class VerifyAuthority {
    @Resource
    RedisService redisService;
    @Resource
    UserService userService;
    @Resource
    PrivilegeService privilegeService;

    /**
     * verify if you have authority
     *
     * @param path
     * @param request
     * @return
     */
    @ApiOperation("验证权限并刷新token有效期")
    @GetMapping("/verifyModuleAuth")
    public String verifyModuleAuth(@RequestParam(value = "path", required = false) String path, HttpServletRequest request) {
        String token = request.getHeader("token");
        return redisService.validateAuthAndUpdateExpireTime(token,path);
    }

    /**
     * query all authority by session
     *
     * @param request
     * @return
     */
    @ApiOperation("根据token查询该用户的模块列表")
    @GetMapping("/queryModuleAuth")
    public ResultBean<String> queryModuleAuth(HttpServletRequest request) {
        ResultBean<String> ret = new ResultBean<>();
        String token = request.getHeader("token");
        String userId = redisService.getUserId(token);
        if (userId != null) {
            try {
                List<String> patterns = redisService.queryModuleAuth(userId);
                ret.addData(patterns);
                ret.setResult(ResultBean.SUCESS);
            } catch (Exception e) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark(e.getMessage());
                log.error(e.toString());
            }
        }else{
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("没有登录");
        }
        return ret;
    }

    /**
     *
     * @return
     */
    @ApiOperation("token生成接口")
    @GetMapping("/getToken")
    public ResultBean<String> getToken() {
        ResultBean<String> ret = new ResultBean<>();
        // 按照uuid生成token
        String token = UUID.randomUUID().toString().replaceAll("-","");
        redisService.rememberUserByTocken(token, DateUtils.getStringDate());
        List<String> dataList = new ArrayList<>();
        dataList.add(token);
        ret.setData(dataList);
        ret.setResult(ResultBean.SUCESS);
        return ret;
    }
    /**
     * refresh valid time
     *
     * @param request
     * @return
     */
    @ApiOperation("刷新token有效期")
    @GetMapping("/refreshExpireTime")
    public boolean refreshExpireTime(HttpServletRequest request) {
        String token = request.getHeader("token");
        return redisService.refreshExpireTime(token);
    }

    @ApiOperation("刷新token有效期")
    @GetMapping("/refreshTokenTime")
    public boolean refreshTokenTime(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return redisService.refreshExpireTime(token);
    }

    /**
     * verify token
     * @return
     */
    @ApiOperation("查询该token有效性")
    @GetMapping("/checkToken")
    public ResultBean<String> checkToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        ResultBean<String> ret = new ResultBean<>();
        // 先查询固定token配置表
        List<AuthToken> authToken= userService.queryAuthToken(token);
        if (!CollectionUtils.isEmpty(authToken)) {
            ret.setResult(ResultBean.SUCESS);
            List<String> dataList = new ArrayList<>();
            dataList.add(authToken.get(0).getDescription());
            ret.setData(dataList);
            ret.setRemark("固定token");
            return ret;
        }
        // 再查询redis中保存的token
        String userId = redisService.getUserId(token);
        if (userId != null) {
            redisService.refreshExpireTime(token);
            ret.setRemark("刷新token成功!");
            ret.setResult(ResultBean.SUCESS);
        }else{
            ret.setRemark("token已过期!");
            ret.setResult(ResultBean.FAILED);
        }
        return ret;
    }

    /**
     * verify token
     *
     * @param token
     * @return
     */
    @ApiOperation("查询该token有效性")
    @GetMapping("/verifyToken")
    public ResultBean<String> verifyToken(@RequestParam(value = "token") String token) {
        ResultBean<String> ret = new ResultBean<>();
        // 先查询固定token配置表
        List<AuthToken> authToken= userService.queryAuthToken(token);
        if (!CollectionUtils.isEmpty(authToken)) {
            ret.setResult(ResultBean.SUCESS);
            List<String> dataList = new ArrayList<>();
            dataList.add(authToken.get(0).getDescription());
            ret.setData(dataList);
            ret.setRemark("固定token");
            return ret;
        }
        // 再查询redis中保存的token
        String userId = redisService.getUserId(token);
        if (userId != null) {
            redisService.refreshExpireTime(token);
            ret.setResult(ResultBean.SUCESS);
        }else{
            ret.setResult(ResultBean.FAILED);
        }
        return ret;
    }
    /**
     * verify token
     *
     * @param request
     * @return
     */
    @ApiOperation("根据token查询用户名")
    @GetMapping("/username")
    public ResultBean<String> getUsernameByToken(HttpServletRequest request) throws Exception {
        String token = request.getHeader("token");
        log.info("getUsernameByToken : " + token);
        String userId = redisService.getUserId(token);
        ResultBean<String> ret = new ResultBean<>();
        if (userId != null) {
            List<User> users = userService.queryUsers(userId,null, null, null, null, null, null, null ,null ,null
                    ,null ,null ,null ,null,null,null ,null ,null ,
                    null ,null ,null ,null ,null ,null ,null, null);
            String username = users.get(0).getUserName();
            ret.setResult(ResultBean.SUCESS);
            List<String> usernameList = new ArrayList<String>();
            usernameList.add(username);
            ret.addData(usernameList);
        }else{
            ret.setResult(ResultBean.FAILED);
        }
        return ret;
    }

    @ApiOperation("查询该userId有权限的资源树 (precinctId以逗号间隔)")
    @RequestMapping(value = "/getPrecinctIdsByUserId",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getPrecinctIdsByUserId(
            @RequestParam(value = "userId", required = true) String userId
    ){
        List<Map<String, Object>>  list=privilegeService.getUserPricinctTree(userId);
        String userPrecinctTree = (list.size()==0?"":list.get(0).get("precinctTreeId").toString());
        JSONObject obj=new JSONObject();
        obj.put("userPrecinctTree",userPrecinctTree);
        return obj;
    }

    @ApiOperation("查询该userId的所有信息")
    @RequestMapping(value = "/user/queryRoleIdByUserId", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<User> queryRoleIdByUserId(@RequestParam(value = "userId", required = false) String userId
    ) {
        ResultBean<User> ret = new ResultBean<>();
        try {
            List<User> user = userService.queryUserById(userId);
            ret.addData(user);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

    @ApiOperation("查询与该userId同属一街镇的用户列表")
    @RequestMapping(value = "/user/querySameStreetUser", method = RequestMethod.GET)
    @ResponseBody
    public Result querySameStreetUser(@RequestParam(value = "userId", required = true) String userId) {
        Result result = new Result();
        try {
            List<String> list = userService.querySameStreetUser(userId);
            result.setData(list);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.toString());
        }
        return result;
    }

    @ApiOperation("查询该userId创建的所有用户列表")
    @RequestMapping(value = "/user/queryBelongToAreaUser", method = RequestMethod.GET)
    @ResponseBody
    public Result queryBelongToAreaUser(@RequestParam(value = "userId", required = true) String userId) {
        Result result = new Result();
        try {
            List<String> list = userService.queryBelongToAreaUser(userId);
            result.setData(list);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.toString());
        }
        return result;
    }

    @ApiOperation("根据token查询用户信息")
    @RequestMapping(value = "/user/queryUserInfoByToken", method = RequestMethod.GET)
    @ResponseBody
    public Result queryUserInfoByToken(@RequestParam(value = "token", required = true) String token) {
        Result ret = new Result();
        String userId = redisService.getUserId(token);
        if (userId != null) {
            try {
                List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
                Map<String,Object> retMap=new HashMap<String,Object>();

                List<User> users = userService.queryUserById(userId);
                if(users.size()!=0){
                    User userTmp = users.get(0);
                    retMap.put("userMessage",userTmp);
                    retMap.put("token",token);
                    retList.add(retMap);
                    ret.setData(retList);
                    ret.setCode(ResultBean.SUCESS);
                }else{
                    ret.setCode(ResultBean.FAILED);
                    ret.setMessage("不存在该用户");
                }
            } catch (Exception e) {
                ret.setCode(ResultBean.FAILED);
                ret.setMessage(e.getMessage());
                log.error(e.toString());
            }
        }else{
            ret.setCode(ResultBean.FAILED);
            ret.setMessage("没有登录或token失效");
        }
        return ret;
    }
}
