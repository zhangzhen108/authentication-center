package com.zz.authentication.center.dto.result.auth;

import lombok.Data;

import java.util.Date;
@Data
public class MenuInfoDTO {
    private Integer id;

    private Integer parentId;

    private String parentName;

    private String name;

    private String code;

    private String component;

    private String path;

    private String css;

    private Integer sort;

    private Integer type;

    private Integer hidden;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer createBy;

    private Integer updateBy;

}