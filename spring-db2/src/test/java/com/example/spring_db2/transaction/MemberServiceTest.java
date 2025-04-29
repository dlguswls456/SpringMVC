package com.example.spring_db2.transaction;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    LogRepository logRepository;

    /**
     * MemberService @Transactional:OFF
     * MemberRepository @Transactional:ON
     * LogRepository @Transactional:ON
     */
//    @Test
    void outerTxOff_success() {
        // given
        String username = "outerTxOff_success";
        // when
        memberService.joinV1(username);
        // then: 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }
    
    /**
    * MemberService @Transactional:OFF
    * MemberRepository @Transactional:ON
    * LogRepository @Transactional:ON Exception
    */
//    @Test
    void outerTxOff_fail() {
        // given
        String username = "로그예외_outerTxOff_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV1(username)).isInstanceOf(RuntimeException.class);

        // then: 완전히 롤백되지 않고, member 데이터가 남아서 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }
    
    /**
    * MemberService @Transactional:ON
    * MemberRepository @Transactional:OFF
    * LogRepository @Transactional:OFF
    */
//    @Test
    void singleTx() {
        // given
        String username = "singleTx";

        // when
        memberService.joinV1(username);

        // then: 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }
    
    /**
    * MemberService @Transactional:ON
    * MemberRepository @Transactional:ON
    * LogRepository @Transactional:ON
    */
//    @Test
    void outerTxOn_success() {
        // given
        String username = "outerTxOn_success";

        // when
        memberService.joinV1(username);

        // then: 모든 데이터가 정상 저장된다.
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isPresent());
    }
    
    /**
    * MemberService @Transactional:ON
    * MemberRepository @Transactional:ON
    * LogRepository @Transactional:ON Exception
    */
//    @Test
    void outerTxOn_fail() {
        // given
        String username = "로그예외_outerTxOn_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV1(username)).isInstanceOf(RuntimeException.class);

        // then: 모든 데이터가 롤백된다.
        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());
    }
    
    /**
    * MemberService @Transactional:ON
    * MemberRepository @Transactional:ON
    * LogRepository @Transactional:ON Exception
    */
    /**
     * 내부 트랜잭션에서 rollbackOnly를 설정하기 때문에
     * 결과적으로 정상 흐름 처리를 해서 외부 트랜잭션에서 커밋을 호출해도 
     * 물리 트랜잭션은 롤백
     * 그리고 UnexpectedRollbackException 이 던져짐
     */
//    @Test
    void recoverException_fail() {
        // given
        String username = "로그예외_recoverException_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV2(username)).isInstanceOf(UnexpectedRollbackException.class);

        // then: 모든 데이터가 롤백된다.
        assertTrue(memberRepository.find(username).isEmpty());
        assertTrue(logRepository.find(username).isEmpty());
    }
    
    /**
    * MemberService @Transactional:ON
    * MemberRepository @Transactional:ON
    * LogRepository @Transactional(REQUIRES_NEW) Exception
    * 
    * REQUIRES_NEW를 사용하여 로그와 관련된 물리 트랜잭션을 별도로 분리
    */
    @Test
    void recoverException_success() {
        // given
        String username = "로그예외_recoverException_success";

        // when
        memberService.joinV2(username);

        // then: member 저장, log 롤백
        assertTrue(memberRepository.find(username).isPresent());
        assertTrue(logRepository.find(username).isEmpty());
    }

}
