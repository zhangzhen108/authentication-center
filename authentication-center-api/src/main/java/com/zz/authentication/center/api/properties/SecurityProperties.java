package com.zz.authentication.center.api.properties;


import com.zz.authentication.center.api.authentication.mobile.ValidateCodeProperties;
import com.zz.authentication.center.api.authentication.social.SocialProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lvhaibao
 * @description
 * @date 2018/11/22 0022 11:54
 */
@ConfigurationProperties(prefix = "system")
@Component
@Data
public class SecurityProperties {


    private OauthPageProperties oauthLogin = new OauthPageProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

}
