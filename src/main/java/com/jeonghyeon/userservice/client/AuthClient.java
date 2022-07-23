package com.jeonghyeon.userservice.client;

import com.jeonghyeon.userservice.dto.FeignResponseDto;
import com.jeonghyeon.userservice.dto.RoleChangeRequestDto;
import org.apache.http.auth.AUTH;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthClient {
    public static final String AUTHORIZATION ="Authorization";

    @GetMapping("/authentication-check")
    String headerCheck(@RequestHeader(AUTHORIZATION) String token);

    @GetMapping("/admin-check")
    FeignResponseDto<String> adminCheck(@RequestHeader(AUTHORIZATION) String token);

    @GetMapping("/user-info-check")
    FeignResponseDto<String> userInfoCheck(@RequestHeader(AUTHORIZATION) String token);

    @PostMapping("/role-change")
    FeignResponseDto<String> roleChange(@RequestBody RoleChangeRequestDto dto);
}
