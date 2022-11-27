package com.github.essien.banquemisr.irrigation.service;

import java.time.LocalDateTime;

/**
 * @author bodmas
 * @since Nov 27, 2022.
 */
public interface IrrigationExecutionService {

    void save(String landId, LocalDateTime executionTime);
}
