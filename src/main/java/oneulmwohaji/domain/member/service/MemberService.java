package oneulmwohaji.domain.member.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.dto.Response.MemberInfoResponse;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberQueryRepository;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public Member saveMember(Member member) {
        memberRepository.save(member);
        return member;
    }

    public MemberInfoResponse getMemberInfo(Long id) {
        Member member = memberQueryRepository.findMemberById(id).orElseThrow(() -> new MemberNotFoundException());
        return MemberInfoResponse.of(member);
    }

    public MemberInfoResponse modifyMemberNickname(Member member, String username) {
        Member updatedMember = Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .username(username)
                .oauthId(member.getOauthId())
                .oAuthProvider(member.getOAuthProvider())
                .isBan(member.isBan())
                .accountType(member.getAccountType())
                .build();

        Member savedMember = memberRepository.save(updatedMember);

        return MemberInfoResponse.of(savedMember);
    }
}
