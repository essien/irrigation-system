package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandConfigurationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Null(message = "'landId' should only be set in URI")
    private String landId;

    /**
     * Water configuration for the land.
     */
    @NotEmpty(message = "'waterConfigs' must not be empty")
    @Valid
    private final List<WaterConfig> waterConfigs;

    private LandConfigurationDto(String landId, List<WaterConfig> waterConfigs) {
        this.landId = landId;
        this.waterConfigs = waterConfigs;
    }

    public String getLandId() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
    }

    public List<WaterConfig> getWaterConfigs() {
        return waterConfigs;
    }

    public static class WaterConfig {

        @NotNull(message = "'start' date is required")
        private final ZonedDateTime start;

        @NotNull(message = "'end' date is required")
        private final ZonedDateTime end;

        @NotNull(message = "'amountOfWater' is required")
        private final Long amountOfWater;

        private WaterConfig(ZonedDateTime start, ZonedDateTime end, Long amountOfWater) {
            this.start = start;
            this.end = end;
            this.amountOfWater = amountOfWater;
        }

        public ZonedDateTime getStart() {
            return start;
        }

        public ZonedDateTime getEnd() {
            return end;
        }

        public Long getAmountOfWater() {
            return amountOfWater;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {

            private ZonedDateTime start;
            private ZonedDateTime end;
            private Long amountOfWater;

            private Builder() {
            }

            public Builder withStart(ZonedDateTime start) {
                this.start = start;
                return this;
            }

            public Builder withEnd(ZonedDateTime end) {
                this.end = end;
                return this;
            }

            public Builder withAmountOfWater(Long amountOfWater) {
                this.amountOfWater = amountOfWater;
                return this;
            }

            public LandConfigurationDto.WaterConfig build() {
                return new LandConfigurationDto.WaterConfig(start, end, amountOfWater);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String landId;
        private List<LandConfigurationDto.WaterConfig> waterConfigs;

        private Builder() {
        }

        public Builder withLandId(String landId) {
            this.landId = landId;
            return this;
        }

        public Builder withWaterConfigs(List<LandConfigurationDto.WaterConfig> waterConfigs) {
            this.waterConfigs = waterConfigs;
            return this;
        }

        public LandConfigurationDto build() {
            return new LandConfigurationDto(landId, waterConfigs);
        }
    }
}
