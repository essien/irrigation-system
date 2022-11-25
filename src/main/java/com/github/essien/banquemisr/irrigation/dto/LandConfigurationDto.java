package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandConfigurationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String landId;

    /**
     * Water configuration for the land.
     */
    @NotEmpty(message = "'waterConfigs' must not be empty")
    @Valid
    private List<WaterConfig> waterConfigs;

    public String getLandId() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
    }

    public List<WaterConfig> getWaterConfigs() {
        return waterConfigs;
    }

    public void setWaterConfigs(List<WaterConfig> waterConfigs) {
        this.waterConfigs = waterConfigs;
    }

    public static class WaterConfig {

        @NotNull(message = "'start' date is required")
        private ZonedDateTime start;

        @NotNull(message = "'end' date is required")
        private ZonedDateTime end;

        @NotNull(message = "'amountOfWater' is required")
        private Long amountOfWater;

        public ZonedDateTime getStart() {
            return start;
        }

        public void setStart(ZonedDateTime start) {
            this.start = start;
        }

        public ZonedDateTime getEnd() {
            return end;
        }

        public void setEnd(ZonedDateTime end) {
            this.end = end;
        }

        public Long getAmountOfWater() {
            return amountOfWater;
        }

        public void setAmountOfWater(Long amountOfWater) {
            this.amountOfWater = amountOfWater;
        }
    }
}
