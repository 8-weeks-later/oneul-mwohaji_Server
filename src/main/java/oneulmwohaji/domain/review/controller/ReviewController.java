package oneulmwohaji.domain.review.controller;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.review.dto.request.ReviewRegistRequest;
import oneulmwohaji.domain.review.dto.response.ReviewResponse;
import oneulmwohaji.domain.review.entity.Review;
import oneulmwohaji.domain.review.service.ReviewService;
import oneulmwohaji.global.jwt.MemberPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review/regist")
    public ResponseEntity<Void> registReview(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                          @RequestBody ReviewRegistRequest reviewRegistRequest) {
        String username = memberPrincipal.getMember().getUsername();
        reviewService.registReview(username, reviewRegistRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/review")
    public ResponseEntity<Page<ReviewResponse>> getReviewFromPost(@RequestParam("postId") Long postId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        Page<ReviewResponse> reviewResponses = reviewService.getReviewListFromPost(page, size, postId);
        return ResponseEntity.ok(reviewResponses);
    }
}
