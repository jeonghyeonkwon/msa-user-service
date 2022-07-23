package com.jeonghyeon.userservice.service;

import com.jeonghyeon.userservice.domain.Account;
import com.jeonghyeon.userservice.domain.Point;
import com.jeonghyeon.userservice.domain.PointStatus;
import com.jeonghyeon.userservice.dto.ErrorResponseDto;
import com.jeonghyeon.userservice.dto.PageListDto;
import com.jeonghyeon.userservice.dto.point.PointRequestDto;
import com.jeonghyeon.userservice.dto.point.PointResponseDto;
import com.jeonghyeon.userservice.repository.AccountRepository;
import com.jeonghyeon.userservice.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final AccountRepository accountRepository;

    public ResponseEntity pointPage(PointStatus pointStatus, Pageable pageable) {
        Page<PointResponseDto> pointPage = pointRepository.pointPageDto(pointStatus, pageable);
        PageListDto dto = new PageListDto(pointPage.getNumber(),pointPage.isFirst(),pointPage.isLast(),pointPage.getTotalPages(),pointPage.getTotalElements(),pointPage.getContent());
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity requestPoint(String uuid, PointRequestDto dto) {
        Optional<Account> opAccount = accountRepository.findById(uuid);
        if(opAccount.isEmpty()){
            return new ResponseEntity(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다. 다시 시도해 주세요"),HttpStatus.BAD_REQUEST);
        }
        Account account = opAccount.get();
        Point point = new Point(UUID.randomUUID().toString(),account,dto.getPoint());
        Point savePoint = pointRepository.save(point);

        return new ResponseEntity(savePoint.getUuid(),HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity decidePoint(String pointRandomId, PointStatus pointStatus) {
        Optional<Point> opPoint = pointRepository.findById(pointRandomId);
        if(opPoint.isEmpty()){
            return new ResponseEntity(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다. 다시 시도해 주세요"),HttpStatus.BAD_REQUEST);
        }
        Point point = opPoint.get();
        Point updatePoint = point.updateStatus(pointStatus);
        if(pointStatus == PointStatus.SUBMIT){
            Account account = point.getAccount();
            Account updateAccount = account.addPoint(point.getPrice());
            accountRepository.save(updateAccount);
        }

        Point savePoint = pointRepository.save(updatePoint);
        return new ResponseEntity(savePoint.getUuid(),HttpStatus.OK);
    }
}
