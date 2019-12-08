package com.salimrahmani.strava.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
public class Run {

    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    @Past
    @Column
    private LocalDateTime start;

    @NotNull
    @PastOrPresent
    @Column
    private LocalDateTime end;

    @NotNull
    @Positive
    @Column
    private BigDecimal kms;

    @NotNull
    @Positive
    @Column
    private BigDecimal calories;

}
