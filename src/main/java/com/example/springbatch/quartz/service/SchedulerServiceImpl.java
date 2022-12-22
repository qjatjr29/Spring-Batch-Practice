package com.example.springbatch.quartz.service;

import com.example.springbatch.quartz.dto.JobRequest;
import com.example.springbatch.quartz.dto.JobResponse;
import com.example.springbatch.quartz.util.DateTimeUtils;
import com.example.springbatch.quartz.util.JobUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service("quartz")
public class SchedulerServiceImpl {

  private static Logger log = LoggerFactory.getLogger(SchedulerServiceImpl.class);

  @Autowired
  private SchedulerFactoryBean schedulerFactoryBean;

  private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();

//  private final SchedulerFactoryBean schedulerFactoryBean;
//
//  public SchedulerServiceImpl(
//      SchedulerFactoryBean schedulerFactoryBean) {
//    this.schedulerFactoryBean = schedulerFactoryBean;
//  }

  public boolean addJob(JobRequest jobRequest, Class<? extends Job> jobClass) {

    JobKey jobKey = null;
    JobDetail jobDetail;
    Trigger trigger;

    try {
      trigger = JobUtils.createTrigger(jobRequest);
      jobDetail = JobUtils.createJobDetail(jobRequest, jobClass);
      jobKey = JobKey.jobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
      Scheduler scheduler = schedulerFactoryBean.getScheduler();

      Date dt = scheduler.scheduleJob(jobDetail, trigger);
      log.debug("Job with jobKey : {} scheduled successfully at date : {}", jobDetail.getKey(), dt);
      return true;
    } catch (SchedulerException e) {
      log.error("error occurred while scheduling with jobKey : {}", jobKey, e);
    }
    return false;
  }

  public boolean isJobExist(JobKey jobKey) throws SchedulerException {
    return schedulerFactoryBean.getScheduler().checkExists(jobKey);
  }

  public JobResponse.StatusResponse getAllJobs() {

    List<JobResponse.JobDetail> jobs = new ArrayList<>();
    int numOfRunningJobs = 0;
    int numOfGroups = 0;
    int numOfAllJobs = 0;

    try {
      Scheduler scheduler = schedulerFactoryBean.getScheduler();
      for(String groupName : scheduler.getJobGroupNames()) {
        numOfGroups++;
        for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
          List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

          JobResponse.JobDetail job = JobResponse.JobDetail.builder()
              .jobName(jobKey.getName())
              .groupName(jobKey.getGroup())
              .scheduleTime(DateTimeUtils.toString(triggers.get(0).getStartTime()))
              .lastFiredTime(DateTimeUtils.toString(triggers.get(0).getPreviousFireTime()))
              .nextFireTime(DateTimeUtils.toString(triggers.get(0).getNextFireTime()))
              .build();

          if(isJobRunning(jobKey)) {
            job.setJobStatus("RUNNING");
            numOfRunningJobs++;
          } else {
            String jobState = getJobState(jobKey);
            job.setJobStatus(jobState);
          }

          numOfAllJobs++;
          jobs.add(job);
        }
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }

    return JobResponse.StatusResponse.builder()
        .numOfAllJobs(numOfAllJobs)
        .numOfGroups(numOfGroups)
        .numOfRunningJobs(numOfRunningJobs)
        .jobs(jobs)
        .build();
  }

  private String getJobState(JobKey jobKey) {

    return null;
  }

  private boolean isJobRunning(JobKey jobKey) {
    return false;
  }

}
