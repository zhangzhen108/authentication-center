package com.zz.authentication.center.api.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zz.authentication.center.api.common.R;
import com.zz.authentication.center.api.domain.login.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author lvhaibao
 * @description 当用户登录成功之后做的处理
 * @date 2019/1/8 0008 10:06
 */
@Component
@Slf4j
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("defaultAuthorizationServerTokenServices")
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

                log.info("登录成功之后的处理");
        String type = request.getHeader("Accept");
        if(!type.contains("text/html")){

            String clientId = "client_1";
            String clientSecret = "$2a$10$ijOPEDarOjkdahi3xpslIu6.cMpBVqYWpbGTkCh0h7Kjt4.NWQwkK";

            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
       // clientSecret=clientDetails.getClientSecret();
            if (null == clientDetails) {
                throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
            } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
                throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
            }

            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(1), clientId, clientDetails.getScope(), "custom");

            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(R.ok(token)));
        }else {
            super.onAuthenticationSuccess(request, response, authentication);
        }





    }
}
