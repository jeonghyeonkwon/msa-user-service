package com.jeonghyeon.userservice.service;

import com.jeonghyeon.userservice.client.RestaurantClient;
import com.jeonghyeon.userservice.dto.FeignResponseDto;
import com.jeonghyeon.userservice.dto.restaurant.FeignRestaurantResponseDto;
import com.jeonghyeon.userservice.dto.restaurant.RestaurantRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantClient restaurantClient;

    @Transactional
    public ResponseEntity createRestaurant(String uuid, RestaurantRequestDto restaurantRequestDto) {

        FeignResponseDto<FeignRestaurantResponseDto> restaurant = restaurantClient.createRestaurant(uuid, restaurantRequestDto);



        return new ResponseEntity(restaurant.getMsg(),HttpStatus.CREATED);
    }
}
