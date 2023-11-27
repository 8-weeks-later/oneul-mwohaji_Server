package oneulmwohaji.domain.admin.service;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.admin.dto.request.PostRequestDto;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    public void modifyUserBan(Long id) {
        Member member = memberRepository.findMemberById(id).orElseThrow(() -> new MemberNotFoundException());
        member.modifyIsBan();
        memberRepository.save(member);
    }

    public void addPost(PostRequestDto postRequestDto) {
        Post post = postRequestDto.of();
        postRepository.save(post);
    }
}
