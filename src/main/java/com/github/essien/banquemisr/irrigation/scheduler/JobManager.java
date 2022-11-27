package com.github.essien.banquemisr.irrigation.scheduler;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.codehaus.commons.nullanalysis.Nullable;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import org.quartz.Job;
import org.quartz.JobBuilder;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import static org.quartz.JobKey.jobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bodmas
 * @since Nov 27, 2022.
 */
@Service
public class JobManager {

    @Autowired
    private Scheduler scheduler;
    private static final String TRIGGER_PREFIX = "_pr";

    @PostConstruct
    public void init() {
        try {
            System.out.println("scheduler is " + scheduler);
            if (!scheduler.isStarted())
                scheduler.start();

            addJob(SampleJob.class, "jobName1", "jobGroup1", new JobDataMap(), "0/5 * * ? * * *");

        } catch (SchedulerException ex) {
            System.out.println("scheduler error: " + ex.getMessage());
        }
    }

    @PreDestroy
    public void terminate() throws SchedulerException {
        if (scheduler.isStarted())
            scheduler.shutdown(true);
    }

    public <T extends Job> boolean cancelJob(Class<T> jobClass, String jobName, String jobGroup, @Nullable JobDataMap jobDataMap,
                                             String cron) throws SchedulerException {
        return scheduler.unscheduleJob(triggerKey(jobName + TRIGGER_PREFIX, jobGroup));
    }

    public <T extends Job> boolean removeJob(Class<T> jobClass, String jobName, String jobGroup, @Nullable JobDataMap jobDataMap,
                                             String cron) throws SchedulerException {

        return scheduler.deleteJob(jobKey(jobName, jobGroup));
    }

    public <T extends Job> Date addJob(Class<T> jobClass, String jobName, String jobGroup, @Nullable JobDataMap jobDataMap, String cron) throws SchedulerException {
        Trigger jobTrigger = buildCronTrigger(jobName + TRIGGER_PREFIX, jobGroup, cron);
        return addQuartzJob(jobTrigger, jobClass, jobName, jobGroup, true, jobDataMap);
    }

    public <T extends Job> Date updateJob(Class<T> jobClass, String jobName, String jobGroup,
                                          @Nullable JobDataMap jobDataMap, String cron) throws SchedulerException {
        Trigger jobTrigger = buildCronTrigger(jobName + TRIGGER_PREFIX, jobGroup, cron);
        return updateQuartzJob(jobTrigger, jobClass, jobName, jobGroup, true, jobDataMap);
    }

    private <T extends Job> Date updateQuartzJob(Trigger jobTrigger, Class<T> jobClass, String jobName, String jobGroup,
                                                 boolean isDurable, @Nullable JobDataMap jobDataMap) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(jobClass, jobName, jobGroup, isDurable, jobDataMap);
        scheduler.addJob(jobDetail, true);
        return scheduler.rescheduleJob(triggerKey(jobTrigger.getKey().getName(), jobGroup), jobTrigger);
    }

    private Trigger buildCronTrigger(String name, String group, String cron) {
        return newTrigger()
                .withIdentity(name, group)
                .withSchedule(cronSchedule(cron))
                .build();
    }

    private <T extends Job> Date addQuartzJob(Trigger jobTrigger, Class<T> jobClass, String jobName, String jobGroup,
                                              boolean isDurable, @Nullable JobDataMap jobDataMap) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(jobClass, jobName, jobGroup, isDurable, jobDataMap);
        return scheduler.scheduleJob(jobDetail, jobTrigger);
    }

    private <T extends Job> JobDetail buildJobDetail(Class<T> job, String name, String group, boolean isDurable,
                                                     @Nullable JobDataMap dataMap) {
        JobBuilder builder = newJob(job)
                .withIdentity(name, group)
                .storeDurably(isDurable);

        if (dataMap != null) {
            builder.usingJobData(dataMap);
        }

        return builder.build();
    }
}
