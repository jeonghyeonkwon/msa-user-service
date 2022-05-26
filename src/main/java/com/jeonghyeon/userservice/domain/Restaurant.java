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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_pk")
    private Long id;

    private String restaurantRandomId;

    private String restaurantName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_pk")
    public Account account;

    @OneToMany(mappedBy = "restaurant")
    private List<Food> foodList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orderList = new ArrayList<>();


    protected Restaurant(){}

    public Restaurant(String restaurantRandomId,String restaurantName,Account account){
        this.restaurantRandomId = restaurantRandomId;
        this.restaurantName = restaurantName;
        this.account = account;
        account.setRestaurant(this);
    }
}
