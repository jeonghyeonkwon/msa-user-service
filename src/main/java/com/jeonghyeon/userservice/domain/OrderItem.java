package com.jeonghyeon.userservice.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="order_item")
public class OrderItem extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_pk")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_pk")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_pk")
    private Order order;

    private Integer count;

    protected OrderItem(){}

    public  OrderItem(Order order,Food food,Integer count){
        this.order = order;
        order.getOrderItemList().add(this);

        this.food = food;
        this.count = count;
    }
}
