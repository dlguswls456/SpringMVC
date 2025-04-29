package com.example.scheduling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 멀티 스레드로 여러 스케줄러 가동
 */
@Slf4j
@Component
public class MultiThreadScheduler {

//    @Scheduled(fixedRate = 1000)
    public void fixedRate1() throws InterruptedException {
        log.info("{} fixedRate1() start {}", Thread.currentThread().getName(),
                                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        Thread.sleep(3000);
    }

//    @Scheduled(fixedRate = 1000)
    public void fixedRate2() throws InterruptedException {
        log.info("{} fixedRate2() start {}", Thread.currentThread().getName(),
                                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        Thread.sleep(3000);
    }

}
