package com.github.essien.banquemisr.irrigation.model;

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

        /**
         * Identifier of the land, supplied by caller.
         */
        private String landId;

        /**
         * Area of the land in square meters.
         */
        private Double area;

        /**
         * Water configuration for the land.
         */
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

        /**
         * Cron indicating when irrigation should be triggered.
         */
        private final String cron;

        /**
         * How long irrigation should run for, in minutes.
         */
        private final Integer duration;

        /**
         * Total amount of water to be supplied during the irrigation process.
         */
        private final Long amountOfWater;

        public WaterConfig(String cron, Integer duration, Long amountOfWater) {
            this.cron = cron;
            this.duration = duration;
            this.amountOfWater = amountOfWater;
        }

        public String getCron() {
            return cron;
        }

        public Integer getDuration() {
            return duration;
        }

        public Long getAmountOfWater() {
            return amountOfWater;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {

            private String cron;
            private Integer duration;
            private Long amountOfWater;

            private Builder() {
            }

            public Builder withCron(String cron) {
                this.cron = cron;
                return this;
            }

            public Builder withDuration(Integer duration) {
                this.duration = duration;
                return this;
            }

            public Builder withAmountOfWater(Long amountOfWater) {
                this.amountOfWater = amountOfWater;
                return this;
            }

            public LandModel.WaterConfig build() {
                return new LandModel.WaterConfig(cron, duration, amountOfWater);
            }
        }
    }
}
