package com.zz.authentication.center.dto.result.auth;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class UserInfoDTO{

    private Integer id;

    private String username;

    private String password;

    private String nickName;

    private String headImgUrl;

    private String phone;

    private Boolean sex;

    private Boolean enabled;

    private String type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer createBy;

    private Integer updateBy;

    List<RoleInfoDTO> roleInfoDTOList;

    List<MenuInfoDTO> menuInfoDTOList;


}