package com.jeonghyeon.userservice.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="points")
public class Point extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="point_pk")
    private Long id;

    private String pointRandomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_pk")
    private Account account;

    private Long price;

    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    protected Point(){}

    public Point(String pointRandomId,Account account,Long price){
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
