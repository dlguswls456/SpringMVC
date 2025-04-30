package com.example.scheduling.batch.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
//@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class TaskletJobAdapterConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job taskletJob() {
        return new JobBuilder("taskletJob", jobRepository)
                                        .start(taskStep()).build();
    }

    @Bean
    public Step taskStep() {
        return new StepBuilder("taskletStep", jobRepository)
                                        .tasklet(myTasklet(), transactionManager)
                                        .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter myTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(service());
        adapter.setTargetMethod("businessLogic");

        return adapter;
    }

    @Bean
    public CustomService service() {
        return new CustomService();
    }
}
