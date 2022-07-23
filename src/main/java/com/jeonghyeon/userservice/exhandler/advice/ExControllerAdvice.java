package com.jeonghyeon.userservice.exhandler.advice;

import com.google.gson.Gson;
import com.jeonghyeon.userservice.dto.ErrorMessageDto;
import com.jeonghyeon.userservice.dto.ResponseDto;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseDto illegalStateException(IllegalStateException e){
        log.error("[IllegalStateException] message -> " + e);
        return new ResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.FeignClientException.class)
    public ResponseDto feignException(FeignException.FeignClientException e){
        log.error("[FeignException] message -> "+e);
        return new ResponseDto<>(HttpStatus.BAD_REQUEST.value(),"잘못된 요청입니다. 다시 확인해 주세요");
    }
}
