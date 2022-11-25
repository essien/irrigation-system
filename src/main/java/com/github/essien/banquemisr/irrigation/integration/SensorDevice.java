package com.github.essien.banquemisr.irrigation.integration;

import com.github.essien.banquemisr.irrigation.exception.DeviceUnavailableException;
import com.github.essien.banquemisr.irrigation.model.LandModel;

/**
 * @author bodmas
 * @since Nov 25, 2022.
 */
public interface SensorDevice {

    /**
     * An instruction to perform an irrigation using the specified configuration.
     *
     * @param landConfiguration the configuration to apply
     * @throws DeviceUnavailableException if the device is not available
     */
    void irrigate(LandModel landConfiguration) throws DeviceUnavailableException;
}
