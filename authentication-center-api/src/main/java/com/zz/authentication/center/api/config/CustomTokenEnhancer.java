package com.zz.authentication.center.api.config;

import com.zz.authentication.center.api.domain.login.LoginUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.oauth2.provider.token.UserAuthenticationConverter.USERNAME;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    final String USERNAME = "userName";
    final String NICKNAME = "nickName";
    private final String USERID = "userId";
    final String PHONE = "phone";
    private final String CLIENT_CREDENTIALS_GRANT_TYPE= "client_credentials";
    private final String PASSWORD_GRANT_TYPE= "password";
    private final String REFRESH_TOKEN_GRANT_TYPE= "refresh_token";
    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {
//        Map<String, Object> response = new HashMap<>();
//        if (CLIENT_CREDENTIALS_GRANT_TYPE.equals(authentication.getOAuth2Request().getGrantType())) {
//            response.put(USERNAME, authentication.getPrincipal());
//        } else if (PASSWORD_GRANT_TYPE.equals(authentication.getOAuth2Request().getGrantType())) {
//            response.put(USERNAME, ((LoginUser) authentication.getPrincipal()).getUsername());
//            response.put(PHONE, ((LoginUser) authentication.getPrincipal()).getPhone());
//            // spring一个bug  如果是long类型，生成令牌后会自动降级为Integer
//            response.put(USERID, ((LoginUser) authentication.getPrincipal()).getUserId());
//        } else if (REFRESH_TOKEN_GRANT_TYPE.equals(authentication.getOAuth2Request().getGrantType())) {
//            Map<String, Object> map = (Map) authentication.getPrincipal();
//            response.put(USERNAME, map.get(USERNAME));
//            response.put(PHONE, map.get(PHONE));
//            // spring一个bug  如果是long类型，生成令牌后会自动降级为Integer
//            response.put(USERID, map.get(USERID));
//        } else {
//            Map<String, Object> map = (Map) authentication.getPrincipal();
//            response.put(USERNAME, map.get(USERNAME));
//            response.put(PHONE, map.get(PHONE));
//            // spring一个bug  如果是long类型，生成令牌后会自动降级为Integer
//            response.put(USERID, map.get(USERID));
//        }
//        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
//            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
//        }
//        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(response);
//        return accessToken;
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof LoginUser) {
                LoginUser user = (LoginUser) principal;
                HashMap<String, Object> map = new HashMap<>();
                map.put(USERNAME, user.getUsername());
                map.put(USERID, user.getUserId());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            }
        }
        return super.enhance(accessToken, authentication);
    }

}
