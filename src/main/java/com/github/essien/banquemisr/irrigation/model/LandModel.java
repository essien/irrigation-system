package com.github.essien.banquemisr.irrigation.model;

import java.time.LocalTime;
import java.util.List;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
public class LandModel {

    /**
     * ID of the land that this configuration applies to.
     */
    private final String landId;

    /**
     * Area of the land in square meters.
     */
    private final Double area;

    /**
     * Water configuration for the land.
     */
    private final List<WaterConfig> waterConfigs;

    public LandModel(String landId, Double area) {
        this(landId, area, null);
    }

    public LandModel(String landId, List<WaterConfig> waterConfigs) {
        this(landId, null, waterConfigs);
    }

    private LandModel(String landId, Double area, List<WaterConfig> waterConfigs) {
        this.landId = landId;
        this.area = area;
        this.waterConfigs = waterConfigs;
    }

    public String getLandId() {
        return landId;
    }

    public Double getArea() {
        return area;
    }

    public List<WaterConfig> getWaterConfigs() {
        return waterConfigs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String landId;
        private Double area;
        private List<LandModel.WaterConfig> waterConfigs;

        private Builder() {
        }

        public Builder withLandId(String landId) {
            this.landId = landId;
            return this;
        }

        public Builder withArea(Double area) {
            this.area = area;
            return this;
        }

        public Builder withWaterConfigs(List<LandModel.WaterConfig> waterConfigs) {
            this.waterConfigs = waterConfigs;
            return this;
        }

        public Builder withLandModel(LandModel landModel) {
            withLandId(landModel.getLandId());
            withArea(landModel.getArea());
            withWaterConfigs(landModel.getWaterConfigs());
            return this;
        }

        public LandModel build() {
            return new LandModel(landId, area, waterConfigs);
        }
    }

    public static class WaterConfig {

        private final LocalTime start;
        private final LocalTime end;
        private final Long amountOfWater;

        public WaterConfig(LocalTime start, LocalTime end, Long amountOfWater) {
            this.start = start;
            this.end = end;
            this.amountOfWater = amountOfWater;
        }

        public LocalTime getStart() {
            return start;
        }

        public LocalTime getEnd() {
            return end;
        }

        public Long getAmountOfWater() {
            return amountOfWater;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {

            private LocalTime start;
            private LocalTime end;
            private Long amountOfWater;

            private Builder() {
            }

            public Builder withStart(LocalTime start) {
                this.start = start;
                return this;
            }

            public Builder withEnd(LocalTime end) {
                this.end = end;
                return this;
            }

            public Builder withAmountOfWater(Long amountOfWater) {
                this.amountOfWater = amountOfWater;
                return this;
            }

            public LandModel.WaterConfig build() {
                return new LandModel.WaterConfig(start, end, amountOfWater);
            }
        }
    }
}
