package com.salimrahmani.strava.repository;

import com.salimrahmani.strava.model.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RunRepository extends JpaRepository<Run, Long> {

    List<Run> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDateTime startDate, LocalDateTime endDate);

}
