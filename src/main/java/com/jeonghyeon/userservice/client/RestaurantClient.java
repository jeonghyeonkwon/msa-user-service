package com.jeonghyeon.userservice.client;

import com.jeonghyeon.userservice.dto.FeignResponseDto;
import com.jeonghyeon.userservice.dto.restaurant.FeignRestaurantResponseDto;
import com.jeonghyeon.userservice.dto.restaurant.RestaurantRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "restaurant-service", url="http://localhost:3065")
public interface RestaurantClient {

    @PostMapping("/{uuid}")
    FeignResponseDto<FeignRestaurantResponseDto> createRestaurant(@PathVariable("uuid") String uuid, RestaurantRequestDto dto);

}
