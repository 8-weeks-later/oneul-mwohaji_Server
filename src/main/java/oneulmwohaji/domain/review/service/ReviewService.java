package oneulmwohaji.domain.review.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.exception.RestaurantNotFoundException;
import oneulmwohaji.domain.post.repository.PostRepository;
import oneulmwohaji.domain.review.dto.request.ReviewRegistRequest;
import oneulmwohaji.domain.review.dto.response.ReviewResponse;
import oneulmwohaji.domain.review.entity.Review;
import oneulmwohaji.domain.review.repository.ReviewQueryRepository;
import oneulmwohaji.domain.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final PostRepository postRepository;

    public void registReview(String username, ReviewRegistRequest reviewRegistRequest) {
        Post post = postRepository.findById(reviewRegistRequest.getProductId())
                .orElseThrow(() -> new RestaurantNotFoundException());
        post.calculateReviewScore(reviewRegistRequest.getRating());
        Review review = reviewRegistRequest.toEntity(username, post);
        reviewRepository.save(review);
    }

    public Page<ReviewResponse> getReviewListFromPost(int page, int size, Long postId) {
        Pageable pageable = PageRequest.of(page, size);
        Post post = postRepository.findById(postId).orElseThrow(() -> new RestaurantNotFoundException());

        Page<Review> reviews = reviewQueryRepository.findReviewByIdAndPage(pageable, post);
        return getReviewResponseListFromReviewList(reviews);
    }

    private Page<ReviewResponse> getReviewResponseListFromReviewList(Page<Review> reviews) {
        List<ReviewResponse> reviewResponses = reviews.map(ReviewResponse::of).getContent();
        return new PageImpl<>(reviewResponses, reviews.getPageable(), reviews.getTotalElements());
    }

}
