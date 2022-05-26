package com.jeonghyeon.userservice.exhandler.advice;

import com.google.gson.Gson;
import com.jeonghyeon.userservice.dto.ErrorMessageDto;
import com.jeonghyeon.userservice.exhandler.ErrorResult;
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
    @ExceptionHandler(FeignException.FeignClientException.class)
    public ErrorResult feignException(FeignException.FeignClientException e){




        Gson gson = new Gson();
        ErrorMessageDto errorDto = gson.fromJson(e.contentUTF8(), ErrorMessageDto.class);


        log.error("[FeignException] message -> "+e);
        return new ErrorResult(HttpStatus.BAD_REQUEST.value(),errorDto.getMsg());
    }
}
