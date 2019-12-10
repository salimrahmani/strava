package com.salimrahmani.strava.service;

import com.salimrahmani.strava.converter.RunConverter;
import com.salimrahmani.strava.dto.ReportDTO;
import com.salimrahmani.strava.dto.RunDTO;
import com.salimrahmani.strava.exception.BusinessException;
import com.salimrahmani.strava.model.Run;
import com.salimrahmani.strava.repository.RunRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RunServiceTest {

    @Mock
    private RunRepository runRepository;

    @Mock
    private RunConverter runConverter;

    @InjectMocks
    private RunService runService;

    @Test
    public void should_create_a_new_run() {

        // Given
        RunDTO runDTO = new RunDTO();

        Run run = Run.builder()
                .start(LocalDateTime.of(2019, 1, 1, 6, 0))
                .end(LocalDateTime.of(2019, 1, 1, 7, 30))
                .kms(BigDecimal.valueOf(5))
                .calories(BigDecimal.valueOf(400))
                .build();

        when(runConverter.convertToModel(runDTO)).thenReturn(run);

        // When
        Run savedRun = runService.save(runDTO);

        // Then
        verify(runConverter).convertToModel(runDTO);
        verify(runRepository).save(run);

    }

    @Test(expected = BusinessException.class)
    public void should_throw_business_exception_when_end_date_is_before_start_date() {
        // Given
        RunDTO runDTO = new RunDTO();

        Run run = Run.builder()
                .start(LocalDateTime.of(2019, 1, 1, 7, 30))
                .end(LocalDateTime.of(2019, 1, 1, 6, 0))
                .kms(BigDecimal.valueOf(5))
                .calories(BigDecimal.valueOf(400))
                .build();

        when(runConverter.convertToModel(runDTO)).thenReturn(run);

        // When
        Run savedRun = runService.save(runDTO);

    }

    @Test
    public void should_generate_stats() {
        // Given
        Run run1 = Run.builder()
                .start(LocalDateTime.of(2019, 1, 1, 6, 0))
                .end(LocalDateTime.of(2019, 1, 1, 7, 0))
                .kms(BigDecimal.valueOf(5))
                .calories(BigDecimal.valueOf(300))
                .build();

        Run run2 = Run.builder()
                .start(LocalDateTime.of(2019, 1, 1, 15, 0))
                .end(LocalDateTime.of(2019, 1, 1, 16, 0))
                .kms(BigDecimal.valueOf(8))
                .calories(BigDecimal.valueOf(500))
                .build();

        when(runRepository.findByStartGreaterThanEqualAndEndLessThanEqual(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Lists.list(run1, run2));

        // When
        ReportDTO reportDTO = runService.generateStats(
                LocalDateTime.of(2019, 1, 1, 5, 0),
                LocalDateTime.of(2019, 1, 1, 23, 0));

        // Then
        assertThat(reportDTO.getAverageKms()).isEqualTo(6.5);
        assertThat(reportDTO.getAverageCals()).isEqualTo(400);
    }
}