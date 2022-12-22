package com.example.springbatch.quartz.util;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import com.example.springbatch.quartz.HelloJob;
import com.example.springbatch.quartz.dto.JobRequest;
import com.example.springbatch.quartz.job.CronJob;
import com.example.springbatch.quartz.job.SimpleJob;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

public class JobUtils {

  public static JobDetail createJobDetail(JobRequest jobRequest, Class<? extends Job> jobClass) {

    JobDetail jobDetail;

    if(jobClass.equals(SimpleJob.class)) {
      jobDetail = newJob(SimpleJob.class)
          .withDescription("Simple Job")
          .withIdentity(jobRequest.getJobName(), jobRequest.getJobGroup())
          .usingJobData("num", 0)
          .usingJobData("name", jobRequest.getJobName())
          .build();
    } else {
      jobDetail = newJob(CronJob.class)
          .withDescription("Cron Job")
          .withIdentity(jobRequest.getJobName(), jobRequest.getJobGroup())
          .usingJobData("num", 0)
          .build();
    }

    return jobDetail;
  }

  public static Trigger createTrigger(JobRequest jobRequest) {
    Trigger trigger = newTrigger()
        .withIdentity(jobRequest.getJobName(), jobRequest.getJobGroup())
        .startNow()
        .withSchedule(simpleSchedule()
            .withIntervalInSeconds(5)
            .repeatForever())
        .build();

    return trigger;
  }

}
