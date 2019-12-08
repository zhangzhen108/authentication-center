package com.zz.authentication.center.api.authentication.social.wexinMiniProgram.api;

import com.zz.authentication.center.api.authentication.social.wexinMiniProgram.model.WeiXinMiniProgramUserInfo;

/**
 * @author lvhaibao
 * @description
 * @date 2019/1/4 0004 9:49
 */
public interface WeiXinMiniProgram {

    WeiXinMiniProgramUserInfo getUserInfo(String openId);
}
