package com.jeonghyeon.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthClient {


    @GetMapping("/authentication-check")
    String headerCheck(@RequestHeader("Authorization")String token);
}
