package oneulmwohaji.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import oneulmwohaji.domain.member.dto.Response.MemberInfoResponse;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberQueryRepository;
import oneulmwohaji.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberQueryRepository memberQueryRepository;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, memberQueryRepository);
    }

    @DisplayName("유저가 존재하는 경우 이메일 조회 테스트")
    @Test
    public void findMemberByEmailTestIfMemberExists() throws Exception {
        Optional<Member> memberOptional = memberService.findMemberByEmail("kevin0928@naver.com");

        assertTrue(memberOptional.isPresent());
    }

    @DisplayName("유저가 존재하지 않는 경우 이메일 조회 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"hello@naver.com", "hey@naver.com", "wooahanstudy@naver.com"})
    public void findMemberByEmailTestIfMemberNotExists(String input) throws Exception {
        Optional<Member> memberOptional = memberService.findMemberByEmail(input);

        assertTrue(memberOptional.isEmpty());
    }

    @DisplayName("유저가 존재하는 경우 유저의 정보 조회 테스트")
    @Test
    public void getMemberInfoTestIfUserExist() throws Exception {
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(2L);
        assertEquals("user", memberInfoResponse.getUsername());
    }

    @DisplayName("유저가 아닌 관리자의 정보 조회 테스트")
    @ParameterizedTest
    @ValueSource(longs = {1L, 3L, 5L})
    public void getMemberInfoTestIfAdmin(Long input) throws Exception {
        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberInfo(input));
    }

    @DisplayName("유저가 존재하지 않는 경우 정보 조회 테스트")
    @ParameterizedTest
    @ValueSource(longs = {10L, 11L, 12L, 13L, 14L})
    public void getMemberInfoTestIfUserNotExist(Long input) throws Exception {
        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberInfo(input));
    }

    @DisplayName("유저가 존재하는 경우 유저의 이름 변경 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"2024년", "파이팅", "잘 할수 있다."})
    public void modifyMemberNicknameTestIfUserExist(String input) throws Exception {
        Optional<Member> member = memberQueryRepository.findMemberById(2L);
        MemberInfoResponse info = memberService.modifyMemberNickname(member.get(), input);
        assertEquals(input, info.getUsername());
    }
}