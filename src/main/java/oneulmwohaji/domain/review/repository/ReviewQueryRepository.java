package oneulmwohaji.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.review.entity.QReview;
import oneulmwohaji.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<Review> findReviewByIdAndPage(Pageable pageable, Post post) {
        List<Review> reviews = jpaQueryFactory
                .selectFrom(QReview.review)
                .where(QReview.review.post.eq(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(reviews, pageable, reviews.size());
    }
}
