package com.zz.authentication.center.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * Created by dujinkai on 2018/8/6.
 * 认证服务配置类
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用特定的方式存储client detail
        clients.withClientDetails(clientDetails());
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        //指定token存储位置
        endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource))
                .userDetailsService(userDetailsService);

//        endpoints
//                //指定token存储位置
//                .tokenStore(tokenStore())
//                // 配置JwtAccessToken转换器
//                .accessTokenConverter(accessTokenConverter())
////                .tokenEnhancer(tokenEnhancerChain)
//                //指定认证管理器,当你选择了资源所有者密码（password）授权类型的时候，需设置这个属性注入一个 AuthenticationManager 对象。
//                .authenticationManager(authenticationManager)
//                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setAccessTokenValiditySeconds(60*1);
//         tokenServices.setRefreshTokenValiditySeconds(60*2);
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setReuseRefreshToken(false);
//        tokenServices.setTokenEnhancer(tokenEnhancerChain);
//        tokenServices.setAuthenticationManager(authenticationManager);
//        tokenServices.setTokenStore(tokenStore());
        //endpoints.tokenServices(defaultAuthorizationServerTokenServices());
        super.configure(endpoints);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
        security.tokenKeyAccess("permitAll()");
        security.allowFormAuthenticationForClients();
    }


    /**
     * 定义clientDetails存储的方式-》Jdbc的方式，注入DataSource
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    /**
     * 指定token存储的位置
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
//        converter.setAccessTokenConverter(new CustomerAccessTokenConverter());
        return converter;
    }
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setAccessTokenValiditySeconds(60*10);
        defaultTokenServices.setRefreshTokenValiditySeconds(60*20);
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;

    }
}
