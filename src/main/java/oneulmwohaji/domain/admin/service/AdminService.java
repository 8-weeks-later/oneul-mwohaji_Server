package oneulmwohaji.domain.admin.service;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.admin.dto.request.AdminRequestDto;
import oneulmwohaji.domain.admin.dto.request.PostRequestDto;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.domain.member.repository.MemberRepositoryImpl;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.repository.PostRepository;
import oneulmwohaji.global.jwt.service.JwtProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final MemberRepositoryImpl memberQueryReporitory;
    private final PostRepository postRepository;
    private final JwtProvider jwtProvider;
    public void modifyUserBan(Long id) {
        Member member = memberRepository.findMemberById(id).orElseThrow(() -> new MemberNotFoundException());
        member.modifyIsBan();
        memberRepository.save(member);
    }

    public void addPost(PostRequestDto postRequestDto) {
        Post post = postRequestDto.of();
        postRepository.save(post);
    }

    public Member login(AdminRequestDto adminRequestDto) {
        Member member = memberQueryReporitory.findAdminById(adminRequestDto)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }

    public String createAccessToken(String oauthId) {
        return jwtProvider.createAccessToken(oauthId);
    }
    public String createRefreshToken(String oauthId) {
        return jwtProvider.createRefreshToken(oauthId);
    }
}
