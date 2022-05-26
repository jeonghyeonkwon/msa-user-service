package com.jeonghyeon.userservice.controller;

import com.jeonghyeon.userservice.client.AuthClient;
import com.jeonghyeon.userservice.domain.PointStatus;
import com.jeonghyeon.userservice.dto.point.PointRequestDto;
import com.jeonghyeon.userservice.dto.RoleChangeRequestDto;
import com.jeonghyeon.userservice.dto.restaurant.RestaurantRequestDto;
import com.jeonghyeon.userservice.service.AccountService;
import com.jeonghyeon.userservice.service.PointService;
import com.jeonghyeon.userservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RestaurantService restaurantService;
    private final PointService pointService;
    private final AuthClient authClient;

    @GetMapping("/feign-check")
    public ResponseEntity feignCheck(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        String result = authClient.headerCheck(authorization);
        System.out.println(result);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/health-check")
    public ResponseEntity healthCheck(){

        return new  ResponseEntity("OK",HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity userList(@PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC)Pageable pageable,@RequestParam(required = false,defaultValue = "") String keyword){
        return accountService.userList(keyword,pageable);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity userDetail(@PathVariable String uuid){
        return accountService.userDetail(uuid);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity roleChangeAccount(@PathVariable String uuid, @RequestBody RoleChangeRequestDto dto){
        return accountService.roleChange(uuid,dto);
    }

    @GetMapping("/{uuid}/point")
    public ResponseEntity pointList(@PathVariable String uuid,@PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, PointStatus pointStatus){
        return pointService.pointPage(pointStatus,pageable);
    }

    @PostMapping("/{uuid}/point")
    public ResponseEntity requestPoint(@PathVariable String uuid, @RequestBody PointRequestDto dto){
        return pointService.requestPoint(uuid,dto);
    }

    @PatchMapping("/point/{pointRandomId}")
    public ResponseEntity decidePoint(@PathVariable String pointRandomId,@RequestParam("type") PointStatus pointStatus){
        return pointService.decidePoint(pointRandomId,pointStatus);
    }


    @PostMapping("/{uuid}/restaurant")
    public ResponseEntity createRestaurant(@PathVariable String uuid, @RequestBody RestaurantRequestDto restaurantRequestDto){
        return restaurantService.createRestaurant(uuid,restaurantRequestDto);
    }



}
