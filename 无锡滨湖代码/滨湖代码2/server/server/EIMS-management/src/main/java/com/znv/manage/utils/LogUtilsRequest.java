package com.znv.manage.utils;

import com.znv.manage.bean.user.User;
import com.znv.manage.redis.RedisService;
import com.znv.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class LogUtilsRequest {

    @Resource
    static RedisService redisService;

    @Resource
    static UserService userService;

    ValueOperations<String, String> valueOperations;

    public static Map<String, String> getLogingUserInfo(HttpServletRequest request) {
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


}
