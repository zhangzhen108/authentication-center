package com.zz.authentication.center.api.service.impl;

import com.auth.dto.result.UserDTO;
import com.zz.authentication.center.api.common.BusinessErrorException;
import com.zz.authentication.center.api.common.ErrorEnum;
import com.zz.authentication.center.api.common.R;
import com.zz.authentication.center.api.domain.login.LoginUser;
import com.zz.authentication.center.api.service.reference.UserInfoReference;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService,SocialUserDetailsService {

    @Resource
    UserInfoReference userInfoReference;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return this.getUserDetails(s);
    }
    private UserDetails getUserDetails(String s){
        return getUserDetail(s);
    }

    /**
     * 获取权限
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthority(List<String> roleInfoDTOList){
        Collection<GrantedAuthority> collection = new HashSet<>();
        if(CollectionUtils.isEmpty(roleInfoDTOList)){
            return collection;
        }
        roleInfoDTOList.parallelStream().forEach(roleInfoDTO -> {
                    collection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            });
        return collection;
    }
    private LoginUser getUserDetail(String s){
        if(s==null){
            throw new UsernameNotFoundException("登录失败");
        }
        UserDTO userDTO= getUserInfo(s);
        if(userDTO==null){
            throw new UsernameNotFoundException("登录失败");
        }
        // 可用性 :true:可用 false:不可用
        boolean enabled = userDTO.getEnabled();
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = true;
        LoginUser userDetailsDO=new LoginUser(userDTO.getUsername(),userDTO.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthority(new ArrayList<>()));
        userDetailsDO.setUserId(String.valueOf(userDTO.getId()));
        return userDetailsDO;
    }
    /**
     * 查询用户
     *
     * 手机及表单登录
     *
     * @param s
     * @return
     */
    private UserDTO getUserInfo(String s){

        return login(s);
    }
    private UserDTO login(String s){
        R<UserDTO> userInfoDTOResponseDTO=userInfoReference.queryById(Long.valueOf(s));
        if(!ErrorEnum.SUCCESS.getCode().equals(userInfoDTOResponseDTO.getCode())){
            throw new BusinessErrorException(ErrorEnum.ERROR);
        }
        UserDTO userDTO=userInfoDTOResponseDTO.getData();
        return userDTO;
    }
    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        return getUserDetail(s);
    }
}
