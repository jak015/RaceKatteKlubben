package org.example.racekatteklubben.infrastructure;

import org.example.racekatteklubben.models.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(MemberRepository.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void createMember_ShouldReturn_id() {
        Member member = new Member();
        member.setId(1);
        member.setMemberName("Test");
        member.setEmail("test@test.com");
        member.setPassword("Test1234");

        int generatedId = memberRepository.createMember(member);

        assertEquals(member.getId(), generatedId);
    }
}