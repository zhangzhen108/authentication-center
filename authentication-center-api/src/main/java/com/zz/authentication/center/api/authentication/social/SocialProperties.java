package com.zz.authentication.center.api.authentication.social;

import com.zz.authentication.center.api.authentication.social.qq.QQProperties;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.WeiXinMiniProgramProperties;
import lombok.Data;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/3 0003 10:57
 */
@Data
public class SocialProperties {

    private QQProperties qq = new QQProperties();

    private String filterProcessesUrl = "/auth";

    private WeiXinMiniProgramProperties weiXinMiniProgram = new WeiXinMiniProgramProperties();
}
