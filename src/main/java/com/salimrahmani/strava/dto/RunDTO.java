package com.salimrahmani.strava.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RunDTO {

    private Long id;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;

    @NotNull
    @Positive
    private BigDecimal kms;

    @NotNull
    @Positive
    private BigDecimal cals;

}
