package oneulmwohaji.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneulmwohaji.domain.review.entity.Review;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private double rating;
    private double productId;
    private String writerName;
    private String title;
    private String content;

    public static ReviewResponse of(Review review) {
        return ReviewResponse.builder()
                .rating(review.getRating())
                .productId(review.getProductId())
                .writerName(review.getWriterName())
                .title(review.getTitle())
                .content(review.getContent())
                .build();
    }
}
