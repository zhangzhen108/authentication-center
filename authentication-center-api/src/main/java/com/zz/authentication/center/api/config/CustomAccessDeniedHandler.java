package com.zz.authentication.center.api.config;

import com.alibaba.fastjson.JSON;
import com.zz.authentication.center.api.common.ErrorEnum;
import com.zz.authentication.center.api.common.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足错误重写
 */
@Component("customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(R.creatR(ErrorEnum.AUTH_DENIED)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
