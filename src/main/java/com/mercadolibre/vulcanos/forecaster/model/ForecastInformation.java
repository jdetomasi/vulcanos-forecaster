package com.mercadolibre.vulcanos.forecaster.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ForecastInformation {
    @Id
    @JsonProperty("dia")
    private Integer day;

    @JsonProperty("clima")
    private String forecast;

    public ForecastInformation() {
    }

    public ForecastInformation(Integer day, String forecast) {
        this.day = day;
        this.forecast = forecast;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }
}
