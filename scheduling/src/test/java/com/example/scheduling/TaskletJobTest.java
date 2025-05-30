package com.example.scheduling;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TaskletJobTest {

    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job taskletJob;

    @Test
    void testTaskletJob() throws JobExecutionAlreadyRunningException, JobRestartException,
                                    JobInstanceAlreadyCompleteException, JobParametersInvalidException,
                                    InterruptedException {
        JobParameters jobParameters = new JobParametersBuilder()
                                        .addLong("time", System.currentTimeMillis()) // JobInstance 식별자
                                        .toJobParameters();

        jobLauncher.run(taskletJob, jobParameters);
    }

}
