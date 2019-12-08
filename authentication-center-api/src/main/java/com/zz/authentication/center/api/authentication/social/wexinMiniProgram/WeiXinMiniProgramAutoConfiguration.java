package com.zz.authentication.center.api.authentication.social.wexinMiniProgram;

import com.zz.authentication.center.api.authentication.social.MyConnectView;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.factory.WeiXinMiniProgramConnectionFactory;
import com.zz.authentication.center.api.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.web.servlet.View;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/4 0004 9:58
 */
@Configuration
@ConditionalOnProperty(prefix = "system.social.wei-xin-mini-program", name = "app-id")
public class WeiXinMiniProgramAutoConfiguration extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinMiniProgramProperties weiXinMiniProgramConfig = securityProperties.getSocial().getWeiXinMiniProgram();
        return new WeiXinMiniProgramConnectionFactory(weiXinMiniProgramConfig.getProviderId(), weiXinMiniProgramConfig.getAppId(),
                weiXinMiniProgramConfig.getAppSecret());
    }

    @Bean({"connect/weiXinMiniProgramConnect", "connect/weiXinMiniProgramConnected"})
    @ConditionalOnMissingBean(name = "weiXinMiniProgramConnectedView")
    public View weiXinMiniProgramConnectedView() {
        return new MyConnectView();
    }
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
    @Override
    public UserIdSource getUserIdSource() {
        // TODO Auto-generated method stub
        return new AuthenticationNameUserIdSource();
    }
}
