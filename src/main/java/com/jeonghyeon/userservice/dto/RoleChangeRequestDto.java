package com.jeonghyeon.userservice.dto;

import lombok.Data;

@Data
public class RoleChangeRequestDto {
    private String accountId;

    private String accountRole;

}
