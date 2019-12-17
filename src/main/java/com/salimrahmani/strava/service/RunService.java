package com.salimrahmani.strava.service;

import com.salimrahmani.strava.dto.ReportDTO;
import com.salimrahmani.strava.exception.BusinessException;
import com.salimrahmani.strava.exception.NotFoundException;
import com.salimrahmani.strava.model.Run;
import com.salimrahmani.strava.repository.RunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RunService {

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Run findById(Long id) {
        return runRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("error.not_found_exception.by_id.run", new Object[]{id}));
    }

    public Page<Run> findAll(Integer pageNumber, Integer size) {
        Pageable pageRequest;
        if (pageNumber == null && size == null) {
            pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        } else {
            pageRequest = PageRequest.of(pageNumber, size);
        }

        return runRepository.findAll(pageRequest);
    }

    public Run save(Run run) {

        if (run.getEnd().isBefore(run.getStart())) {
            throw new BusinessException("error.business_exception.endDate_before_startDate", new Object[]{});
        }

        return runRepository.save(run);
    }

    public ReportDTO generateStats(LocalDateTime start, LocalDateTime end) {

        if (end.isBefore(start)) {
            throw new BusinessException("error.business_exception.endDate_before_startDate", new Object[]{});
        }

        List<Run> runs = runRepository.findByStartGreaterThanEqualAndEndLessThanEqual(start, end);

        return new ReportDTO(getAverageKms(runs), getAverageCals(runs));
    }

    private double getAverageKms(List<Run> runs) {
        return runs.stream()
                .mapToDouble(run -> run.getKms().doubleValue())
                .average()
                .orElse(0);
    }

    private double getAverageCals(List<Run> runs) {
        return runs.stream()
                .mapToDouble(run -> run.getCals().doubleValue())
                .average()
                .orElse(0);
    }
}
