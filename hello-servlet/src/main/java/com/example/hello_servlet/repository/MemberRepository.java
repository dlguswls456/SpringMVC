package com.example.hello_servlet.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.example.hello_servlet.model.Member;


public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0L);

    private static MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {
    }

    public void save(Member member) {
        setIdWithReflection(member);
        store.put(member.getId(), member);
    }

    private void setIdWithReflection(Member member) {
        try {
            Class<Member> memberClass = Member.class;
            Field id = memberClass.getDeclaredField("id");
            id.setAccessible(true);
            id.set(member, sequence.incrementAndGet());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Reflection Exception", e);
        }
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
