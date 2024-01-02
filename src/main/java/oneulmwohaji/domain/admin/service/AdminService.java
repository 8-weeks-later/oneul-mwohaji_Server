package oneulmwohaji.domain.admin.service;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.admin.dto.request.AdminRequest;
import oneulmwohaji.domain.admin.dto.request.PostRequest;
import oneulmwohaji.domain.admin.dto.response.MemberUpdateResponse;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.domain.member.repository.AdminMemberQueryRepository;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.repository.PostRepository;
import oneulmwohaji.global.jwt.service.JwtProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final AdminMemberQueryRepository memberQueryReporitory;
    private final PostRepository postRepository;
    private final JwtProvider jwtProvider;
    public MemberUpdateResponse modifyUserBan(Long id) {
        Member member = memberRepository.findMemberById(id).orElseThrow(() -> new MemberNotFoundException());
        member.modifyIsBan();
        memberRepository.save(member);
        return MemberUpdateResponse.of(member);
    }

    public void addPost(PostRequest postRequest) {
        Post post = postRequest.of();
        postRepository.save(post);
    }

    public Member login(AdminRequest adminRequest) {
        Member member = memberQueryReporitory.findAdminById(adminRequest)
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
