package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandCreationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "'landId' must be supplied")
    private final String landId;
    private final Double area;

    private LandCreationDto(String landId, Double area) {
        this.landId = landId;
        this.area = area;
    }

    public String getLandId() {
        return landId;
    }

    public Double getArea() {
        return area;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String landId;
        private Double area;

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

        public LandCreationDto build() {
            return new LandCreationDto(landId, area);
        }
    }
}
