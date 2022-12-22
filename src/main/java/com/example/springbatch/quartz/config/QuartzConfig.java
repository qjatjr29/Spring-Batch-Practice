package com.example.springbatch.quartz.config;

import com.example.springbatch.quartz.Listener.QuartzJobListener;
import com.example.springbatch.quartz.Listener.QuartzTriggerListener;
import com.example.springbatch.quartz.job.AutoWiringSpringBeanJobFactory;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.quartz.JobListener;
import org.quartz.TriggerListener;

import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@RequiredArgsConstructor
@Configuration
public class QuartzConfig {

  private final DataSource dataSource;
  private final QuartzProperties quartzProperties;
  private final TriggerListener triggersListener = new QuartzTriggerListener();
  private final JobListener jobsListener = new QuartzJobListener();


  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));

    AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    schedulerFactoryBean.setJobFactory(jobFactory);

    schedulerFactoryBean.setApplicationContext(applicationContext);

    Properties properties = new Properties();
    properties.putAll(quartzProperties.getProperties());

    schedulerFactoryBean.setGlobalTriggerListeners(triggersListener);
    schedulerFactoryBean.setGlobalJobListeners(jobsListener);
    schedulerFactoryBean.setOverwriteExistingJobs(true);
    schedulerFactoryBean.setDataSource(dataSource);
    schedulerFactoryBean.setQuartzProperties(properties);
    schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
    return schedulerFactoryBean;
  }

}
