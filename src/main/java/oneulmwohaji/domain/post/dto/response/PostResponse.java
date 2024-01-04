package oneulmwohaji.domain.post.dto.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import oneulmwohaji.domain.post.entity.Post;
import org.locationtech.jts.geom.Point;

@Getter
@AllArgsConstructor
@Builder
public class PostResponse {
    private long id;
    private Map<String, Double> location;
    private String restaurantName;
    private List<String> categories;
    private List<String> hashTags;
    private List<String> memberNames;
    private int scrapCount;

    public static PostResponse getPostResponseDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .location(getLocation(getX(post.getPoint()), getY(post.getPoint())))
                .restaurantName(post.getRestaurantName())
                .categories(post.getCategories())
                .hashTags(post.getHashTags())
                .memberNames(post.getMemberNames())
                .scrapCount(post.getScrapCount())
                .build();
    }

    private static Map<String, Double> getLocation(double x, double y) {
        Map<String, Double> pointMap = new HashMap<>();
        pointMap.put("x", x);
        pointMap.put("y", y);
        return pointMap;
    }

    private static double getX(Point point) {
        return point.getX();
    }

    private static double getY(Point point) {
        return point.getY();
    }
}
