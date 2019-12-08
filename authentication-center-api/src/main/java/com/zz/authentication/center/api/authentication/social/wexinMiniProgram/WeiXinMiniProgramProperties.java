package com.zz.authentication.center.api.authentication.social.wexinMiniProgram;

import com.zz.authentication.center.api.authentication.social.SocialBaseProperties;
import lombok.Data;

/**
 * @author lvhaibao
 * @description 自定义微信的服务提供商ID
 * @date 2019/1/4 0004 9:47
 */
@Data
public class WeiXinMiniProgramProperties extends SocialBaseProperties {

    private String providerId = "weiXinMiniProgram";

}
