package com.zz.authentication.center.api.authentication.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/2 0002 10:56
 */
public interface ValidateCodeGenerator {

    ValidateCode createImageCode(HttpServletRequest request, HttpServletResponse response);
}
