package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandModificationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String landId;

    // TODO: Use validation instead to report error when no fields are specified in the update.
    @NotBlank(message = "'area' must be supplied")
    private Double area;

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
}
