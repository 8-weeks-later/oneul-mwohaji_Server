package oneulmwohaji.domain.admin.dto.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.review.entity.Review;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String restaurantName;
    @NotBlank
    private String category;
    @NotBlank
    private String hashTag;
    @NotBlank
    private String memberName;
    @NotNull
    private Double x;
    @NotNull
    private Double y;

    private Point toPoint() {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(x, y));
    }

    private List<String> toCategoryList() {
        return Arrays.stream(category.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private List<String> toHashTagList() {
        return Arrays.stream(hashTag.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private List<String> toMemberList() {
        return Arrays.stream(memberName.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public Post of() {
        return Post.builder()
                .point(toPoint())
                .hashTags(toCategoryList())
                .categories(toHashTagList())
                .memberNames(toMemberList())
                .scrapCount(0)
                .visitorReviewScore(0)
                .visitorReviewCount(0)
                .reviews(new ArrayList<>())
                .restaurantName(restaurantName)
                .build();
    }

    public Post modifyOf(Long id, Post post) {
        return Post.builder()
                .id(id)
                .point(toPoint())
                .hashTags(toCategoryList())
                .categories(toHashTagList())
                .memberNames(toMemberList())
                .scrapCount(post.getScrapCount())
                .visitorReviewCount(post.getVisitorReviewCount())
                .visitorReviewScore(post.getVisitorReviewScore())
                .reviews(post.getReviews())
                .restaurantName(restaurantName)
                .build();
    }
}
