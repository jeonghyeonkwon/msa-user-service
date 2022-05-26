package com.jeonghyeon.userservice.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="foods")
public class Food extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_pk")
    private Long id;

    private String foodName;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_pk")
    private Restaurant restaurant;

    protected Food(){}

    public Food(String foodName,Long price,Restaurant restaurant){
        this.foodName = foodName;
        this.price = price;
        this.restaurant = restaurant;
        restaurant.getFoodList().add(this);
    }
}
