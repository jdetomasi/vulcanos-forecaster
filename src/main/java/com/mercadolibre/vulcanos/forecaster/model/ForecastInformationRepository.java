package com.mercadolibre.vulcanos.forecaster.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastInformationRepository extends JpaRepository<ForecastInformation, Integer> {
}
