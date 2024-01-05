package oneulmwohaji.domain.post.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.member.repository.MemberRepository;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoRequest;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.exception.RestaurantNotFoundException;
import oneulmwohaji.domain.post.repository.PostRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<PostResponse> getPostsByUserGeometry(UserGeometryInfoRequest userGeometryInfoRequest)
            throws ParseException {
        String userPoint = userGeometryInfoRequest.toWKT();
        List<Post> posts = postRepository.findByPointWithinRangeOrderByDistance(userPoint,
                userGeometryInfoRequest.getRange());
        if (posts.size() == 0) {
            throw new RestaurantNotFoundException();
        } else {
            return getPostResponseDto(posts);
        }
    }

    private List<PostResponse> getPostResponseDto(List<Post> posts) {
        return posts.stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void scrapPost(Member member, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException());
        member.addScrapPost(post);
        memberRepository.save(member);
    }


    public Member getMemberAndScrapList(Long memberId) {
        Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> new MemberNotFoundException());
        return member;
    }

    public Page<PostResponse> getScrapPostPage(Long memberId, int page, int size) {
        Member member = getMemberAndScrapList(memberId);
        List<Post> memberPosts = member.getPosts();

        int start = page * size;
        int end = Math.min(start + size, memberPosts.size());

        List<PostResponse> postResponses = memberPosts.subList(start, end)
                .stream()
                .map(PostResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(postResponses, PageRequest.of(page, size), memberPosts.size());
    }

}
