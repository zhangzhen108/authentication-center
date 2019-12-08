package com.zz.authentication.center.api.authentication.social.qq;

import com.zz.authentication.center.api.authentication.social.qq.factory.QQConnectionFactory;
import com.zz.authentication.center.api.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/3 0003 11:01
 */
@Configuration
@ConditionalOnProperty(prefix = "system.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;
    @Override
    public UserIdSource getUserIdSource() {
        // TODO Auto-generated method stub
        return new AuthenticationNameUserIdSource();
    }
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
}
