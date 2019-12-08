package com.zz.authentication.center.api.authentication.social.wexinMiniProgram.apapter;

import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.api.WeiXinMiniProgram;
import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.model.WeiXinMiniProgramUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 *
 * @author lvhaibao
 * @description
 * @date 2019/1/4 0004 9:56
 */
public class WeiXinMiniProgramAdapter implements ApiAdapter<WeiXinMiniProgram> {

    private String openId;

    public WeiXinMiniProgramAdapter() {}

    public WeiXinMiniProgramAdapter(String openId){
        this.openId = openId;
    }
    /**
     * @param api
     * @return
     */
    @Override
    public boolean test(WeiXinMiniProgram api) {
        return true;
    }

    /**
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(WeiXinMiniProgram api, ConnectionValues values) {
        //WeiXinMiniProgramUserInfo profile = api.getUserInfo(openId);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        WeiXinMiniProgramUserInfo profile =new WeiXinMiniProgramUserInfo();
        profile.setOpenid(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(request.getParameter("nickName"));
        values.setImageUrl(request.getParameter("avatarUrl"));
    }

    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(WeiXinMiniProgram api) {
        return null;
    }

    /**
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(WeiXinMiniProgram api, String message) {
        //do nothing
    }
}
