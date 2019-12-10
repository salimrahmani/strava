package com.salimrahmani.strava.converter;

import com.salimrahmani.strava.dto.RunDTO;
import com.salimrahmani.strava.model.Run;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RunConverter implements Converter<Run, RunDTO> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RunDTO convertToDTO(Run model) {
        RunDTO dto = modelMapper.map(model, RunDTO.class);
        dto.setStart(formatter.format(model.getStart()));
        dto.setEnd(formatter.format(model.getEnd()));
        return dto;
    }

    @Override
    public Run convertToModel(RunDTO dto) {
        Run model = modelMapper.map(dto, Run.class);
        model.setStart(LocalDateTime.parse(dto.getStart(), formatter));
        model.setEnd(LocalDateTime.parse(dto.getEnd(), formatter));
        return model;
    }
}
