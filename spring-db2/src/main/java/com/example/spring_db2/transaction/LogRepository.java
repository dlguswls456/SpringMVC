package com.example.spring_db2.transaction;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * JPA를 사용하는 회원 리포지토리. 저장과 조회 기능을 제공
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LogRepository {

    private final EntityManager em;

//    @Transactional
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 로그와 관련된 물리 트랜잭션을 별도로 분리
    public void save(Log logMessage) {
        log.info("log 저장");
        em.persist(logMessage);

        if(logMessage.getMessage().contains("로그예외")) {
            log.info("log 저장 시 예외 발생");
            throw new RuntimeException("예외 발생");
        }
    }

    public Optional<Log> find(String message) {
        return em.createQuery("select l from Log l where l.message = :message", Log.class)
                                        .setParameter("message", message).getResultList().stream().findAny();
    }

}
