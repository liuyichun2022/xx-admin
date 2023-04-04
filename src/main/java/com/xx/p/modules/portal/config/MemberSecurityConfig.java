package com.xx.p.modules.portal.config;

import com.xx.p.modules.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * emoji-security模块相关配置
 */
@Configuration
public class MemberSecurityConfig {

    @Autowired
    private UmsMemberService umsMemberService;

    @Bean("memberDetailService")
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> umsMemberService.loadUserByUsername(username);
    }
}
