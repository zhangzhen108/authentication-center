package com.zz.authentication.center.dto.param.auth;

import com.zz.authentication.center.dto.result.auth.RoleUserDTO;
import lombok.Data;

import java.util.Set;

@Data
public class RoleUserParamDTO extends RoleUserDTO {
   Set<Integer> roleIdSet;
}