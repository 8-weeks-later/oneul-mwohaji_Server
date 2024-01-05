package oneulmwohaji.domain.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.review.entity.Review;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRegistRequest {
    private Long productId;
    private double rating;
    private String title;
    private String content;

    public Review toEntity(String writername, Post post) {
        return Review.builder()
                .rating(rating)
                .productId(productId)
                .writerName(writername)
                .title(title)
                .content(content)
                .post(post)
                .build();
    }

}
