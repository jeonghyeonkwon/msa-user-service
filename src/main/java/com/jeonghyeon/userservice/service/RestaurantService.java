package com.jeonghyeon.userservice.service;

import com.jeonghyeon.userservice.client.RestaurantClient;
import com.jeonghyeon.userservice.domain.Account;
import com.jeonghyeon.userservice.dto.FeignResponseDto;
import com.jeonghyeon.userservice.dto.restaurant.FeignRestaurantResponseDto;
import com.jeonghyeon.userservice.dto.restaurant.RestaurantRequestDto;
import com.jeonghyeon.userservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {
    private final AccountRepository accountRepository;
    private final RestaurantClient restaurantClient;

    @Transactional
    public ResponseEntity createRestaurant(String uuid, RestaurantRequestDto restaurantRequestDto) {
        Optional<Account> opAccount = accountRepository.findById(uuid);
        if(opAccount.isEmpty()){
            return new ResponseEntity("일반유저는 음식점을 개설 할 수 없습니다.",HttpStatus.BAD_REQUEST);
        }
        FeignResponseDto<FeignRestaurantResponseDto> restaurant = restaurantClient.createRestaurant(uuid, restaurantRequestDto);

        return new ResponseEntity(restaurant.getMsg(),HttpStatus.CREATED);
    }
}
