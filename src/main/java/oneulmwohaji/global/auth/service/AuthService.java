package oneulmwohaji.global.auth.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.global.auth.dto.request.MemberRegistRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    public Optional<Member> findMemberByOAuthId(String oauthId) {
        return memberRepository.findMemberByOauthId(oauthId);
    }

    public void registMember(MemberRegistRequestDto memberRegistRequestDto) {
        Member member = memberRegistRequestDto.of();
        memberRepository.save(member);
    }
}
