package com.jeonghyeon.userservice.dto.account;

import com.jeonghyeon.userservice.domain.AccountRole;
import lombok.Data;

@Data
public class AccountDetailDto {

    private Long id;

    private String accountRandomId;

    private String accountId;

    private String accountName;

    private String accountTel;

    private Long price;

    private String accountRole;

    private String zipCode;

    private String detail;

    public AccountDetailDto(Long id,
                            String accountRandomId,
                            String accountId,
                            String accountName,
                            String accountTel,
                            Long price,
                            AccountRole accountRole,
                            String zipCode,
                            String detail){
        this.id = id;
        this.accountRandomId = accountRandomId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountTel = accountTel;
        this.price = price;
        this.accountRole = accountRole.getKor();
        this.zipCode = zipCode;
        this.detail = detail;
    }

}
