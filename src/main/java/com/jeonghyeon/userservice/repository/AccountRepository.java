package com.jeonghyeon.userservice.repository;

import com.jeonghyeon.userservice.domain.Account;
import com.jeonghyeon.userservice.repository.querydsl.AccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> , AccountRepositoryCustom {

    Optional<Account> findByAccountId(String accountId);

    Optional<Account> findByAccountRandomId(String accountRandomId);
}
