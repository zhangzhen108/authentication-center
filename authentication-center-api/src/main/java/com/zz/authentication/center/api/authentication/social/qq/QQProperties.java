package com.zz.authentication.center.api.authentication.social.qq;

import com.zz.authentication.center.api.authentication.social.SocialBaseProperties;
import lombok.Data;

/**
 * @author lvhaibao
 * @description 自定义QQ的服务提供商ID
 * @date 2019/1/3 0003 10:58
 */
@Data
public class QQProperties extends SocialBaseProperties {

    private String providerId = "qq";
}
