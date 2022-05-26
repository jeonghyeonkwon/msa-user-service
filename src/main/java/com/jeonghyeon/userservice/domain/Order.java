package com.jeonghyeon.userservice.domain;


import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
public class Order extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_pk")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_pk")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_pk")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();

    protected Order(){}

    public Order(Account account,Restaurant restaurant){
        this.account = account;
        account.getOrderList().add(this);
        this.restaurant = restaurant;
        restaurant.getOrderList().add(this);
        this.orderStatus= OrderStatus.REQUEST;
    }


}
