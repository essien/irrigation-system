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
    private String landId;
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
