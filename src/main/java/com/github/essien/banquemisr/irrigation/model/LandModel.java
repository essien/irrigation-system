package com.github.essien.banquemisr.irrigation.model;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
public class LandModel {

    /**
     * ID of the land that this configuration applies to.
     */
    private String landId;

    /**
     * Area of the land in square meters.
     */
    private Double area;

    /**
     * Water configuration for the land.
     */
    private List<WaterConfig> waterConfigs;

    public String getLandId() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public List<WaterConfig> getWaterConfigs() {
        return waterConfigs;
    }

    public void setWaterConfigs(List<WaterConfig> waterConfigs) {
        this.waterConfigs = waterConfigs;
    }

    public static class WaterConfig {

        private ZonedDateTime start;
        private ZonedDateTime end;
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
