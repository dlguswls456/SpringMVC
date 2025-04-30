package com.example.scheduling.batch.chunk;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.scheduling.batch.domain.Account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 전체 금액이 10,000원 이상인 회원들에게 1,000원 캐시백을 주는 배치
 */
@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ChunkJobJDBCConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    @Bean
    public Job exampleJob() throws Exception {
        return new JobBuilder("exampleJob", jobRepository)
                                        .start(step())
                                        .build();
    }
    
    @Bean
    @JobScope
    public Step step() throws Exception {
        return new StepBuilder("step", jobRepository)
                                        .<Account, Account>chunk(10, transactionManager)
                                        .reader(reader())
                                        .processor(processor())
                                        .writer(writer())
                                        .build();
    }
    
    @Bean
    @StepScope
    public JdbcPagingItemReader<Account> reader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", "10000");
        
        // pageSize와 fetchSize는 동일하게 설정
        return new JdbcPagingItemReaderBuilder<Account>()
                                        .name("JdbcPagingItemReader")
                                        .dataSource(dataSource)
                                        .fetchSize(10)
                                        .pageSize(10)
                                        .rowMapper(new BeanPropertyRowMapper<>(Account.class))
                                        .queryProvider(customQueryProvider())
                                        .parameterValues(parameterValues)
                                        .build();
    }
    
    @Bean
    @StepScope
    public ItemProcessor<Account, Account> processor() {
        return new ItemProcessor<Account, Account>() {
            
            @Override
            public Account process(Account item) throws Exception {
                // 1000원 추가 적립
                item.setMoney(item.getMoney() + 1000);
                return item;
            }
            
        };
    }
    
    @Bean
    @StepScope
    public JdbcBatchItemWriter<Account> writer() {
        return new JdbcBatchItemWriterBuilder<Account>()
                                        .dataSource(dataSource)
                                        .sql("UPDATE ACCOUNT SET MONEY = :money WHERE ID = :Id")
                                        .beanMapped()
                                        .build();
    }
    
    public PagingQueryProvider customQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setSelectClause("SELECT ID, MEMBER_ID AS memberId, MONEY AS money");
        factoryBean.setFromClause("FROM ACCOUNT");
        factoryBean.setWhereClause("WHERE MONEY >= :amount");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("ID", Order.ASCENDING);

        factoryBean.setSortKeys(sortKeys);

        return factoryBean.getObject();
    }
}
