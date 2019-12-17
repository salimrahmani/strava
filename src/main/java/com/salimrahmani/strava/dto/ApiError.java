package com.salimrahmani.strava.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ApiError implements Serializable {

    private int status;
    private String message;
    private List<String> errors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status.value();
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status.value();
        this.message = message;
        this.errors = errors;
        this.dateTime = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status.value();
        this.message = message;
        this.errors = Collections.singletonList(error);
        this.dateTime = LocalDateTime.now();
    }
}