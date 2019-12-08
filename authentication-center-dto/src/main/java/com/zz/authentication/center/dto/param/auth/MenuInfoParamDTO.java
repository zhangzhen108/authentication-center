package com.zz.authentication.center.dto.param.auth;

import com.zz.authentication.center.dto.result.auth.MenuInfoDTO;
import lombok.Data;

import java.util.Set;
@Data
public class MenuInfoParamDTO extends MenuInfoDTO {
    
    private String nameLike;

    private Set<Integer> idSet;

}