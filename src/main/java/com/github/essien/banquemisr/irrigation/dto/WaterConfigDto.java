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

    @NotNull(message = "'cron' is required")
    private final String cron;

    @NotNull(message = "'duration' is required")
    private final Integer duration;

    @NotNull(message = "'amountOfWater' is required")
    private final Long amountOfWater;

    public WaterConfigDto(String cron, Integer duration, Long amountOfWater) {
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

        public WaterConfigDto build() {
            return new WaterConfigDto(cron, duration, amountOfWater);
        }
    }
}
