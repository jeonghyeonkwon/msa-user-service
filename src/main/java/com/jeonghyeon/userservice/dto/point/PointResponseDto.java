package com.jeonghyeon.userservice.dto.point;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class PointResponseDto {


    private String pointRandomId;

    private Long requestPrice;

    private String requestDate;
    private String accountId;

    private String accountName;

    public PointResponseDto(String pointRandomId, Long requestPrice, LocalDateTime date,String accountId,String accountName){
        this.pointRandomId = pointRandomId;
        this.requestPrice = requestPrice;
        String format = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.requestDate = format;
        this.accountId = accountId;
        this.accountName = accountName;
    }
}
