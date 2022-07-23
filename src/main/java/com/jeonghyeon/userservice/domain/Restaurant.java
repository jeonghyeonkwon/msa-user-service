package com.jeonghyeon.userservice.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="restaurant")
public class Restaurant extends BaseTimeEntity{
    @Id
    @Column(name="restaurant_pk")
    private String uuid;

    private String restaurantName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_pk")
    public Account account;

    protected Restaurant(){}

    public Restaurant(String uuid,String restaurantName,Account account){
        this.uuid = uuid;
        this.restaurantName = restaurantName;
        this.account = account;
        account.setRestaurant(this);
    }
}
