package com.example.springbatch.quartz.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class JobResponse {

  @Getter
  @Setter
  @Builder
  public static class JobDetail {
    private String jobName;
    private String groupName;
    private String scheduleTime;
    private String lastFiredTime;
    private String nextFireTime;
    private String jobStatus;
  }

  @Getter
  @Setter
  @Builder
  public static class StatusResponse {
    private Integer numOfAllJobs;
    private Integer numOfRunningJobs;
    private Integer numOfGroups;
    private List<JobDetail> jobs;
  }

}
