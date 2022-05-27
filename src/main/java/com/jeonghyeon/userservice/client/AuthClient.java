package com.jeonghyeon.userservice.client;

import com.jeonghyeon.userservice.dto.FeignResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthClient {


    @GetMapping("/authentication-check")
    String headerCheck(@RequestHeader("Authorization")String token);

    @GetMapping("/admin-check")
    FeignResponseDto<String> adminCheck(@RequestHeader("Authorization")String token);

    @GetMapping("/user-info-check")
    FeignResponseDto<String> userInfoCheck(@RequestHeader("Authorization")String token);
}
