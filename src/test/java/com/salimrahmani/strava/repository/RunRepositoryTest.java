package com.salimrahmani.strava.repository;

import com.salimrahmani.strava.model.Run;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class RunRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RunRepository runRepository;

    @Test
    public void should_create_a_new_run() {
        // Given
        Run run = new Run();
        run.setStart(LocalDateTime.of(2019, 1, 1, 6, 0));
        run.setEnd(LocalDateTime.of(2019, 1, 1, 7, 30));
        run.setKms(BigDecimal.valueOf(5));
        run.setCals(BigDecimal.valueOf(400));

        // When
        Run savedRun = runRepository.save(run);


        // Then
        assertThat(savedRun.getId()).isNotNull();
        assertThat(runRepository.findAll()).isNotEmpty();
    }

    @Test
    public void should_find_runs_between_two_dates() {
        // Given

        Run run1 = new Run();
        run1.setStart(LocalDateTime.of(2019, 1, 1, 6, 0));
        run1.setEnd(LocalDateTime.of(2019, 1, 1, 7, 0));
        run1.setKms(BigDecimal.valueOf(5));
        run1.setCals(BigDecimal.valueOf(300));

        Run run2 = new Run();
        run2.setStart(LocalDateTime.of(2019, 1, 1, 15, 0));
        run2.setEnd(LocalDateTime.of(2019, 1, 1, 16, 0));
        run2.setKms(BigDecimal.valueOf(5));
        run2.setCals(BigDecimal.valueOf(300));

        Run run3 = new Run();
        run3.setStart(LocalDateTime.of(2019, 1, 2, 6, 0));
        run3.setEnd(LocalDateTime.of(2019, 1, 2, 7, 0));
        run3.setKms(BigDecimal.valueOf(5));
        run3.setCals(BigDecimal.valueOf(300));

        entityManager.persist(run1);
        entityManager.persist(run2);
        entityManager.persist(run3);

        entityManager.flush();

        // When
        LocalDateTime start = LocalDateTime.of(2019, 1, 1, 5, 0);
        LocalDateTime end = LocalDateTime.of(2019, 1, 1, 23, 0);
        List<Run> runs = runRepository.findByStartGreaterThanEqualAndEndLessThanEqual(start, end);

        // Then
        assertThat(runs.size()).isEqualTo(2);
    }
}