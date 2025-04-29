package com.example.scheduling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class CronScheduler {

    /**
     
    1 2 3 4 5 6  // 순서
    * * * * * *  // 실행주기 문자열

    // 순서별 정리
    1. 초(0-59)
    2. 분(0-59)
    3. 시간(0-23)
    4. 일(1-31)
    5. 월(1-12)
    6. 요일(0-7)

    "0 0 * * * *" = 매 시간마다 실행한다.
    "* / 10 * * * * *" = 매 10초마다 실행한다.
    "0 0 8-10 * * *" = 매일 8, 9, 10시에 실행한다
    "0 0 6,19 * * *" = 매일 오전 6시, 오후 7시에 실행한다.
    "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
    "0 0 9-17 * * MON-FRI" = 오전 9시부터 오후 5시까지 주중(월~금)에 실행한다.
    "0 0 0 25 12 ?" = every Christmas Day at midnight
    */
//    @Scheduled(cron = "*/2 * * * * *")
    public void cron() {
        log.info("Cron Scheduler Error Started per 2 seconds "
                                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
    }
}
