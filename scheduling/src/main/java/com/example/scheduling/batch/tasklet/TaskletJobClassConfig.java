package com.example.scheduling.batch.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@EnableBatchProcessing
@RequiredArgsConstructor
@Configuration
public class TaskletJobClassConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    
    @Bean
    public Job taskletJob() {
        return new JobBuilder("taskletJpb", jobRepository)
                                        .start(taskStep())
                                        .build();
    }
    
    @Bean
    public Step taskStep() {
        return new StepBuilder("taskletStep", jobRepository)
                                        .tasklet(new BusinessTasklet(), transactionManager)
                                        .build();
    }

}
