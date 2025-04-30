package com.example.scheduling.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Flow를 통한 Step 구성
 */
@Slf4j
//@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class FlowStepJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job exampleJob() {
        return new JobBuilder("exampleJob", jobRepository)
                                        .start(startStep())
                                            .on("FAILED") // startStep의 ExitStatus가 FAILED인 경우
                                            .to(failOverStep()) // failOverStep을 실행
                                            .on("*") // failOverStep의 결과와 상관없이
                                            .to(writeStep()) // writeStep을 실행
                                            .on("*") // writeStep의 결과와 상관없이
                                            .end() // Flow를 종료
                                        
                                        
                                        .from(startStep()) // startStep이 FAILED가 아니고
                                            .on("COMPLETED") // COMPLETED인 경우
                                            .to(processStep()) // processStep을 실행
                                            .on("*") // processStep의 결과와 상관없이
                                            .to(writeStep()) // writeStep을 실행
                                            .on("*") // writeStep의 결과와 상관없이
                                            .end() // Flow를 종료
                                            
                                        .from(startStep()) // startStep의 결과가 FAILED, COMPLETED가 아닌
                                            .on("*") // 모든 경우
                                            .to(writeStep()) // writeStep을 실행
                                            .on("*") // writeStep의 결과와 상관없이
                                            .end() // Flow를 종료
                                        .end()
                                        .build();
    }

    @Bean
    public Step startStep() {
        return new StepBuilder("startStep", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("startStep!");

            String result = "COMPLETED";
//            String result = "FAIL";
//            String result = "UNKNOWN";

            // Flow에서 on은 RepeatStatus가 아닌 ExitStatus를 바라본다.
            if(result.equals("COMPLETED")) {
                contribution.setExitStatus(ExitStatus.COMPLETED);
            } else if(result.equals("FAIL")) {
                contribution.setExitStatus(ExitStatus.FAILED);
            } else if(result.equals("UNKNOWN")) {
                contribution.setExitStatus(ExitStatus.UNKNOWN);
            }

            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }
    
    @Bean
    public Step failOverStep() {
        return new StepBuilder("failOverStep", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("failOverStep!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Step processStep() {
        return new StepBuilder("processStep", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("processStep!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

    @Bean
    public Step writeStep() {
        return new StepBuilder("writeStep", jobRepository).tasklet((contribution, chunkContext) -> {
            log.info("writeStep!");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }

}
