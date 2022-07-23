package com.jeonghyeon.userservice.repository.querydsl;

import com.jeonghyeon.userservice.domain.AccountRole;
import com.jeonghyeon.userservice.dto.account.AccountDetailDto;
import com.jeonghyeon.userservice.dto.account.BasicAccountResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.jeonghyeon.userservice.domain.QAccount.account;

public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public AccountRepositoryCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BasicAccountResponseDto> accountPage(String keyword, Pageable pageable) {
        List<BasicAccountResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(BasicAccountResponseDto.class,
                        account.uuid,
                        account.accountId,
                        account.accountName,
                        account.accountTel,
                        account.accountRole,
                        account.money))
                .from(account)
                .where(namedEq(keyword),notAdmin())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(account.uuid.desc()).fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(account.count()).from(account).where(namedEq(keyword), notAdmin());
        return PageableExecutionUtils.getPage(content,pageable,()->countQuery.fetchOne());
    }

    @Override
    public Optional<AccountDetailDto> accountDetail(String uuid) {
        AccountDetailDto accountDetailDto = jpaQueryFactory.select(Projections.constructor(AccountDetailDto.class,
                        account.uuid,
                        account.accountId,
                        account.accountName,
                        account.accountTel,
                        account.money,
                        account.accountRole,
                        account.address.zipCode,
                        account.address.detail
                ))
                .from(account)
                .where(account.uuid.eq(uuid)).fetchOne();
        return Optional.ofNullable(accountDetailDto);
    }

    private BooleanExpression namedEq(String keyword){
        return keyword.equals("")?null:account.accountName.like(keyword);
    }
    private BooleanExpression notAdmin(){
        return account.accountRole.ne(AccountRole.ADMIN);
    }

}
