package com.zz.authentication.center.api.config;

import com.zz.authentication.center.api.authentication.cahe.redis.VcodeManager;
import com.zz.authentication.center.api.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zz.authentication.center.api.authentication.mobile.SmsCodeFilter;
import com.zz.authentication.center.api.authentication.openid.OpenIdAuthenticationConfig;
import com.zz.authentication.center.api.common.constants.FromLoginConstant;
import com.zz.authentication.center.api.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by dujinkai on 2018/8/6.
 * 安全配置类
 */
@Order(90)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private OpenIdAuthenticationConfig openIdAuthenticationConfig;
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;
    /**
     * 注入加密工具类
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 注入查询用户接口
     */
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private VcodeManager vcodeManager;
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    AuthExceptionEntryPoint authExceptionEntryPoint;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        // 存储内存
//		center.inMemoryAuthentication().withUser("demo").password("123").roles("USER")
//				.and().withUser("buxiaoxia").password("123").roles("ADMIN");
        // 使用自定义认证用户信息
        auth.userDetailsService(userDetailsService)
        // 使用MD5加密校验
        //.passwordEncoder(new Md5PasswordEncoder())
        ;
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
    @Autowired
    public void config(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        //设置UserDetailsService以及密码规则
        authenticationManagerBuilder
                .userDetailsService(userDetailsService);
               // .passwordEncoder(passwordEncoder());
    }
}

