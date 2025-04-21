package com.example.springdb1.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import com.example.springdb1.domain.Member;
import com.example.springdb1.repository.MemberRepositoryV0;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MemberRepositoryTest {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memberV0", 1000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}", member == findMember); // false
        log.info("member equals findMember {}", member.equals(findMember)); // true (EqualsAndHashCode)
        assertThat(findMember).isEqualTo(member); // EqualsAndHashCode

        // update
        repository.update(member.getMemberId(), 5000);
        Member updateMember = repository.findById(member.getMemberId());
        log.info("updateMember={}", updateMember);
        log.info("member == updateMember {}", member == updateMember); // false
        log.info("member equals updateMember {}", member.equals(updateMember)); // true (EqualsAndHashCode)
        assertThat(updateMember).isEqualTo(new Member(member.getMemberId(), 5000)); // EqualsAndHashCode

        // delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);
    }

}
