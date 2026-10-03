package org.kgromov;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Country(
        @JsonProperty("Code") String code,
        @JsonProperty("Name") String name,
        @JsonProperty("SurfaceArea") BigDecimal area,
        @JsonProperty("Population") Integer population,
        @JsonProperty("Capital") String capital,
        @JsonProperty("GovernmentForm") String governmentForm,
        @JsonProperty("Continent") String continent,
        @JsonProperty("Region") String region,
        @JsonProperty("IndepYear") Short independenceYear,
        @JsonProperty("LifeExpectancy") BigDecimal lifespan,
        @JsonProperty("GNP") BigDecimal gnp,
        @JsonProperty("GNPOld") BigDecimal gnpOld,
        @JsonProperty("LocalName") String localName,
        @JsonProperty("HeadOfState") String head,
        @JsonProperty("Code2") String code2
) {
}
