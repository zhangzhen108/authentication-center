package com.zz.authentication.center.dto.param.auth;

import com.zz.authentication.center.dto.result.auth.UserInfoDTO;
import lombok.Data;

@Data
public class UserInfoParamDTO extends UserInfoDTO {
    private String nickNameLike;
}