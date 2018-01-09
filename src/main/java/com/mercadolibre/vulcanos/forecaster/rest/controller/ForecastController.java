package com.mercadolibre.vulcanos.forecaster.rest.controller;

import com.mercadolibre.vulcanos.forecaster.model.ForecastInformationRepository;
import com.mercadolibre.vulcanos.forecaster.rest.model.ErrorResponse;
import com.mercadolibre.vulcanos.forecaster.service.GalaxyDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class ForecastController {
    private final ForecastInformationRepository forecastInformationRepository;
    private final GalaxyDataLoader galaxyDataLoader;

    @Autowired
    public ForecastController(ForecastInformationRepository forecastInformationRepository, GalaxyDataLoader galaxyDataLoader) {
        this.forecastInformationRepository = forecastInformationRepository;
        this.galaxyDataLoader = galaxyDataLoader;
    }

    @RequestMapping("/clima")
    @ResponseBody
    public Object forecast(@RequestParam("dia") Integer dia) {
        if (forecastInformationRepository.exists(dia)) {
            return forecastInformationRepository.findOne(dia);
        } else {
            return new ResponseEntity<>(new ErrorResponse("Pronóstico no encontrado para el día #" + dia), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/forecast/load")
    @ResponseBody
    public Object load(@RequestParam(value = "years", defaultValue = "10") Integer years) {
        if (years <= 0) {
            return new ResponseEntity<>(new ErrorResponse("Invalid years: " + years), HttpStatus.BAD_REQUEST);
        } else {
            return this.galaxyDataLoader.loadData(years);
        }
    }
}
