package com.zz.authentication.center.api.authentication.social.wexinMiniProgram.factory;

import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.WeiXinAccessGrant;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.apapter.WeiXinMiniProgramAdapter;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.api.WeiXinMiniProgram;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.provider.WeiXinMiniProgramServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author lvhaibao
 * @description 创建连接工厂
 * @date 2019/1/4 0004 9:59
 */
public class WeiXinMiniProgramConnectionFactory extends OAuth2ConnectionFactory<WeiXinMiniProgram> {

    /**
     * @param appId
     * @param appSecret
     */
    public WeiXinMiniProgramConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WeiXinMiniProgramServiceProvider(appId, appSecret), new WeiXinMiniProgramAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if (accessGrant instanceof WeiXinAccessGrant) {
            return ((WeiXinAccessGrant) accessGrant).getOpenId();
        }
        return null;
    }

    @Override
    public Connection<WeiXinMiniProgram> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WeiXinMiniProgram>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
    public Connection<WeiXinMiniProgram> createConnection(ConnectionData data) {
        return new OAuth2Connection<WeiXinMiniProgram>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WeiXinMiniProgram> getApiAdapter(String providerUserId) {
        return new WeiXinMiniProgramAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WeiXinMiniProgram> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WeiXinMiniProgram>) getServiceProvider();
    }
}
