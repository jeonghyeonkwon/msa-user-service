package com.jeonghyeon.userservice.domain;

public enum OrderStatus {
    REQUEST("요청"),
    RECEIVE("접수"),
    PREPARATION("준비 중"),
    RECRUIT("라이더 모집"),
    SELECTED("라이더 선택 완료"),
    DELIVERY("배달 중"),
    COMPLETE("배달 완료"),
    DECIDE("확정"),
    CANCEL("취소");

    private String kor;

    OrderStatus(String kor){
        this.kor = kor;
    }

}
