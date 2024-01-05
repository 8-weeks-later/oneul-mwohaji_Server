package oneulmwohaji.domain.admin.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.admin.dto.request.AdminRequest;
import oneulmwohaji.domain.admin.dto.request.PostRequest;
import oneulmwohaji.domain.admin.dto.response.AdminUpdateResponse;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.domain.admin.repository.AdminMemberQueryRepository;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.exception.RestaurantNotFoundException;
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
    public AdminUpdateResponse modifyUserBan(Long id) {
        Member member = memberRepository.findMemberById(id).orElseThrow(() -> new MemberNotFoundException());
        member.modifyIsBan();
        memberRepository.save(member);
        return AdminUpdateResponse.of(member);
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

    public PostResponse modifyPost(Long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException());
        Post modifiedPost = postRequest.modifyOf(id, post);
        postRepository.save(modifiedPost);
        return PostResponse.of(modifiedPost);
    }

    public String createAccessToken(String oauthId) {
        return jwtProvider.createAccessToken(oauthId);
    }
    public String createRefreshToken(String oauthId) {
        return jwtProvider.createRefreshToken(oauthId);
    }
}
