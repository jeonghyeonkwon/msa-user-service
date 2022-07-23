package com.jeonghyeon.userservice.dto.account;

import com.jeonghyeon.userservice.domain.AccountRole;
import lombok.Data;

@Data
public class BasicAccountResponseDto {
    private String uuid;
    private String accountId;

    private String accountName;

    private String accountTel;

    private Long price;

    private String accountRole;
    public BasicAccountResponseDto(
                                   String uuid,
                                   String accountId,
                                   String accountName,
                                   String accountTel,
                                   AccountRole accountRole,
                                   Long price
    ){
        this.uuid = uuid;
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountTel = accountTel;
        this.accountRole = accountRole.getKor();
        this.price = price;
    }
}
