package oneulmwohaji.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import oneulmwohaji.domain.post.entity.Post;
import org.locationtech.jts.geom.Point;

@Getter
@AllArgsConstructor
@Builder
public class PostResponseDto {
    // FIXME: 2023-12-12
    private long id;
    private double x;
    private double y;
    private String restaurantName;
    private int scrapCount;

    public static PostResponseDto getPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .x(getX(post.getPoint()))
                .y(getY(post.getPoint()))
                .restaurantName(post.getRestaurantName())
                .scrapCount(post.getScrapCount())
                .build();

    }

    private static double getX(Point point) {
        return point.getX();
    }

    private static double getY(Point point) {
        return point.getY();
    }
}
