package com.lms.api.common.repository;

import com.lms.api.common.entity.CalendarEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<CalendarEntity, LocalDate> {
    Optional<CalendarEntity> findById(LocalDate date);
}
