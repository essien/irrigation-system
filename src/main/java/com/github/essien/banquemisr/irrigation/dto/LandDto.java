package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandDto implements Serializable {

    private static final long serialVersionUID = 1L;

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
