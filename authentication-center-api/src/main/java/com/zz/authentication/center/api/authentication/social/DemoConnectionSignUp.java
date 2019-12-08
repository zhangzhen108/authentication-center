package com.zz.authentication.center.api.authentication.social;

import com.auth.dto.result.UserDTO;
import com.zz.authentication.center.api.common.CommonStatusEnum;
import com.zz.authentication.center.api.common.R;
import com.zz.authentication.center.api.common.utils.RandomUtil;
import com.zz.authentication.center.api.service.reference.UserInfoReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvhaibao
 * @description 自定义注册处理
 * @date 2019/1/3 0003 11:24
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserInfoReference userInfoReference;

    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识,当不用@Component时，就用

        //这时候，偷偷给用户添加一条user表，并且返回用户的uin

        //业务需要1
        //当用户直接用QQ登录的时候，不需要提示用户注册，后台直接注册给用户注册
        String uin = RandomUtil.randomString(6);
//        UserInfoParamDTO userInfoParamDTO=new UserInfoParamDTO();
//        userInfoParamDTO.setNickName(connection.getDisplayName());
//        userInfoParamDTO.setPassword("123456");
//        userInfoParamDTO.setUsername(uin);
//        userInfoReference.add(userInfoParamDTO);
        //业务需求2
        //当改用户第一次注册没手机号，就提示用手机号绑定
        UserDTO userDTO=new UserDTO();
        userDTO.setUsername("root"+uin);
        userDTO.setPassword("123456");
        userDTO.setStatus(CommonStatusEnum.enable.getStatus());
        R<UserDTO> userDTOR= userInfoReference.add(userDTO);
        return String.valueOf(userDTOR.getData().getId());
    }
}
