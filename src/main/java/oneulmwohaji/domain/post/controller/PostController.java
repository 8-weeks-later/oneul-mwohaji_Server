package oneulmwohaji.domain.post.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoRequest;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.service.PostService;
import oneulmwohaji.global.jwt.MemberPrincipal;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/get/restaurants")
    public ResponseEntity<?> findRestaurantsByGeometry(@RequestParam("longitude") double longitude,
                                                       @RequestParam("latitude") double latitude,
                                                       @RequestParam("range") double range) throws ParseException {

        UserGeometryInfoRequest userGeometryInfoRequest = getUserGeometryInfoDtoFromParam(longitude, latitude, range);

        List<PostResponse> posts = postService.getPostsByUserGeometry(userGeometryInfoRequest);
        return ResponseEntity.ok(posts);
    }

    private UserGeometryInfoRequest getUserGeometryInfoDtoFromParam(double x, double y, double range) {
        return UserGeometryInfoRequest.builder()
                .x(x)
                .y(y)
                .range(range)
                .build();
    }

    @Transactional
    @PostMapping("/user/scrap")
    public ResponseEntity<Void> scrapPost(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                          @RequestParam("id") Long id) {
        Long memberId = memberPrincipal.getMember().getId();
        Member member = postService.getMemberAndScrapList(memberId);
        postService.scrapPost(member, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/scrap/list")
    public ResponseEntity<Page<PostResponse>> getScrapPostList(
            @AuthenticationPrincipal MemberPrincipal memberPrincipal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Long memberId = memberPrincipal.getMember().getId();
        Page<PostResponse> postsPage = postService.getScrapPostPage(memberId, page, size);
        return ResponseEntity.ok(postsPage);
    }
}
