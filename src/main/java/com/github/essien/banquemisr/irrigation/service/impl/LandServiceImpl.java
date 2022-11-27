package com.github.essien.banquemisr.irrigation.service.impl;

import com.github.essien.banquemisr.irrigation.entity.Land;
import com.github.essien.banquemisr.irrigation.entity.WaterConfig;
import com.github.essien.banquemisr.irrigation.exception.DuplicateLandException;
import com.github.essien.banquemisr.irrigation.exception.IrrigationSchedulerException;
import com.github.essien.banquemisr.irrigation.exception.LandNotFoundException;
import com.github.essien.banquemisr.irrigation.job.IrrigationJob;
import com.github.essien.banquemisr.irrigation.model.LandModel;
import com.github.essien.banquemisr.irrigation.model.PageModel;
import com.github.essien.banquemisr.irrigation.repo.LandRepository;
import com.github.essien.banquemisr.irrigation.scheduler.JobManager;
import com.github.essien.banquemisr.irrigation.service.LandService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ma.glasnost.orika.MapperFacade;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link LandService}.
 * <p>
 * @author bodmas
 * @since Nov 26, 2022.
 */
@Service
public class LandServiceImpl implements LandService {

    private static final Logger log = LoggerFactory.getLogger(LandServiceImpl.class);

    private static final String JOB_GROUP = "irrigations";

    private final LandRepository landRepository;
    private final MapperFacade mapperFacade;
    private final JobManager jobManager;

    public LandServiceImpl(LandRepository landRepository, MapperFacade mapperFacade, JobManager jobManager) {
        this.landRepository = landRepository;
        this.mapperFacade = mapperFacade;
        this.jobManager = jobManager;
    }

    @Override
    public void create(LandModel landModel) {
        if (landRepository.existsByKey(landModel.getLandId()))
            throw new DuplicateLandException(landModel.getLandId());
        Land land = new Land();
        land.setKey(landModel.getLandId());
        land.setArea(landModel.getArea());
        landRepository.save(land);
    }

    @Override
    public void configure(LandModel landModel) {
        Land land = getLand(landModel.getLandId());
        List<WaterConfig> waterConfigs = mapperFacade.mapAsList(landModel.getWaterConfigs(), WaterConfig.class);
        land.setWaterConfigs(waterConfigs);
        landRepository.save(land);

        boolean error = false;

        List<String> jobNames = new ArrayList<>();
        for (WaterConfig waterConfig : waterConfigs) {
            try {
                String jobName = UUID.randomUUID().toString();
                final JobDataMap jobDataMap = new JobDataMap();

                jobDataMap.put("landId", landModel.getLandId());
                jobDataMap.put("duration", waterConfig.getDuration());
                jobManager.addJob(IrrigationJob.class, jobName, JOB_GROUP, jobDataMap, waterConfig.getCron());
                jobNames.add(jobName);
            } catch (SchedulerException ex) {
                error = true;
                break;
            }
        }
        if (error) {
            for (String jobName : jobNames) {
                try {
                    if (jobManager.jobExists(jobName, JOB_GROUP))
                        jobManager.removeJob(jobName, JOB_GROUP);
                } catch (SchedulerException ex) {
                    log.warn("Error removing job " + jobName + ", " + JOB_GROUP);
                }
            }
            landRepository.delete(land);
            throw new IrrigationSchedulerException();
        }
    }

    @Override
    public LandModel update(LandModel landModel) {
        Land land = getLand(landModel.getLandId());
        land.setArea(landModel.getArea());
        landRepository.save(land);
        return mapperFacade.map(land, LandModel.class);
    }

    private Land getLand(String key) {
        return landRepository.findByKey(key).orElseThrow(() -> new LandNotFoundException(key));
    }

    @Override
    public PageModel<LandModel> getAll(int page, int size) {
        return new PageModel<>(
                mapperFacade.mapAsList(landRepository.findAll(PageRequest.of(page, size)), LandModel.class), page, size
        );
    }
}
