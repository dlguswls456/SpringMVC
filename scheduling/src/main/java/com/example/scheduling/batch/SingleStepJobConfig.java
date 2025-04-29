package com.example.scheduling.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 단일 Step
 */
@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SingleStepJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job exampleJob() {
        return new JobBuilder("exampleJob", jobRepository).start(step()).build();
    }

    @Bean
    public Step step() {
        return new StepBuilder("step", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("Step!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

}
