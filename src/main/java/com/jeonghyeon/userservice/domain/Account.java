package com.jeonghyeon.userservice.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="accounts")
public class Account extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_pk")
    private Long id;

    private String accountRandomId;

    private String accountId;

    private String accountName;

    private String accountTel;

    private Long money;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Embedded
    private Address address;

    protected Account(){}

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "account")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "account")
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Point> pointList = new ArrayList<>();

    public Account(String accountRandomId,String accountId,String accountName,String accountTel,Address address){
        this.accountRandomId = accountRandomId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountTel = accountTel;
        this.money = (long) 0;
        this.accountRole = AccountRole.NORMAL;
        this.address = address;
    }




    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Account updateAccountRole(String accountRole) {
        this.accountRole = AccountRole.valueOf(accountRole);
        return this;
    }

    public Account addPoint(Long money) {
        Long thisLong = this.money;
        this.money = Long.sum(thisLong,money);
        return this;
    }
}
