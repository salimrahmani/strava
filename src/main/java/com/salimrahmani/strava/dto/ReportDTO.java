package com.salimrahmani.strava.dto;

import lombok.Data;

@Data
public class ReportDTO {

    private double averageKms;
    private double averageCals;

    public ReportDTO(double averageKms, double averageCals) {
        this.averageKms = averageKms;
        this.averageCals = averageCals;
    }

}
