package com.salimrahmani.strava.converter;

import com.salimrahmani.strava.dto.RunDTO;
import com.salimrahmani.strava.model.Run;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RunConverterTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RunConverter runConverter;

    @Test
    public void should_convert_to_dto() {
        // Given

        Run run = Run.builder()
                .start(LocalDateTime.of(2019, 1, 1, 6, 0))
                .end(LocalDateTime.of(2019, 1, 1, 7, 0))
                .build();

        when(modelMapper.map(run, RunDTO.class)).thenReturn(new RunDTO());

        // When
        RunDTO runDTO = runConverter.convertToDTO(run);

        // Then
        assertThat(runDTO.getStart()).isEqualTo("2019-01-01 06:00");
        assertThat(runDTO.getEnd()).isEqualTo("2019-01-01 07:00");

    }

    @Test
    public void should_convert_to_model() {
        // Given
        RunDTO dto = new RunDTO();

        dto.setStart("2019-01-01 06:00");
        dto.setEnd("2019-01-01 07:00");


        Run run = Run.builder().build();

        when(modelMapper.map(dto, Run.class)).thenReturn(run);

        // When
        Run model = runConverter.convertToModel(dto);

        // THen
        assertThat(model.getStart().getYear()).isEqualTo(2019);
        assertThat(model.getStart().getMonthValue()).isEqualTo(1);
        assertThat(model.getStart().getDayOfMonth()).isEqualTo(1);
        assertThat(model.getStart().getHour()).isEqualTo(6);
        assertThat(model.getStart().getMinute()).isEqualTo(0);

        assertThat(model.getEnd().getYear()).isEqualTo(2019);
        assertThat(model.getEnd().getMonthValue()).isEqualTo(1);
        assertThat(model.getEnd().getDayOfMonth()).isEqualTo(1);
        assertThat(model.getEnd().getHour()).isEqualTo(7);
        assertThat(model.getEnd().getMinute()).isEqualTo(0);

    }

}