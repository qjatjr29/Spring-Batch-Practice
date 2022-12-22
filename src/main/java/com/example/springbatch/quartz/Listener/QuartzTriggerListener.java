package com.example.springbatch.quartz.Listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

@Slf4j
public class QuartzTriggerListener implements TriggerListener {

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public void triggerFired(Trigger trigger, JobExecutionContext context) {
    log.info("=== Trigger Listener : Trigger 실행 ===");
  }

  /**
   * 결과가 true -> JobListener jobExecutionVetoed 실행 - job 중단
   */
  @Override
  public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
    log.info("=== Trigger Listener : Trigger 상태 체크 ===");
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

    int executeCount = 1;
    if(jobDataMap.containsKey("executeCount")) {
      executeCount = (int) jobDataMap.get("executeCount");
    }

    return executeCount >= 2;
  }

  @Override
  public void triggerMisfired(Trigger trigger) {

  }

  @Override
  public void triggerComplete(Trigger trigger, JobExecutionContext context,
      CompletedExecutionInstruction triggerInstructionCode) {

  }
}
