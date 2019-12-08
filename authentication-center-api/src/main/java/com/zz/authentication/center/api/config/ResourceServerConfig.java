package com.zz.authentication.center.api.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zz.authentication.center.api.authentication.cahe.redis.VcodeManager;
import com.zz.authentication.center.api.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.zz.authentication.center.api.authentication.mobile.SmsCodeFilter;
import com.zz.authentication.center.api.authentication.openid.OpenIdAuthenticationConfig;
import com.zz.authentication.center.api.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资源服务配置类
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);
    /**
     * 不需要认证的请求  http.authorizeRequests()
     *                 //下边的路径放行
     *                 .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui",
     *                         "/swagger-resources","/swagger-resources/configuration/security",
     *                         "/swagger-ui.html","/course/coursebase/**").permitAll()
     *                 .anyRequest().authenticated();
     */
    private static String[] WHITE_LIST = new String[]{"/actuator/health","/login","/v2/api-docs"};
    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    AuthExceptionEntryPoint authExceptionEntryPoint;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
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
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //设置用于解码的非对称加密的公钥
        converter.setVerifierKey(getPubKey());
        //converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        return converter;
    }
    /**converter
     * 本地获取公钥
     * @return
     */
    private String getPubKey() {
        log.info("ResourceServerConfig#getPubKey从本地获取公钥");
        Resource resource = new ClassPathResource("public.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            System.out.println("本地公钥");
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            log.info("ResourceServerConfig#getPubKey从本地获取公钥发生异常,异常信息为", ioe);
            return getKeyFromAuthorizationServer();
        }
    }

    /**
     * 授权中心获取公钥
     * @return
     */
    private String getKeyFromAuthorizationServer() {
        log.info("ResourceServerConfig#getKeyFromAuthorizationServer从授权中心获取公钥");
        ObjectMapper objectMapper = new ObjectMapper();
        String pubKey = new RestTemplate().getForObject(resourceServerProperties.getJwt().getKeyUri(), String.class);
        try {
            Map map = objectMapper.readValue(pubKey, Map.class);
            log.info("ResourceServerConfig#getKeyFromAuthorizationServer从授权中心获取公钥为{}", JSON.toJSONString(map));
            return map.get("value").toString();
        } catch (IOException e) {
            log.info("ResourceServerConfig#getKeyFromAuthorizationServer从授权中心获取公钥发生异常,异常信息为", e);
        }
        return null;
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 前后端分离 不需要csrf
        http.cors().and().csrf().disable()
                //配置order访问控制，必须认证过后才可以访问
                .authorizeRequests().anyRequest().permitAll().and();
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(vcodeManager);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();
        //http.requestCache().requestCache(new NullRequestCache());
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
                .formLogin()
                //登录成功之后的处理
                .successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailureHandler).and()
                .exceptionHandling().authenticationEntryPoint(authExceptionEntryPoint)
                //禁用跨站伪造
                .and().csrf().disable().authorizeRequests().anyRequest().permitAll().and()
                //短信验证码配置
                .apply(smsCodeAuthenticationSecurityConfig)
                //社交登录
                .and().apply(mySocialSecurityConfig)
                //openID登录
                .and().apply(openIdAuthenticationConfig);
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
        config.authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }
    @Bean
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}
