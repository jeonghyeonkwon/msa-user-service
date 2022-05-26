package com.jeonghyeon.userservice.repository;

import com.jeonghyeon.userservice.domain.Point;
import com.jeonghyeon.userservice.repository.querydsl.PointRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point,Long>, PointRepositoryCustom {
    Optional<Point> findByPointRandomId(String pointRandomId);
}
