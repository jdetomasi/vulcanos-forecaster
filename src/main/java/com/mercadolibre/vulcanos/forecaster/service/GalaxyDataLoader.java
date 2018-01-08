package com.mercadolibre.vulcanos.forecaster.service;

import com.mercadolibre.vulcanos.forecaster.model.ForecastInformation;
import com.mercadolibre.vulcanos.forecaster.model.ForecastInformationRepository;
import com.mercadolibre.vulcanos.galaxy.model.Forecast;
import com.mercadolibre.vulcanos.galaxy.simulation.GalaxySimulator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GalaxyDataLoader {
    private static final Logger logger = Logger.getLogger(GalaxyDataLoader.class);

    private static final Integer DAYS_IN_YEAR = 365;

    private final GalaxySimulator galaxySimulator;
    private final ForecastInformationRepository forecastInformationRepository;

    @Autowired
    public GalaxyDataLoader(GalaxySimulator galaxySimulator, ForecastInformationRepository forecastInformationRepository) {
        this.galaxySimulator = galaxySimulator;
        this.forecastInformationRepository = forecastInformationRepository;
    }

    public Map<String, String> loadData(Integer years){
        Map<Forecast, Integer> forecastPeriodCount = new HashMap<>();
        Arrays.stream(Forecast.values()).forEach(f -> forecastPeriodCount.put(f, 0));
        RainData rainData = new RainData();
        Forecast previousForecast = null;

        for (int day = 0; day < DAYS_IN_YEAR * years; day++) {
            Forecast newForecast = galaxySimulator.getCurrentDayForecast();
            saveOneDayForecast(day, newForecast);

            updateForecastPeriodCount(forecastPeriodCount, previousForecast, newForecast);

            if (Forecast.RAIN == newForecast) {
                rainData = calculateUpdatedRainIntensityInfo(day, newForecast, rainData);
            }

            previousForecast = newForecast;
            galaxySimulator.advanceOneDay();

            logger.info(String.format("Day #%d - %s (%f)", day, newForecast.toString(), newForecast.getValue().doubleValue()));
        }

        return buildSuccessfulResponse(forecastPeriodCount, rainData.getDaysWithMaxRainIntensity());
    }

    private void saveOneDayForecast(Integer day, Forecast forecast) {
        ForecastInformation forecastInformation = new ForecastInformation(day, forecast.name());
        forecastInformationRepository.save(forecastInformation);
    }

    private void updateForecastPeriodCount(Map<Forecast, Integer> forecastPeriodCount, Forecast previousForecast, Forecast newForecast) {
        if (previousForecast != newForecast) {
            forecastPeriodCount.put(newForecast, forecastPeriodCount.get(newForecast) + 1);
        }
    }

    private Map<String, String> buildSuccessfulResponse(Map<Forecast, Integer> forecastPeriodCount, List<Integer> maxRainIntensityDay) {
        Map<String, String> results = new HashMap<>();
        results.put("Rainy periods", forecastPeriodCount.get(Forecast.RAIN).toString());
        results.put("Optimal periods", forecastPeriodCount.get(Forecast.OPTIMAL).toString());
        results.put("Normal periods", forecastPeriodCount.get(Forecast.NORMAL).toString());
        results.put("Drought periods", forecastPeriodCount.get(Forecast.RAIN).toString());
        results.put("Most rainy day(s)", maxRainIntensityDay.toString());

        return results;
    }

    private RainData calculateUpdatedRainIntensityInfo(Integer day, Forecast forecast, RainData rainData) {
        boolean isRainIntensityBiggerThanPrevious = forecast.getValue().compareTo(rainData.getMaxRainIntensity()) > 0;

        if (isRainIntensityBiggerThanPrevious) {
            rainData = new RainData();
            rainData.setDaysWithMaxRainIntensity(new ArrayList<>(day));
            rainData.setMaxRainIntensity(forecast.getValue());

            return  rainData;
        } else {
            boolean isRainIntensityEqualsThanPrevious = forecast.getValue().compareTo(rainData.getMaxRainIntensity()) == 0;
            if (isRainIntensityEqualsThanPrevious) {
                rainData.getDaysWithMaxRainIntensity().add(day);
            }

            return rainData;
        }
    }

    private static class RainData {
        List<Integer> daysWithMaxRainIntensity = new ArrayList<>(); // several days can have the same max rain intensity
        BigDecimal maxRainIntensity = BigDecimal.ZERO;

        public List<Integer> getDaysWithMaxRainIntensity() {
            return daysWithMaxRainIntensity;
        }

        public void setDaysWithMaxRainIntensity(List<Integer> daysWithMaxRainIntensity) {
            this.daysWithMaxRainIntensity = daysWithMaxRainIntensity;
        }

        public BigDecimal getMaxRainIntensity() {
            return maxRainIntensity;
        }

        public void setMaxRainIntensity(BigDecimal maxRainIntensity) {
            this.maxRainIntensity = maxRainIntensity;
        }
    }
}