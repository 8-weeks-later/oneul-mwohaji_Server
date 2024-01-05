package oneulmwohaji.domain.post.entity;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneulmwohaji.domain.review.entity.Review;
import org.locationtech.jts.geom.Point;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "POINT SRID 4326")
    private Point point;
    @Column(nullable = false)
    private String restaurantName;
    @ElementCollection
    @CollectionTable(name = "post_categories", joinColumns = @JoinColumn(name = "post_id"))
    @Column
    private List<String> categories;
    @ElementCollection
    @CollectionTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> hashTags;
    @ElementCollection
    @CollectionTable(name = "post_members", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> memberNames;
    private int scrapCount;
    private double visitorReviewScore;
    private int visitorReviewCount;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Review> reviews;

    public void addScrapCount() {
        this.scrapCount++;
    }
    public void calculateReviewScore(double rating) {
        double newScore = ((visitorReviewScore * visitorReviewCount) + rating) / (visitorReviewCount + 1);
        this.visitorReviewScore = Math.round(newScore * 100.0) / 100.0;
        this.visitorReviewCount++;
    }

}
