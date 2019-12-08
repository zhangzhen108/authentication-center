package com.zz.authentication.center.dto.result.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Data
public class TokenUserDTO implements Serializable {
    private static final long serialVersionUID = 3534527592553027415L;
    private Integer userId;
    private String nickName;
    private String userName;
    private Set<String> roles;
    static final String USER_NAME = "userName";
    static final String NICK_NAME = "nickName";
    static final String USER_ID = "userId";
    static final String AUTHORITIES = "authorities";
    public static TokenUserDTO toDTO(Map<String, ?> map) {
        if (map == null) {
            return null;
        } else {
            TokenUserDTO tokenUserDTO = new TokenUserDTO();
            if (map.get(USER_ID) != null) {
                Integer userId = (Integer) map.get(USER_ID);
                tokenUserDTO.setUserId(userId);
            }

            if (map.get(NICK_NAME) != null) {
                tokenUserDTO.setNickName(String.valueOf(map.get(NICK_NAME)));
            }

            if (map.get(USER_NAME) != null) {
                tokenUserDTO.setUserName(String.valueOf(map.get(USER_NAME)));
            }

            if (map.get(AUTHORITIES) != null) {
                List<String> roleList = (List) map.get(AUTHORITIES);
                Set<String> roleSet=new HashSet<>(roleList);
                tokenUserDTO.setRoles(roleSet);
            }
            return tokenUserDTO;
        }
    }
}
