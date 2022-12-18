package com.example.springbatch.simpleJob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

//  public SimpleJobConfiguration(
//      JobBuilderFactory jobBuilderFactory,
//      StepBuilderFactory stepBuilderFactory) {
//    this.jobBuilderFactory = jobBuilderFactory;
//    this.stepBuilderFactory = stepBuilderFactory;
//  }

  private final SimpleJobTasklet tasklet1;

  @Bean
  public Job simpleJob() {
    return jobBuilderFactory.get("simpleJob")
        .start(simpleStep1())
        .next(simpleStep2(null))
        .build();
  }


//  @Bean
//  @JobScope
//  public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
//    return stepBuilderFactory.get("simpleStep1")
//        .tasklet((contribution, chunkContext) -> {
//          throw new IllegalArgumentException("===step1 실패====");
//        })
//        .build();
//  }


  public Step simpleStep1() {
    log.info("===== simple Step1 =====");
    return stepBuilderFactory.get("simpleStep1")
        .tasklet(tasklet1)
        .build();
  }

  @Bean
  @JobScope
  public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
    return stepBuilderFactory.get("simpleStep2")
        .tasklet((contribution, chunkContext) -> {
          log.info("======== STEP 2 ======");
          log.info("======== RequestDate : {} ======", requestDate);
          return RepeatStatus.FINISHED;
        })
        .build();
  }


}
