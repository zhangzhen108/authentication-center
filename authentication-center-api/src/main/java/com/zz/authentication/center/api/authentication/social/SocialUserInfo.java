package com.zz.authentication.center.api.authentication.social;

import lombok.Data;

/**
 * @author lvhaibao
 * @description
 * @date 2018/12/12 0012 16:45
 */
@Data
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headimg;

}
