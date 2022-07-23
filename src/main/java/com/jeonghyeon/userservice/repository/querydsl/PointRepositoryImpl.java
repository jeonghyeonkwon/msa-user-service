package com.jeonghyeon.userservice.repository.querydsl;

import com.jeonghyeon.userservice.domain.PointStatus;
import com.jeonghyeon.userservice.dto.point.PointResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.jeonghyeon.userservice.domain.QPoint.*;
import static com.jeonghyeon.userservice.domain.QAccount.*;

public class PointRepositoryImpl implements PointRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public PointRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<PointResponseDto> pointPageDto(PointStatus pointStatus, Pageable pageable){
        List<PointResponseDto> content = jpaQueryFactory.select(Projections.constructor(PointResponseDto.class,
                        point.uuid,
                        point.price,
                        point.createdDate,
                        account.accountId,
                        account.accountName
                ))
                .from(point)
                .join(point.account, account)
                .where(pointEq(pointStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(point.uuid.desc()).fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(point.count()).from(point).where(pointEq(pointStatus));
        return PageableExecutionUtils.getPage(content,pageable,()->countQuery.fetchOne());
    }

    private BooleanExpression pointEq(PointStatus pointStatus){
        return pointStatus==PointStatus.ALL?null:point.pointStatus.eq(pointStatus);
    }

}
