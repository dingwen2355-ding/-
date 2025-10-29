package com.grandlynn.modules.activiti.service.impl;

import com.grandlynn.common.system.api.ISysBaseAPI;
import com.grandlynn.common.system.vo.LoginUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component("myUserDetailsService")
public class MyUserDetailService implements UserDetailsService {

    @Lazy
    @Resource
    private ISysBaseAPI sysBaseAPI;
//    @Autowired
//    private UserInfoMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //用户信息
//        UserInfo user = userMapper.getUserInfoByName(username);
//        if (user == null && "root".equals(username)) {
//            return new User("anonymous", "123456", new ArrayList<>(0));
//        }
//        if (user == null) {
//            throw new RuntimeException("用户不存在");
//        }
//        //用户角色
//        List<String> strings = userMapper.queryUserRoles(username);
        LoginUser user = sysBaseAPI.getUserByName(username);
        if (user == null){
            return null;
        }
        List<String> roles = sysBaseAPI.getRolesByUsername(username);
        return new User(
                username,
                user.getPassword(),
                //角色转换为对应的集合
                roles.stream().map(item-> "GROUP_"+item).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
