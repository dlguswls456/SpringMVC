package com.example.springdb1.repository;

import static com.example.springdb1.ConnectionConst.PASSWORD;
import static com.example.springdb1.ConnectionConst.URL;
import static com.example.springdb1.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.springdb1.domain.Member;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    /**
     * 의존성 주입을 변경해도 repository의 코드는 변하지 않는다
     */
    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션 획득
        // DriverManagerDataSource dataSource =
        // new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        // 커넥션 풀링: HikariProxyConnection -> JdbcConnection (커넥션 재사용 가능)
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException, InterruptedException {
        log.info("start");

        // save
        Member member = new Member("memberV0", 10000);
        repository.save(member);

        // findById
        Member memberById = repository.findById(member.getMemberId());
        assertThat(memberById).isNotNull();

        // update: money: 10000 -> 20000
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);
    }

}
