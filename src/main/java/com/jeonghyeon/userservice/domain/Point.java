package com.jeonghyeon.userservice.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="points")
public class Point extends BaseTimeEntity{
    @Id

    @Column(name="point_pk")
    private String uuid;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_pk")
    private Account account;

    private Long price;

    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    protected Point(){}

    public Point(String uuid,Account account,Long price){
        this.uuid = uuid;
        this.account = account;
        account.getPointList().add(this);
        this.price = price;
        this.pointStatus = PointStatus.REQUEST;
    }

    public Point updateStatus(PointStatus pointStatus) {
        this.pointStatus = pointStatus;
        return this;
    }
}
