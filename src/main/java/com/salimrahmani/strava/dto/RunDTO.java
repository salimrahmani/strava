package com.salimrahmani.strava.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class RunDTO {

    private Long id;

    @NotNull
    private String start;

    @NotNull
    private String end;

    private BigDecimal kms;
    private BigDecimal cals;

}
