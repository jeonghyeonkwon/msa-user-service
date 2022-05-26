package com.jeonghyeon.userservice.messagequeue;

import com.google.gson.Gson;
import com.jeonghyeon.userservice.domain.Account;
import com.jeonghyeon.userservice.domain.Address;
import com.jeonghyeon.userservice.dto.ErrorMessageDto;
import com.jeonghyeon.userservice.dto.account.KafkaUserInfoDto;
import com.jeonghyeon.userservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final AccountRepository accountRepository;


    @KafkaListener(topics ="auth-create-user-event")
    public void createAccountInfo(String kafkaMessage){
        log.info("createAccountInfo -> {}",kafkaMessage);
        Gson gson = new Gson();

        KafkaUserInfoDto dto = gson.fromJson(kafkaMessage, KafkaUserInfoDto.class);

        Optional<Account> opAccount = accountRepository.findByAccountRandomId(dto.getAccountRandomId());
        if(opAccount.isEmpty()){
            Address address = new Address(dto.getZipCode(), dto.getDetail());
            Account account = new Account(dto.getAccountRandomId(),dto.getAccountId(),dto.getAccountName(),dto.getAccountTel(),address);
            accountRepository.save(account);
        }
    }
}

