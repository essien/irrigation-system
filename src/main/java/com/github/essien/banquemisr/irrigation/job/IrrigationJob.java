package com.github.essien.banquemisr.irrigation.job;

import com.github.essien.banquemisr.irrigation.exception.DeviceUnavailableException;
import com.github.essien.banquemisr.irrigation.integration.SensorDevice;
import com.github.essien.banquemisr.irrigation.model.Instruction;
import com.github.essien.banquemisr.irrigation.scheduler.JobManager;
import com.github.essien.banquemisr.irrigation.service.IrrigationExecutionService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bodmas
 * @since Nov 27, 2022.
 */
public class IrrigationJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(IrrigationJob.class);
    private static final String RETRIALS_REMAINING = "retrialsRemaining";
    private static final int RETRY_INTERVAL = 30;

    private final int MAX_RETRIAL_ATTEMPTS = 3;

    @Autowired
    private SensorDevice sensorDevice;
    @Autowired
    private JobManager jobManager;
    @Autowired
    private IrrigationExecutionService irrigationExecutionService;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        JobDataMap jobDataMap = jec.getJobDetail().getJobDataMap();
        final String landId = (String) jobDataMap.get("landId");
        final Integer duration = (Integer) jobDataMap.get("duration");
        final Boolean isRetry = (Boolean) jobDataMap.getOrDefault("isRetry", Boolean.FALSE);

        try {
            final LocalDateTime executionTime = LocalDateTime.now();
            sensorDevice.irrigate(Instruction.builder().withLandId(landId).withDuration(duration).build());
            irrigationExecutionService.save(landId, executionTime);
        } catch (DeviceUnavailableException ex) {
            log.debug("Device was unavailable", ex);

            // Implement retry mechanism.
            if (isRetry.equals(Boolean.FALSE)) {
                // Fresh failure.
                jobDataMap.put("isRetry", Boolean.TRUE);
                jobDataMap.put(RETRIALS_REMAINING, MAX_RETRIAL_ATTEMPTS);
                scheduleRetrial(jec, jobDataMap);
            } else {
                // Already in retrial.
                Integer retrialsRemaining = (Integer) jobDataMap.get(RETRIALS_REMAINING);
                retrialsRemaining--;
                if (retrialsRemaining <= 0)
                    return;
                jobDataMap.put(RETRIALS_REMAINING, retrialsRemaining);
                scheduleRetrial(jec, jobDataMap);
            }
        }
    }

    private void scheduleRetrial(JobExecutionContext jec, JobDataMap jobDataMap) {
        try {
            jobManager.addJob(IrrigationJob.class, "retrial", jec.getJobDetail().getKey().getGroup(),
                              jobDataMap, nextRetryExecutionTime());
        } catch (SchedulerException ex1) {
            log.debug("Error scheduling retrial", ex1);
        }
    }

    private static Date nextRetryExecutionTime() {
        return Date.from(LocalDateTime.now().plusSeconds(RETRY_INTERVAL).toInstant(ZoneOffset.UTC));
    }
}
