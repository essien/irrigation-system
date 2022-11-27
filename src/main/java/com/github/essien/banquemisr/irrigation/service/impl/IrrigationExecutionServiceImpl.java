package com.github.essien.banquemisr.irrigation.service.impl;

import com.github.essien.banquemisr.irrigation.entity.IrrigationExecution;
import com.github.essien.banquemisr.irrigation.entity.Land;
import com.github.essien.banquemisr.irrigation.exception.LandNotFoundException;
import com.github.essien.banquemisr.irrigation.repo.LandRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.github.essien.banquemisr.irrigation.service.IrrigationExecutionService;
import com.github.essien.banquemisr.irrigation.repo.IrrigationExecutionRepository;

/**
 * @author bodmas
 * @since Nov 27, 2022.
 */
@Service
public class IrrigationExecutionServiceImpl implements IrrigationExecutionService {

    private final IrrigationExecutionRepository irrigationRepository;
    private final LandRepository landRepository;

    public IrrigationExecutionServiceImpl(IrrigationExecutionRepository irrigationRepository, LandRepository landRepository) {
        this.irrigationRepository = irrigationRepository;
        this.landRepository = landRepository;
    }

    @Override
    public void save(String landId, LocalDateTime executionTime) {
        Land land = landRepository.findByKey(landId).orElseThrow(() -> new LandNotFoundException(landId));
        irrigationRepository.save(new IrrigationExecution(land, executionTime));
    }
}
