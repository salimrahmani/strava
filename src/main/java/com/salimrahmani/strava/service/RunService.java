package com.salimrahmani.strava.service;

import com.salimrahmani.strava.dto.ReportDTO;
import com.salimrahmani.strava.exception.BusinessException;
import com.salimrahmani.strava.model.Run;
import com.salimrahmani.strava.repository.RunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RunService {

    @Autowired
    private RunRepository runRepository;

    public Run save(Run run) {

        if (run.getEnd().isBefore(run.getStart())) {
            throw new BusinessException("error.business_exception.endDate_before_startDate", new Object[]{});
        }

        return runRepository.save(run);
    }

    public ReportDTO generateStats(LocalDateTime start, LocalDateTime end) {

        // Get all runs in range.
        List<Run> runs = runRepository.findByStartGreaterThanEqualAndEndLessThanEqual(start, end);

        return new ReportDTO(getAverageKms(runs), getAverageCals(runs));
    }

    private double getAverageKms(List<Run> runs) {
        return runs.stream()
                .mapToDouble(run -> run.getKms().doubleValue())
                .average()
                .orElse(Double.NaN);
    }


    private double getAverageCals(List<Run> runs) {
        return runs.stream()
                .mapToDouble(run -> run.getCalories().doubleValue())
                .average()
                .orElse(Double.NaN);
    }
}
