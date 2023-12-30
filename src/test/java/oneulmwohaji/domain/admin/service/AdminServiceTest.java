package oneulmwohaji.domain.admin.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import oneulmwohaji.domain.admin.dto.request.AdminRequestDto;
import oneulmwohaji.domain.admin.dto.request.PostRequestDto;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.domain.member.repository.AdminMemberQueryRepository;
import oneulmwohaji.domain.post.repository.PostRepository;
import oneulmwohaji.global.jwt.service.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AdminMemberQueryRepository adminMemberQueryRepositoryimpl;
    @Mock
    private PostRepository postRepository;
    @Mock
    private JwtProvider jwtProvider;
    private AdminService adminService;
    private PostRequestDto postRequestDto;
    private AdminRequestDto adminRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminService = new AdminService(memberRepository, adminMemberQueryRepositoryimpl, postRepository, jwtProvider);
    }

    @DisplayName("유저가 존재하는 경우 유저 밴 테스트")
    @Test
    public void userBanModifyTestIfUserExists() throws Exception {
        //given

        //when
        adminService.modifyUserBan(1L);

        //then
        Optional<Member> memberOptional = memberRepository.findById(1L);
        assertTrue(memberOptional.isPresent());
        assertTrue(memberOptional.get().isBan());
    }

    @DisplayName("유저가 존재하지 않는 경우 밴 테스트")
    @Test
    public void userBanModifyTestIfUserNotExists() throws Exception {
        assertThrows(MemberNotFoundException.class, () -> adminService.modifyUserBan(5L));
    }

    @DisplayName("유저가 이미 밴 되어있는 경우 유저 밴 테스트")
    @Test
    public void userBanModifyTestIfUserAlreadyBanned() throws Exception {
        Optional<Member> memberOptional = memberRepository.findById(2L);
        adminService.modifyUserBan(2L);

        assertTrue(memberOptional.isPresent());
        assertFalse(memberOptional.get().isBan());
    }

    /*
    레스토랑 정보는 post 디렉토리에 있어야 하지만, Controller에서 admin만 정보를 게시할 수 있게 설정해놔서 admin 디렉토리에 있음
     */
    @DisplayName("레스토랑 정보 저장 테스트")
    @Test
    public void addPostTest() throws Exception {
        //given
        postRequestDto = new PostRequestDto("홍콩반점", "중식", "중식, 맛집, 경기도", "hong", 12.3, 20.1);
        //when
        adminService.addPost(postRequestDto);
        //then
        postRepository.findById(1L);
    }

    @DisplayName("관리자 로그인 테스트")
    @Test
    public void adminLoginTest() throws Exception {
        //given
        adminRequestDto = new AdminRequestDto("kevin0928@naver.com", "123123123");
        //when
        Member member = adminService.login(adminRequestDto);
        //then
        assertEquals("kevin0928@naver.com", member.getEmail());
        assertEquals("123123123", member.getOauthId());
    }

    @DisplayName("관리자가 아닌 일반 유저가 로그인을 시도하는 경우 테스트")
    @Test
    public void adminLoginTestIfNonAdminUserAttemptsLogin() throws Exception {
        adminRequestDto = new AdminRequestDto("user@naver.com", "112233");

        assertThrows(MemberNotFoundException.class, () -> adminService.login(adminRequestDto));
    }

    @DisplayName("회원가입 하지 않은 관리자가 로그인을 시도하는 경우 테스트")
    @Test
    public void nonRegisteredAdminLoginTest() throws Exception {
        adminRequestDto = new AdminRequestDto("hello.com", "112233");

        assertThrows(MemberNotFoundException.class, () -> adminService.login(adminRequestDto));
    }
}