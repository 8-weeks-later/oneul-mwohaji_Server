package oneulmwohaji.domain.admin.dto.request;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import oneulmwohaji.domain.post.entity.Post;
import org.springframework.data.geo.Point;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
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
        return new Point(this.x, this.y);
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
                .review(null)
                .restaurantName(restaurantName)
                .build();
    }
}
