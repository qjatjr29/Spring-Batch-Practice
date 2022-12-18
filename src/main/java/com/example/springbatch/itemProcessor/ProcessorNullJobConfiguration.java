package com.example.springbatch.itemProcessor;

import com.example.springbatch.Pay;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProcessorNullJobConfiguration {

  public static final String JOB_NAME = "processorNullBatch";
  public static final String BEAN_PREFIX = JOB_NAME + "_";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory emf;

  @Value("${chunkSize:1000}")
  private int chunkSize;

  @Bean(JOB_NAME)
  public Job processorNullJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .preventRestart()
        .start(processorNullStep())
        .build();
  }

  @Bean(BEAN_PREFIX + "step")
  @JobScope
  public Step processorNullStep() {
    return stepBuilderFactory.get(BEAN_PREFIX + "step")
        .<Pay, Pay>chunk(chunkSize)
        .reader(processorNullReader())
        .processor(processorNull())
        .writer(processorNullWriter())
        .build();
  }

  @Bean
  public JpaPagingItemReader<Pay> processorNullReader() {
    return new JpaPagingItemReaderBuilder<Pay>()
        .name(BEAN_PREFIX+"reader")
        .entityManagerFactory(emf)
        .pageSize(chunkSize)
        .queryString("SELECT p FROM Pay p")
        .build();
  }

  @Bean
  public ItemProcessor<Pay, Pay> processorNull() {
    return pay -> {

      boolean isIgnoreTarget = pay.getId() % 2 == 0L;
      if(isIgnoreTarget){
        log.info("====== Pay name={}, isIgnoreTarget={}", pay.getTxName(), isIgnoreTarget);
        return null;
      }

      return pay;
    };
  }

  private ItemWriter<Pay> processorNullWriter() {
    return items -> {
      for (Pay item : items) {
        log.info("Pay Name={}", item.getTxName());
      }
    };
  }
}