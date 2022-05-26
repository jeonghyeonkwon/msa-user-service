package com.jeonghyeon.userservice.exhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private int errorCode;
    private String message;

}
