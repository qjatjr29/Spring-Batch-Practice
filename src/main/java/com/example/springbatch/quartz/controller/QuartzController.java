package com.example.springbatch.quartz.controller;

import com.example.springbatch.quartz.common.ApiResponse;
import com.example.springbatch.quartz.dto.JobRequest;
import com.example.springbatch.quartz.dto.JobResponse;
import com.example.springbatch.quartz.dto.JobResponse.StatusResponse;
import com.example.springbatch.quartz.job.CronJob;
import com.example.springbatch.quartz.job.SimpleJob;
import com.example.springbatch.quartz.service.SchedulerServiceImpl;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@RestController
public class QuartzController {

  private final SchedulerServiceImpl schedulerServiceImpl;

  public QuartzController(SchedulerServiceImpl schedulerServiceImpl) {
    this.schedulerServiceImpl = schedulerServiceImpl;
  }

  @PostMapping("/jobs")
  public ResponseEntity<?> addScheduleJob(@RequestBody JobRequest jobRequest) throws SchedulerException {

    System.out.println("==== request POST ====");
    System.out.println(jobRequest.toString());

    JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());

    if(!schedulerServiceImpl.isJobExist(jobKey)) {
      if(jobRequest.getCronExpression() == null) {
        schedulerServiceImpl.addJob(jobRequest, SimpleJob.class);
      } else {
        schedulerServiceImpl.addJob(jobRequest, CronJob.class);
      }
    } else {
      return new ResponseEntity<>(new ApiResponse(false, "Job already exists"), HttpStatus.CONFLICT);
    }

    return new ResponseEntity<>(new ApiResponse(true, "Job created Successfully"), HttpStatus.CREATED);
  }

  @GetMapping("/jobs")
  public ResponseEntity<JobResponse.StatusResponse> findAll() {
    StatusResponse allJobs = schedulerServiceImpl.getAllJobs();
    return new ResponseEntity<>(allJobs, HttpStatus.OK);
  }


}
