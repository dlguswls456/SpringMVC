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
 * 다중 Step
 */
@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class MultiStepJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job exampleJob() {
        return new JobBuilder("exampleJob", jobRepository)
                                        .start(firstStep())
                                        .next(secondStep())
                                        .next(thirdStep())
                                        .build();
    }

    @Bean
    public Step firstStep() {
        return new StepBuilder("firstStep", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("firstStep!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Step secondStep() {
        return new StepBuilder("secondStep", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("secondStep!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Step thirdStep() {
        return new StepBuilder("thirdStep", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("thirdStep!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

}
