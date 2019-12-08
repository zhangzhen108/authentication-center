package com.zz.authentication.center.api.authentication.social.wexinMiniProgram.provider;

import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.api.WeiXinMiniProgram;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.api.WeiXinMiniProgramImpl;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.template.WeiXinMiniProgramOAuth2Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 *
 * @author lvhaibao
 * @description
 * @date 2019/1/4 0004 10:02
 */
public class WeiXinMiniProgramServiceProvider extends AbstractOAuth2ServiceProvider<WeiXinMiniProgram> {

    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * @param appId
     * @param appSecret
     */
    public WeiXinMiniProgramServiceProvider(String appId, String appSecret) {
        super(new WeiXinMiniProgramOAuth2Template(appId, appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }


    @Override
    public WeiXinMiniProgram getApi(String accessToken) {
        return new WeiXinMiniProgramImpl(accessToken);
    }
}
