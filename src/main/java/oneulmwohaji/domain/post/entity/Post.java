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
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
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

    /*
    TODO : reivew 클래스 생성 한 뒤, 1:N 매핑
    */
    @Column
    private String review;
}
