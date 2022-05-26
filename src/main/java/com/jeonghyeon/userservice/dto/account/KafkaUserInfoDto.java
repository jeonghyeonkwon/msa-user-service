package com.jeonghyeon.userservice.dto.account;

import lombok.Data;

@Data
public class KafkaUserInfoDto {
    private String accountId;
    private String accountRandomId;
    private String accountName;
    private String accountTel;
    private String zipCode;
    private String detail;
}
