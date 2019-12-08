package com.zz.authentication.center.api.authentication.mobile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lvhaibao
 * @description
 * @date 2018/12/3 0003 19:24
 */
@Data
@AllArgsConstructor
public class ValidateCode implements Serializable {

    private String code;
    private LocalDateTime expireTime;

    public ValidateCode(String code, int seconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(seconds);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
