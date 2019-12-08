package com.zz.authentication.center.api.authentication.social;

public class SocialBaseProperties {
    private String appId;
    private String appSecret;

    public SocialBaseProperties() {
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
