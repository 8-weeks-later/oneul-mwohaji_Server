package oneulmwohaji.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @DisplayName("유저가 존재하는 경우 이메일 조회 테스트")
    @Test
    public void findMemberByEmailTestIfMemberExists() throws Exception {
        Optional<Member> memberOptional = memberService.findMemberByEmail("kevin0928@naver.com");

        assertTrue(memberOptional.isPresent());
    }

    @DisplayName("유저가 존재하지 않는 경우 이메일 조회 테스트")
    @Test
    public void findMemberByEmailTestIfMemberNotExists()throws Exception {
        Optional<Member> memberOptional = memberService.findMemberByEmail("nothing@naver.com");

        assertTrue(memberOptional.isEmpty());
    }
}