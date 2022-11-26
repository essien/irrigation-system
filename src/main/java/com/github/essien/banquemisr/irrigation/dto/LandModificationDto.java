package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandModificationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Null(message = "'landId' should only be set in URI")
    private String landId;

    // TODO: Use validation instead to report error when no fields are specified in the update.
    @NotNull(message = "'area' must be supplied")
    private final Double area;

    private LandModificationDto(String landId, Double area) {
        this.landId = landId;
        this.area = area;
    }

    public String getLandId() {
        return landId;
    }

    public void setLandId(String landId) {
        this.landId = landId;
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

        @JsonProperty
        public Builder withLandId(String landId) {
            this.landId = landId;
            return this;
        }

        @JsonProperty
        public Builder withArea(Double area) {
            this.area = area;
            return this;
        }

        public LandModificationDto build() {
            return new LandModificationDto(landId, area);
        }
    }
}
