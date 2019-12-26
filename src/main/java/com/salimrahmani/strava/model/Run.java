package com.salimrahmani.strava.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "run_id")
    private Long id;

    @NotNull
    @Past
    @Column
    private LocalDateTime startDate;

    @NotNull
    @PastOrPresent
    @Column
    private LocalDateTime endDate;

    @NotNull
    @Positive
    @Column
    private BigDecimal kms;

    @NotNull
    @Positive
    @Column
    private BigDecimal cals;

}
