package com.example.itemservice;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.example.springdb1.domain.Member;
import com.example.springdb1.domain.MemberRepositoryV0;


public class MemberRepositoryTest {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 1000);
        repository.save(member);
    }

}
