package oneulmwohaji.domain.admin.service;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    public void modifyUserBan(Long id) {
        Member member = memberRepository.findMemberById(id).orElseThrow(() -> new MemberNotFoundException());
        member.modifyIsBan();
        memberRepository.save(member);
    }
}
