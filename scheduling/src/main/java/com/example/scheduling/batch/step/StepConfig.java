package com.example.scheduling.batch.step;

import java.sql.SQLException;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.scheduling.batch.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
//@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class StepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ItemReader<Member> reader;
    private final ItemProcessor<Member, Member> processor;
    private final ItemWriter<Member> writer;

    /**
     * startLimit은 해당 Step의 실패 이후 재시작 가능 횟수를 의미
     * 
     */
    @Bean
    @JobScope
    public Step startLimit() {
        return new StepBuilder("startLimitStep", jobRepository)
                                        .startLimit(3)
                                        .<Member, Member>chunk(10, transactionManager)
                                        .reader(reader)
                                        .processor(processor)
                                        .writer(writer)
                                        .build();
    }

    /**
     * skip(예외): 특정 예외가 발생하면 해당 아이템을 건너뛰고 다음 아이템으로 진행
     * noSkip(예외): 특정 예외가 발생하면 즉시 Step 실패 처리
     * skipLimit 횟수를 초과하게 되면 Step은 실패 처리됨
     * .faultTolerant() 필수! : 예외가 발생했을 때 전체 Step을 실패 처리하지 않고, 일부 아이템만 건너뛰거나 재시도 가능
     */
    @Bean
    @JobScope
    public Step skip() {
        return new StepBuilder("skipStep", jobRepository)
                                        .<Member, Member>chunk(10, transactionManager)
                                        .reader(reader)
                                        .processor(processor)
                                        .writer(writer)
                                        .faultTolerant()
                                        .skipLimit(1) // skip 허용 횟수, 해당 횟수 초과 시 에러 발생, skip 사용시 필수 설정
                                        .skip(NullPointerException.class) // NullPointerException에 대해선 Skip
                                        .noSkip(SQLException.class) // SQLException에 대해선 noSkip
//                                        .skipPolicy(new CustomSkipPolilcy) // 사용자가 커스텀하며 Skip Policy 설정 가능
                                        .build();
    }

    /**
     * retry(예외): 특정 예외가 발생하면 해당 아이템을 다시 시도
     * noRetry(예외): 특정 예외가 발생하면 즉시 Step 실패 처리
     * skipLimit 횟수를 초과하게 되면 Step은 실패 처리됨
     * .faultTolerant() 필수!
     */
    @Bean
    @JobScope
    public Step retry() {
        return new StepBuilder("retryStep", jobRepository)
                                        .<Member, Member>chunk(10, transactionManager)
                                        .reader(reader)
                                        .processor(processor)
                                        .writer(writer)
                                        .faultTolerant()
                                        .retryLimit(1) // retry 횟수, retry 사용시 필수 설정, 해당 Retry 이후 Exception시 Fail 처리
                                        .retry(SQLException.class) // SQLException에 대해선 Retry
                                        .noRetry(NullPointerException.class) // NullPointerException에 대해선 noRetry
//                                        .retryPolicy(new CustomRetryPolilcy) // 사용자가 커스텀하며 Retry Policy 설정 가능
                                        .build();
    }

    /**
     * retry(예외): 특정 예외가 발생하면 해당 아이템을 다시 시도
     * noRetry(예외): 특정 예외가 발생하면 즉시 Step 실패 처리
     * skipLimit 횟수를 초과하게 되면 Step은 실패 처리됨
     * .faultTolerant() 필수!
     */
    @Bean
    @JobScope
    public Step noRollback() {
        return new StepBuilder("noRollbackStep", jobRepository)
                                        .<Member, Member>chunk(10, transactionManager)
                                        .reader(reader)
                                        .processor(processor)
                                        .writer(writer)
                                        .faultTolerant()
                                        .noRollback(NullPointerException.class) // NullPointerException에 대해선 rollback이 되지 않게 설정
                                        .build();
    }

}
