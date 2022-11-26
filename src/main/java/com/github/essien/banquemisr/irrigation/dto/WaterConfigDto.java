package com.github.essien.banquemisr.irrigation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaterConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "'start' time is required")
    private final LocalTime start;

    @NotNull(message = "'end' time is required")
    private final LocalTime end;

    @NotNull(message = "'amountOfWater' is required")
    private final Long amountOfWater;

    public WaterConfigDto(LocalTime start, LocalTime end, Long amountOfWater) {
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

        public WaterConfigDto build() {
            return new WaterConfigDto(start, end, amountOfWater);
        }
    }
}
