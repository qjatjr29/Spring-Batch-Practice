package com.example.springbatch.quartz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

  private String jobName;
  private String jobGroup;
  private String cronExpression;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("JobRequest{");
    sb.append("jobName='").append(jobName).append('\'');
    sb.append(", jobGroup='").append(jobGroup).append('\'');
    sb.append(", cronExpression='").append(cronExpression).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
