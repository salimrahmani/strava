package com.salimrahmani.strava.service;

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
import org.modelmapper.ModelMapper;

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
    private ModelMapper modelMapper;

    @InjectMocks
    private RunService runService;

    @Test
    public void should_create_a_new_run() {

        // Given
        RunDTO runDTO = new RunDTO();

        Run run = new Run();
        run.setStartDate(LocalDateTime.of(2019, 1, 1, 6, 0));
        run.setEndDate(LocalDateTime.of(2019, 1, 1, 7, 30));
        run.setKms(BigDecimal.valueOf(5));
        run.setCals(BigDecimal.valueOf(400));

        // When
        Run savedRun = runService.save(run);

        // Then
        verify(runRepository).save(run);

    }

    @Test(expected = BusinessException.class)
    public void should_throw_business_exception_when_end_date_is_before_start_date() {
        // Given
        RunDTO runDTO = new RunDTO();

        Run run = new Run();
        run.setStartDate(LocalDateTime.of(2019, 1, 1, 7, 30));
        run.setEndDate(LocalDateTime.of(2019, 1, 1, 6, 0));
        run.setKms(BigDecimal.valueOf(5));
        run.setCals(BigDecimal.valueOf(400));

        // When
        Run savedRun = runService.save(run);

    }

    @Test
    public void should_generate_stats() {
        // Given
        Run run1 = new Run();
        run1.setStartDate(LocalDateTime.of(2019, 1, 1, 6, 0));
        run1.setEndDate(LocalDateTime.of(2019, 1, 1, 7, 0));
        run1.setKms(BigDecimal.valueOf(5));
        run1.setCals(BigDecimal.valueOf(300));

        Run run2 = new Run();
        run2.setStartDate(LocalDateTime.of(2019, 1, 1, 15, 0));
        run2.setEndDate(LocalDateTime.of(2019, 1, 1, 16, 0));
        run2.setKms(BigDecimal.valueOf(8));
        run2.setCals(BigDecimal.valueOf(500));

        when(runRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqual(any(LocalDateTime.class), any(LocalDateTime.class)))
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