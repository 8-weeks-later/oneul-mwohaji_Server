package oneulmwohaji.domain.review.repository;

import oneulmwohaji.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
