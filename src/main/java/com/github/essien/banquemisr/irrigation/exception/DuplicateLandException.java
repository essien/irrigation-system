package com.github.essien.banquemisr.irrigation.exception;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
public class DuplicateLandException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String landId;

    public DuplicateLandException(String landId) {
        this.landId = landId;
    }

    public String getLandId() {
        return landId;
    }
}
