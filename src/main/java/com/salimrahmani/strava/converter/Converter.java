package com.salimrahmani.strava.converter;

public interface Converter<M, D> {

    D convertToDTO(M model);

    M convertToModel(D dto);
}
