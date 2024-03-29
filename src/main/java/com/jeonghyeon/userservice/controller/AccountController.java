package com.jeonghyeon.userservice.controller;

import com.jeonghyeon.userservice.client.AuthClient;
import com.jeonghyeon.userservice.domain.AccountRole;
import com.jeonghyeon.userservice.domain.PointStatus;
import com.jeonghyeon.userservice.dto.FeignResponseDto;
import com.jeonghyeon.userservice.dto.point.PointRequestDto;
import com.jeonghyeon.userservice.dto.RoleChangeRequestDto;
import com.jeonghyeon.userservice.dto.restaurant.RestaurantRequestDto;
import com.jeonghyeon.userservice.service.AccountService;
import com.jeonghyeon.userservice.service.PointService;
import com.jeonghyeon.userservice.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;



/*
* 회원 정보 관련 서비스
* 관리자 회원 관리 관련 서비스
* 정보 변경시 auth-service에 동기화 시켜줘야됨
* */

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RestaurantService restaurantService;
    private final PointService pointService;
    private final AuthClient authClient;
    private static final String AUTHORIZATION = "Authorization";
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
    /*
    * 유저 정보 리스트
    * */
    @GetMapping("/users")
    public ResponseEntity userList(
            HttpServletRequest request,
            @PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false,defaultValue = "") String keyword
    ){
        String authorization = request.getHeader(AUTHORIZATION);
        FeignResponseDto<String> result = authClient.adminCheck(authorization);

        if(result.getMsg()!= AccountRole.ADMIN.toString()){
            throw new IllegalStateException("관리자만 접근할 수 있습니다");
        }
        return accountService.userList(keyword,pageable);
    }
    /*
    * 유저 정보 자세히
    * */
    @GetMapping("/{uuid}")
    public ResponseEntity userDetail(
            HttpServletRequest request,
            @PathVariable String uuid){
        String authorization = request.getHeader(AUTHORIZATION);
        FeignResponseDto<String> result = authClient.userInfoCheck(authorization);
        if(result.getMsg()!=uuid){
            throw new IllegalStateException("해당 토큰과 요청 주소가 일치하지 않습니다.");
        }
        return accountService.userDetail(uuid);
    }

    /*
    * 유저 유형 (일반 유저, 음식점주) 정보 변경
    * */
    @PatchMapping("/{uuid}")
    public ResponseEntity roleChangeAccount(
            HttpServletRequest request,
            @PathVariable String uuid,
            @RequestBody RoleChangeRequestDto dto){
        String authorization = request.getHeader(AUTHORIZATION);
        FeignResponseDto<String> result = authClient.userInfoCheck(authorization);
        if(result.getMsg()!=uuid){
            throw new IllegalStateException("해당 토큰과 요청 주소가 일치하지 않습니다.");
        }
        return accountService.roleChange(uuid,dto);
    }

    /*
    * 포인트 리스트
    * */
    @GetMapping("/{uuid}/point")
    public ResponseEntity pointList(
            HttpServletRequest request,
            @PathVariable String uuid,
            @PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
            PointStatus pointStatus
    ){
        String authorization = request.getHeader(AUTHORIZATION);
        FeignResponseDto<String> result = authClient.userInfoCheck(authorization);
        if(result.getMsg()!=uuid){
            throw new IllegalStateException("해당 토큰과 요청 주소가 일치하지 않습니다.");
        }
        return pointService.pointPage(pointStatus,pageable);
    }
    /*
    * 음식점 생성
    * */
    @PostMapping("/{uuid}/restaurant")
    public ResponseEntity createRestaurant(
            HttpServletRequest request,
            @PathVariable String uuid, @RequestBody RestaurantRequestDto restaurantRequestDto){
        String authorization = request.getHeader(AUTHORIZATION);
        FeignResponseDto<String> result = authClient.userInfoCheck(authorization);
        if(result.getMsg()!=uuid){
            throw new IllegalStateException("해당 토큰과 요청 주소가 일치하지 않습니다.");
        }
        return restaurantService.createRestaurant(uuid,restaurantRequestDto);
    }


    //------------------------------------
    // 포인트 요청
    @PostMapping("/{uuid}/point")
    public ResponseEntity requestPoint(
            HttpServletRequest request,
            @PathVariable String uuid,
            @RequestBody PointRequestDto dto){
        String authorization = request.getHeader(AUTHORIZATION);
        FeignResponseDto<String> result = authClient.userInfoCheck(authorization);
        if(result.getMsg()!=uuid){
            throw new IllegalStateException("해당 토큰과 요청 주소가 일치하지 않습니다.");
        }
        return pointService.requestPoint(uuid,dto);
    }
    //포인트 결정
    @PatchMapping("/point/{pointRandomId}")
    public ResponseEntity decidePoint(@PathVariable String pointRandomId,@RequestParam("type") PointStatus pointStatus){
        return pointService.decidePoint(pointRandomId,pointStatus);
    }

}
