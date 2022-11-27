package com.github.essien.banquemisr.irrigation.model;

/**
 * @author bodmas
 * @since Nov 27, 2022.
 */
public class Instruction {

    private final String landId;
    private final Integer duration;

    private Instruction(String landId, Integer duration) {
        this.landId = landId;
        this.duration = duration;
    }

    public String getLandId() {
        return landId;
    }

    public Integer getDuration() {
        return duration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String landId;
        private Integer duration;

        private Builder() {
        }

        public Builder withLandId(String landId) {
            this.landId = landId;
            return this;
        }

        public Builder withDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Instruction build() {
            return new Instruction(landId, duration);
        }
    }
}
