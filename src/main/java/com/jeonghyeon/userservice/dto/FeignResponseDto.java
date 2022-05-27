package com.jeonghyeon.userservice.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class FeignResponseDto<T> {
    private int statusCode;
    private T msg;

}
