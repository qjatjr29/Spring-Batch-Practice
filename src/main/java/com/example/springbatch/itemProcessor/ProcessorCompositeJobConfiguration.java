package com.example.springbatch.itemProcessor;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProcessorCompositeJobConfiguration {

  public static final String JOB_NAME = "processorCompositeBatch";
  public static final String BEAN_PREFIX = JOB_NAME + "_";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory emf;

  @Value("${chunkSize:1000}")
  private int chunkSize;

  @Bean(JOB_NAME)
  public Job compositeJob() {
    return jobBuilderFactory.get(JOB_NAME)
        .preventRestart()
        .start(compositeStep())
        .build();
  }

  @Bean(BEAN_PREFIX + "step")
  @JobScope
  public Step compositeStep() {
    return stepBuilderFactory.get(BEAN_PREFIX + "step")
        .<Teacher, String>chunk(chunkSize)
        .reader(compositeReader())
        .processor(compositeProcessor())
        .writer(compositeWriter())
        .build();
  }

  @Bean
  public JpaPagingItemReader<Teacher> compositeReader() {
    return new JpaPagingItemReaderBuilder<Teacher>()
        .name(BEAN_PREFIX+"reader")
        .entityManagerFactory(emf)
        .pageSize(chunkSize)
        .queryString("SELECT t FROM Teacher t")
        .build();
  }

  @Bean
  public CompositeItemProcessor compositeProcessor() {
    List<ItemProcessor> delegates = new ArrayList<>(2);
    delegates.add(compositeProcessor1());
    delegates.add(compositeProcessor2());

    CompositeItemProcessor processor = new CompositeItemProcessor<>();

    processor.setDelegates(delegates);

    return processor;
  }

  public ItemProcessor<Teacher, String> compositeProcessor1() {
    return Teacher::getName;
  }

  public ItemProcessor<String, String> compositeProcessor2() {
    return name -> " 안녕하세요  :  "+ name + "입니다.";
  }

  private ItemWriter<String> compositeWriter() {
    return items -> {
      for (String item : items) {
        log.info("Teacher Name={}", item);
      }
    };
  }
}