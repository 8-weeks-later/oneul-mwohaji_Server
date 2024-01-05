package oneulmwohaji.domain.review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneulmwohaji.domain.post.entity.Post;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double rating;
    private Long productId;
    private String writerName;
    private String title;
    @Column(length = 500)
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
