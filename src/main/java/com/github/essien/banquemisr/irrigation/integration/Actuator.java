package com.github.essien.banquemisr.irrigation.integration;

import com.github.essien.banquemisr.irrigation.exception.DeviceUnavailableException;
import com.github.essien.banquemisr.irrigation.model.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author bodmas
 * @since Nov 27, 2022.
 */
@Component
public class Actuator implements SensorDevice {

    private static final Logger log = LoggerFactory.getLogger(Actuator.class);

    @Override
    public void irrigate(Instruction instruction) throws DeviceUnavailableException {
        log.info("Performing irrigation on land " + instruction.getLandId()
                 + " and will run for " + instruction.getDuration() + " minutes");
    }
}
