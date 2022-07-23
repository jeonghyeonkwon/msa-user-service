package com.jeonghyeon.userservice.service;

import com.jeonghyeon.userservice.client.AuthClient;
import com.jeonghyeon.userservice.domain.Account;
import com.jeonghyeon.userservice.dto.*;
import com.jeonghyeon.userservice.dto.account.AccountDetailDto;
import com.jeonghyeon.userservice.dto.account.BasicAccountResponseDto;
import com.jeonghyeon.userservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AuthClient authClient;

    public ResponseEntity userList(String keyword,Pageable pageable) {
        Page<BasicAccountResponseDto> accountPage = accountRepository.accountPage(keyword, pageable);
        PageListDto dto = new PageListDto(accountPage.getNumber(),accountPage.isFirst(),accountPage.isLast(),accountPage.getTotalPages(),accountPage.getTotalElements(),accountPage.getContent());
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    public ResponseEntity userDetail(String uuid) {
        Optional<AccountDetailDto> opAccountDetail = accountRepository.accountDetail(uuid);
        if(opAccountDetail.isEmpty()){
            return new ResponseEntity(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "잘못된 요청 입니다. 다시 시도해 주세요"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(opAccountDetail.get(),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity roleChange(String uuid, RoleChangeRequestDto dto) {
        Optional<Account> opAccount = accountRepository.findById(uuid);
        if(opAccount.isEmpty()){
            return new ResponseEntity(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다"),HttpStatus.BAD_REQUEST);
        }
        Account account = opAccount.get();
        Account updateAccount = account.updateAccountRole(dto.getAccountRole());

        authClient.roleChange(dto);
        accountRepository.save(updateAccount);
        return new ResponseEntity("권한이 변경되었습니다.",HttpStatus.OK);
    }

}
