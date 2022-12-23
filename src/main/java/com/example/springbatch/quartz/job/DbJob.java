package com.example.springbatch.quartz.job;

import com.example.springbatch.user.service.UserService;
import java.time.LocalDateTime;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

public class DbJob extends QuartzJobBean {

  private static Logger log = LoggerFactory.getLogger(SimpleJob.class);

  @Autowired
  private UserService userService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    JobKey jobKey = context.getJobDetail().getKey();
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

    LocalDateTime now = LocalDateTime.now();

    // 현재 시간을 기반으로 삭제된지 60초 지난 데이터 삭제하기
    userService.deleteStep(now);
  }
}
