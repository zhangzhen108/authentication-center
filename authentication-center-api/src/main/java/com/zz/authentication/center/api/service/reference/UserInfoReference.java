package com.zz.authentication.center.api.service.reference;

import com.auth.dto.result.UserDTO;
import com.zz.authentication.center.api.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth")
public interface UserInfoReference {

    @GetMapping("/api/user/queryById")
    R<UserDTO> queryById(@RequestParam("userId") Long userId);

    @PostMapping("/api/user/add")
    R<UserDTO> add(@RequestBody UserDTO userDTO);
}
