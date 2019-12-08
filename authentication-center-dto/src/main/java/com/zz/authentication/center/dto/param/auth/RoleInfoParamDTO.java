package com.zz.authentication.center.dto.param.auth;

import com.zz.authentication.center.dto.result.auth.RoleInfoDTO;
import lombok.Data;

import java.util.Set;

@Data
public class RoleInfoParamDTO extends RoleInfoDTO {

    private Set<Integer> ids;
    private Set<String> codeSet;

    private String nameLike;
}