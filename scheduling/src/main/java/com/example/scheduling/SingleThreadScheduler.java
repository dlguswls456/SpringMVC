package com.example.scheduling;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class SingleThreadScheduler {

    // 정해진 시간 텀을 두고 반복
//    @Scheduled(fixedRate = 1000)
    public void fixedRate() {
        log.info("fixedRate Scheduler " + LocalDateTime.now());
    }

    // 작업이 정해진 텀보다 길어질 경우 문제가 발생할 수 있다 => 비동기로 스케줄링 하는 방법도 있다
//    @Scheduled(fixedRate = 1000)
    public void fixedRateError() throws InterruptedException {
        log.info("fixedRate Scheduler Error Started " + LocalDateTime.now());
        Thread.sleep(3000);
        log.info("fixedRate Scheduler Error Ended " + LocalDateTime.now());
    }

    // 비동기로 스케줄링 하는 방법
    @Async
    @Scheduled(fixedRate = 1000)
    public void fixedRateAsync() throws InterruptedException {
        log.info("fixedRate Scheduler Error Started " + LocalDateTime.now());
        Thread.sleep(3000);
        log.info("fixedRate Scheduler Error Ended " + LocalDateTime.now());
    }
  
    // 스케줄러 작업을 마치고 난 뒤와 다음 작업을 시작하는 시간 사이의 간격
//    @Scheduled(fixedDelay = 1000)
    public void fixedDelay() throws InterruptedException {
        log.info("fixedDelay Scheduler Started " + LocalDateTime.now());
        Thread.sleep(3000);
        log.info("fixedDelay Scheduler Ended " + LocalDateTime.now());
    }

}
