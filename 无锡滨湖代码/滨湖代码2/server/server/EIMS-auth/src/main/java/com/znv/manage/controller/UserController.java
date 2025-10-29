package com.znv.manage.controller;

import com.znv.manage.bean.user.PowerVO;
import com.znv.manage.bean.user.User;
import com.znv.manage.common.bean.Constant;
import com.znv.manage.common.bean.Precinct;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.common.utils.CusAccessUtil;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.dao.UserServiceMapper;
import com.znv.manage.precinct.service.PrecinctService;
import com.znv.manage.redis.RedisService;
import com.znv.manage.service.LogService;
import com.znv.manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户管理
 *
 * @author ：lilingling
 * @date ：2020-2-28
 */

@Api(tags = "用户管理")
@Controller
@Slf4j
public class UserController {

    @Resource
    RedisService redisService;
    @Resource
    UserService userService;
    @Resource
    LogService logService;
    @Resource
    PrecinctService precinctService;

    @Resource
    UserServiceMapper userServiceMapper;

    @Value("${authServer.address1:http://localhost:8086}")
    private String address1;

    @Value("${authServer.address2:http://localhost:8077}")
    private String address2;

    private static final String ZNV_TOKEN = "12CBD9B708D887A41AFAB97DAC46AAC6";

    @ApiOperation("用户新增")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertUser(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "streetId", required = true) String streetId,
            @RequestParam(value = "departmentId", required = false, defaultValue = "0") String departmentId,
            @RequestParam(value = "roleId") String roleId,
            @RequestParam(value = "precinctTreeId", required = false) String precinctTreeId,
            @RequestParam(value = "gender", required = false, defaultValue = "2") int gender,
            @RequestParam(value = "userType", required = false, defaultValue = "0") int userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false, defaultValue = "0") int userState,
            @RequestParam(value = "errLoginTimes", required = false, defaultValue = "0") int errLoginTimes,
            @RequestParam(value = "updateTime", required = false) String updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "userLevel", required = false, defaultValue = "1") int userLevel,
            @RequestParam(value = "loginClientType", required = false, defaultValue = "1") int loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            HttpServletRequest request,
            HttpSession session
    ) {
        ResultBean<String> ret = new ResultBean<>();

        if (isSpecialChar(userName) || password.length() < 6) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("用户名不能为空，不能含有特殊字符，密码不能小于6位");
            return ret;
        }
        String operationUserid;
        String optId = redisService.getUserId(request.getHeader("Authorization"));
        if (optId != null) {
            operationUserid = optId;
        } else {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("session 失效，请重新登录");
            return ret;
        }
        try {
            // 青岛 去掉权限控制
//            String result=userService.controlPermissionCreatUser(null,operationUserid,roleId,precinctTreeId,streetId);
//            if(null!=result){
//                ret.setResult(ResultBean.FAILED);
//                ret.setRemark(result);
//                return ret;
//            }
            List<User> users = userService.queryUserByName(userName);
            if (users != null && users.size() > 0) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("用户名:" + userName + ",已存在");
                return ret;
            }
            User user = new User(null, userName,
                    password, departmentId, roleId, precinctTreeId,
                    userType + "", employeeId, trueName, mobilePhone, email,
                    phone, address, userState + "", errLoginTimes, updateTime,
                    description, adminUser, fax, gender + "", userLevel + "",
                    loginClientType + "", null, userUrl, systemFlag,
                    DateUtils.getNowTime("yyyy-MM-dd HH:mm:ss"), null, null, operationUserid, streetId);
            userService.insertUser(user);
            ret.addData(Arrays.asList(user.getUserId()));
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
            Map<String, String> map = this.getLogingUserInfo(request);
            if (map.size() != 0) {
                logService.insertLog("create", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), user.getUserId(), user.getUserName(), "0", null);
            }

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("用户编辑")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateUser(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "departmentId", required = false, defaultValue = "0") String departmentId,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "precinctTreeId", required = false) String precinctTreeId,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false) String userState,
            @RequestParam(value = "errLoginTimes", required = false) Integer errLoginTimes,
            @RequestParam(value = "updateTime", required = false) Date updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "userLevel", required = false) String userLevel,
            @RequestParam(value = "loginClientType", required = false) String loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            @RequestParam(value = "streetId", required = false) String streetId,
            HttpServletRequest request,
            HttpSession session
    ) {
        ResultBean<String> ret = new ResultBean<>();
        String operationUserid;
        String optId = redisService.getUserId(request.getHeader("Authorization"));
        if (optId != null) {
            operationUserid = optId;
        } else {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("session 失效，请重新登录");
            return ret;
        }
        if (userName != null && isSpecialChar(userName)) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("用户名不能为空，不能含有特殊字符");
            return ret;
        }
        if (userName != null && password != null && password.length() < 6) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("密码不能小于6位");
            return ret;
        }

//        if (!StringUtils.isEmpty(precinctTreeId)) {
//            String[] precinctTreeIds = precinctTreeId.split(",");
//            String precinctId = precinctTreeIds[0].length() == 6?precinctTreeIds[0]:precinctTreeIds[0].substring(0,9);
//            for (String tempId : precinctTreeIds) {
//                if (!tempId.contains(precinctId)) {
//                    ret.setResult(ResultBean.FAILED);
//                    ret.setRemark("一个用户不能选择多个街镇资源");
//                    return ret;
//                }
//            }
//        }

        List<User> users = userService.queryUserById(userId);
        User user;
        if (CollectionUtils.isEmpty(users)) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("被修改的用户不存在!");
            return ret;
        } else {
            user = users.get(0);
        }
        String roleIds = user.getRoleId();
        //设置departmentId
        if (precinctTreeId != null) {
            int count = 0;
            String[] precinctTreeIds = precinctTreeId.split(",");
            if (Constant.ROLE_ID.get("街道坐席人员").equals(roleIds) || Constant.ROLE_ID.get("街道管理员").equals(roleIds)) {
                departmentId = "3702030250000000000";
            } else if (Constant.ROLE_ID.get("街道科室").equals(roleIds)) {
                List<Precinct> list = precinctService.queryPrecinctByKind(Integer.valueOf(Constant.PRECINCT_KIND.get("科室")), null);
                for (Precinct p : list) {
                    for (int i = 0; i < precinctTreeIds.length; i++) {
                        if (precinctTreeIds[i].equals(p.getPrecinctId())) {
                            departmentId = precinctTreeIds[i];
                            count++;
                        }
                    }
                }

            } else if (Constant.ROLE_ID.get("社区人员").equals(roleIds)) {
                List<Precinct> list = precinctService.queryPrecinctByKind(Integer.valueOf(Constant.PRECINCT_KIND.get("社区")), null);
                for (Precinct p : list
                ) {
                    for (int i = 0; i < precinctTreeIds.length; i++) {
                        if (precinctTreeIds[i].equals(p.getPrecinctId())) {
                            departmentId = precinctTreeIds[i];
                            count++;
                        }
                    }
                }
            } else if (Constant.ROLE_ID.get("网格员").equals(roleIds)) {
                List<Precinct> list = precinctService.queryPrecinctByKind(Integer.valueOf(Constant.PRECINCT_KIND.get("网格")), null);
                for (Precinct p : list
                ) {
                    for (int i = 0; i < precinctTreeIds.length; i++) {
                        if (precinctTreeIds[i].equals(p.getPrecinctId())) {
                            departmentId = precinctTreeIds[i];
                            count++;
                        }
                    }
                }
            }
            if ("0".equals(departmentId)) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("权限与所属部门不对应！");
                return ret;
            }
            if (count > 1) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("只能选择一个所属组织！");
                return ret;
            }
        } else {
            departmentId = user.getDepartmentId();
        }
//        else {
//            ret.setResult(ResultBean.FAILED);
//            ret.setRemark("所属部门不能为空！");
//            return ret;
//        }
        // 青岛 去掉权限控制
//        String result=userService.controlPermissionUpdateUser(userId,operationUserid,StringUtils.isEmpty(roleId)?user.getRoleId():roleId,precinctTreeId);
//        if(null!=result){
//            ret.setResult(ResultBean.FAILED);
//            ret.setRemark(result);
//            return ret;
//        }

        try {
            userService.updateUser(userId, userName,
                    password == null ? null : password,
                    departmentId, roleId, precinctTreeId, userType, employeeId, trueName,
                    mobilePhone, email, phone, address, userState, errLoginTimes, updateTime, description, adminUser,
                    fax, gender, userLevel, loginClientType, userUrl, systemFlag, streetId);

            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

            Map<String, String> map = this.getLogingUserInfo(request);
            if (map.size() != 0) {

                if (trueName != null) {
                    logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "200", "修改真实姓名为:" + trueName);
                }
                if (phone != null) {
                    logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "200", "修改电话为:" + phone);
                }
                if (email != null) {
                    logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "200", "修改邮箱为:" + email);
                }
                if (gender != null) {
                    String genderName = "";
                    if ("0".equals(gender)) {
                        genderName = "男";
                    } else if ("1".equals(gender)) {
                        genderName = "女";
                    } else {
                        genderName = "未知";
                    }

                    logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), userId, user.getUserName(), "200", "修改性别为:" + genderName);
                }

            }

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

    @ApiOperation("用户删除")
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteUsers(
            @RequestParam(value = "userIds") String userIds,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false) String userState,
            @RequestParam(value = "errLoginTimes", required = false) Integer errLoginTimes,
            @RequestParam(value = "updateTime", required = false) Date updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "userLevel", required = false) String userLevel,
            @RequestParam(value = "loginClientType", required = false) String loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            @RequestParam(value = "createTime", required = false) Date createTime,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            String optId = redisService.getUserId(request.getHeader("Authorization"));
            log.info("optId:" + optId);
            // 青岛 去掉权限控制
//            String idsByPrivilege = userService.getUserIdsByPrivilege(optId,userIds);
//            log.info("idsByPrivilege:"+idsByPrivilege);

            List<User> usersToDelete = userService.queryUsers(userIds, null, null, null, null, null, null, null, null, null, null
                    , null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
            log.info("usersToDelete:" + usersToDelete);
//            for (User user:usersToDelete) {
//                if (!idsByPrivilege.contains(user.getUserId())){
//                    ret.setResult(ResultBean.FAILED);
//                    ret.setRemark("权限不足");
//                    return ret;
//                }
//            }
            userService.deleteUsers(userIds, userName, password, departmentId, userType, employeeId, trueName,
                    mobilePhone, email, phone, address, userState, errLoginTimes, updateTime, description, adminUser,
                    fax, gender, userLevel, loginClientType, userUrl, systemFlag, createTime);
            Map<String, String> map = this.getLogingUserInfo(request);
            log.info("map:" + map);
            if (map.size() != 0) {
                logService.multiInsertLog("delete", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), usersToDelete, "0", null);
            }
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.getMessage());
        }
        return ret;
    }

    @ApiOperation("用户查询")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<User> queryUsers(
            @RequestParam(value = "userIds", required = false) String userIds,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "precinctName", required = false) String precinctName,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "userType", required = false) String userType,
            @RequestParam(value = "employeeId", required = false) String employeeId,
            @RequestParam(value = "trueName", required = false) String trueName,
            @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "userState", required = false) String userState,
            @RequestParam(value = "errLoginTimes", required = false) Integer errLoginTimes,
            @RequestParam(value = "updateTime", required = false) Date updateTime,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "adminUser", required = false) String adminUser,
            @RequestParam(value = "fax", required = false) String fax,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "userLevel", required = false) String userLevel,
            @RequestParam(value = "loginClientType", required = false) String loginClientType,
            @RequestParam(value = "userUrl", required = false) String userUrl,
            @RequestParam(value = "systemFlag", required = false) String systemFlag,
            @RequestParam(value = "createTime", required = false) Date createTime,
            @RequestParam(value = "roleId", required = false) String roleId,
            HttpServletRequest request,
            HttpSession session
    ) {
        ResultBean<User> ret = new ResultBean<>();
        try {
            String operationUserid;
            String optId = redisService.getUserId(request.getHeader("Authorization"));
            if (optId != null || ZNV_TOKEN.equals(request.getHeader("Authorization"))) {
                operationUserid = optId;
            } else {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark("session 失效，请重新登录");
                return ret;
            }
//            userIds=userService.getUserIdsByPrivilege(operationUserid,userIds);
            List<User> users = userService.queryUsers(userIds, userName, password, precinctId, departmentId, precinctName, userType, employeeId, trueName,
                    mobilePhone, email, phone, address, userState, errLoginTimes, updateTime, description, adminUser,
                    fax, gender, userLevel, loginClientType, userUrl, systemFlag, createTime, roleId);
            ret.addData(users);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

    @RequestMapping(value = "/queryDepartment", method = RequestMethod.GET)
    @ResponseBody
    public Result queryDepartment(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "precinct_id", required = false) String precinct_id
    ) {
        Result result = new Result();
        try {
            List<Map<String, String>> list = userService
                    .queryDepartment(id, department, type, address, precinct_id);
            result.setData(list);
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.toString());
        }
        return result;
    }

    @RequestMapping(value = "/user/changePasswordByName", method = RequestMethod.GET)
    @ResponseBody
    public void changePasswordByName(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "newPassword", required = true) String newPassword
    ) {
        userServiceMapper.changePasswordByName(userName, newPassword);
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> changePassword(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword,
            @RequestParam(value = "type", required = true) String type,
            HttpServletRequest request
    ) {
        ResultBean<String> ret = new ResultBean<>();
        String optId = redisService.getUserId(request.getHeader("Authorization"));

        try {

            // 青岛 去掉权限控制
//            String userIds = userService.getUserIdsByPrivilege(optId,userId);
//            if (!userIds.contains(userId)){
//                ret.setResult(ResultBean.FAILED);
//                ret.setRemark("权限不足");
//                return ret;
//            }
            if (userId == null || userId.isEmpty()) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark(String.format("用户名不能为空！"));
                return ret;
            }

            if (newPassword == null || newPassword.length() < 6) {
                ret.setResult(ResultBean.FAILED);
                ret.setRemark(String.format("密码不能小于6位"));
                return ret;
            }

            //List<String> str = new ArrayList<>();
            if ("reset".equals(type.toLowerCase())) {
                // 去掉MD5加密
//                userService.changePassword(userName, CommonUtils.MD5(newPassword));

                userService.changePassword(userId, newPassword);
                //同步另外两个系统用户密码
                // syncOtherSystemUserInfo(userName,newPassword);
            } else if ("change".equals(type.toLowerCase())) {
                List<User> users = userService.queryUserByName(userName);
                if (users != null && users.size() > 0 && users.get(0) != null) {
                    if (oldPassword == null || oldPassword.isEmpty()) {
                        ret.setResult(ResultBean.FAILED);
                        ret.setRemark(String.format("旧密码不正确"));
                        return ret;
                    }
                    // 去掉MD5加密
//                    if (!users.get(0).getPassword().toLowerCase().equals(CommonUtils.MD5(oldPassword).toLowerCase())) {
                    if (!users.get(0).getPassword().toLowerCase().equals(CommonUtils.MD5(oldPassword).toLowerCase())) {
                        ret.setResult(ResultBean.FAILED);
                        ret.setRemark(String.format("旧密码不正确"));
                        return ret;
                    }

                    userService.changePassword(userId, CommonUtils.MD5(newPassword));
                    //同步另外两个系统用户密码
                    //  syncOtherSystemUserInfo(userName,CommonUtils.MD5(newPassword));
                } else {
                    ret.setResult(ResultBean.FAILED);
                    ret.setRemark(String.format("%s is not exist!", userName));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
        }
        Map<String, String> map = this.getLogingUserInfo(request);
        if (map.size() != 0) {
            User user = userService.queryUserByName(userName).get(0);
            logService.insertLog("update", map.get("userId"), map.get("userName"), CusAccessUtil.getIpAddress(request), new Date(), user.getUserId(), userName, "200", "修改密码");
        }

        return ret;
    }

    private void syncOtherSystemUserInfo(String userName, String newPassword) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(address1 + "/user/changePasswordByName?userName=" + userName + "&" + "newPassword=" + newPassword, String.class);
        restTemplate.getForObject(address2 + "/user/changePasswordByName?userName=" + userName + "&" + "newPassword=" + newPassword, String.class);
    }

    @RequestMapping(value = "/verifyUserName", method = RequestMethod.GET)
    @ResponseBody
    public Result verifyName(
            @RequestParam(value = "name") String name
    ) {
        Result result = new Result();
        try {
            List<User> list = userService.queryUserByName(name);
            if (list.size() > 0) {
                result.setCode(1);
                result.setMessage("名字已存在");
            }
        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/debug", method = RequestMethod.GET)
    public String debugTset(HttpSession session) {
        session.setAttribute("username", "admin_debug");
        return "redirect:swagger-ui.html";
    }

    public Map<String, String> getLogingUserInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            if (token != null && !token.isEmpty()) {
                String userId = redisService.getUserId(token);
                map.put("userId", userId);
                map.put("userName", "");
                List<User> users = userService.queryUserById(userId);
                if (users.size() > 0) {
                    map.put("userName", users.get(0).getUserName());
                }
            } else {
                log.info("User operation without authorization!");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return map;
    }

    private static boolean isSpecialChar(String str) {
        String regEx = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%+_]|\n|\r|\t|\\s";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public PowerVO getToken(@RequestParam(value = "tockenheader", required = true) String tockenheader) {
        return userService.getToken(tockenheader);
    }

    @ApiOperation("更新用户状态")
    @RequestMapping(value = "/user/updateUserState", method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateUserState(@RequestParam(value = "userId", required = false) String userId,
                                              @RequestParam(value = "userState", required = false) String userState,
                                              @RequestParam(value = "errLoginTimes", required = false) String errLoginTimes) {
        ResultBean<String> ret = new ResultBean<>();
        List<User> users = userService.queryUserById(userId);
        User user;
        if (CollectionUtils.isEmpty(users)) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark("被修改的用户不存在!");
            return ret;
        } else {
            user = users.get(0);
        }
        try {
            if (user.getUserState().equals(Constant.USER_STATE.get("已锁定"))) {
                userState = Constant.USER_STATE.get("正常");
                errLoginTimes = "0";
            }
            if (user.getUserState().equals(Constant.USER_STATE.get("正常"))) {
                userState = Constant.USER_STATE.get("已锁定");
            }
            userService.updateUserState(userId, userState, errLoginTimes);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");
        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());
        }
        return ret;
    }

    @ApiOperation("用户组织路径查询")
    @GetMapping("user/userWithPath")
    @ResponseBody
    public Result userWithPath(@RequestParam(value = "trueName", required = false) String trueName,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        return userService.userWithPath(trueName, page, pageSize);
    }
}
