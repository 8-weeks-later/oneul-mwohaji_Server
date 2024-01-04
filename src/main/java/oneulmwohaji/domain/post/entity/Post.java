package oneulmwohaji.domain.post.entity;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @Column
    private int scrapCount;
    @Column
    private String review;

    public void addScrapCount() {
        this.scrapCount++;
    }
}
