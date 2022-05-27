package com.jeonghyeon.userservice.dto;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int statusCode;
    private T msg;
    public ResponseDto(int statusCode,T msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
