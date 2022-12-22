package com.example.springbatch.quartz.job;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SimpleJob extends QuartzJobBean {

  private static Logger log = LoggerFactory.getLogger(SimpleJob.class);

  private int MAX_SLEEP_IN_SECONDS = 5;
  private volatile Thread currThread;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    JobKey jobKey = context.getJobDetail().getKey();
    currThread = Thread.currentThread();
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    String name = (String) jobDataMap.get("name");

    IntStream.range(0, 5).forEach(i -> {
      log.info("SimpleJob " + name + " Counting - {}", i);
      try {
        TimeUnit.SECONDS.sleep(MAX_SLEEP_IN_SECONDS);
      } catch (InterruptedException e) {
        log.error(e.getMessage(), e);
      }
    });
  }

}
