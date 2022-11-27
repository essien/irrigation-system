package com.github.essien.banquemisr.irrigation.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author bodmas
 * @since Nov 27, 2022.
 */
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("running my quartz job");
    }
}
