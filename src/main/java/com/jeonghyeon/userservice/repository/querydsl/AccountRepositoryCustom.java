package com.jeonghyeon.userservice.repository.querydsl;

import com.jeonghyeon.userservice.dto.account.AccountDetailDto;
import com.jeonghyeon.userservice.dto.account.BasicAccountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AccountRepositoryCustom {
    Page<BasicAccountResponseDto> accountPage(String keyword, Pageable pageable);

    Optional<AccountDetailDto> accountDetail(String uuid);
}
