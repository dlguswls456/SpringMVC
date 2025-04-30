package com.example.scheduling;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class ChunkJobTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job exampleJob;

    @Test
    public void jobSchduled() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
                                    JobRestartException, JobInstanceAlreadyCompleteException, InterruptedException {
        // 현재 시간을 JobParameter로 사용 (중복 실행 방지용)
        JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();

        // Job 실행
        JobExecution jobExecution = jobLauncher.run(exampleJob, jobParameters);

        // 상태 모니터링
        while(jobExecution.isRunning()) {
            log.info("Job is running...");
            Thread.sleep(500);
        }

        log.info("Job Execution: " + jobExecution.getStatus());
        log.info("Job getJobId: " + jobExecution.getJobId());
        log.info("Job getExitStatus: " + jobExecution.getExitStatus());
        log.info("Job getJobInstance: " + jobExecution.getJobInstance());
        log.info("Job getStepExecutions: " + jobExecution.getStepExecutions());
        log.info("Job getLastUpdated: " + jobExecution.getLastUpdated());
        log.info("Job getFailureExceptions: " + jobExecution.getFailureExceptions());

    }

}
