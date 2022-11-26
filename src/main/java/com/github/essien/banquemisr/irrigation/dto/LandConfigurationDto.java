package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
    private final List<WaterConfigDto> waterConfigs;

    private LandConfigurationDto(String landId, List<WaterConfigDto> waterConfigs) {
        this.landId = landId;
        this.waterConfigs = waterConfigs;
    }

    public String getLandId() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
    }

    public List<WaterConfigDto> getWaterConfigs() {
        return waterConfigs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String landId;
        private List<WaterConfigDto> waterConfigs;

        private Builder() {
        }

        public Builder withLandId(String landId) {
            this.landId = landId;
            return this;
        }

        public Builder withWaterConfigs(List<WaterConfigDto> waterConfigs) {
            this.waterConfigs = waterConfigs;
            return this;
        }

        public LandConfigurationDto build() {
            return new LandConfigurationDto(landId, waterConfigs);
        }
    }
}
