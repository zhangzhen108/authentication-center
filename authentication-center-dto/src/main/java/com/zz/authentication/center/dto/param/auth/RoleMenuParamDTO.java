package com.zz.authentication.center.dto.param.auth;

import lombok.Data;

import java.util.Date;
import java.util.Set;
@Data
public class RoleMenuParamDTO {
    
    private Integer id;

    private Integer roleId;

    private Integer menuId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer createBy;

    private Integer updateBy;

    private Set<Integer> ids;

}