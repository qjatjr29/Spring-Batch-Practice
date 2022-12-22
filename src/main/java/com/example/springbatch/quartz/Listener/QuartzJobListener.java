package com.example.springbatch.quartz.Listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class QuartzJobListener implements JobListener {

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public void jobToBeExecuted(JobExecutionContext context) {
    log.info("=== Job Listener : Job 수행 전 ===");
  }

  @Override
  public void jobExecutionVetoed(JobExecutionContext context) {
    log.info("=== Job Listener : Job 중단 ===");
  }

  @Override
  public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
    log.info("=== Job Listener : Job 수행 후 ===");
  }
}
