package oneulmwohaji.domain.member.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public Member saveMember(Member member) {
        memberRepository.save(member);
        return member;
    }
}
