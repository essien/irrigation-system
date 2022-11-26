package com.github.essien.banquemisr.irrigation.exception;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
public class LandNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String landId;

    public LandNotFoundException(String landId) {
        this.landId = landId;
    }

    public String getLandId() {
        return landId;
    }
}
