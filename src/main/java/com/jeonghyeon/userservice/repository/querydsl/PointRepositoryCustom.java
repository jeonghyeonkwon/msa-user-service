package com.jeonghyeon.userservice.repository.querydsl;

import com.jeonghyeon.userservice.domain.PointStatus;
import com.jeonghyeon.userservice.dto.point.PointResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointRepositoryCustom {
    Page<PointResponseDto> pointPageDto(PointStatus pointStatus, Pageable pageable);
}
