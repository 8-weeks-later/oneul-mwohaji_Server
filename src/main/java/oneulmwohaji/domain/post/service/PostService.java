package oneulmwohaji.domain.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.exception.MemberExistException;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoDto;
import oneulmwohaji.domain.post.dto.response.PostResponseDto;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.exception.RestaurantNotFoundException;
import oneulmwohaji.domain.post.repository.PostRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<PostResponseDto> getPostsByUserGeometry(UserGeometryInfoDto userGeometryInfoDto) throws ParseException {
        String userPoint = userGeometryInfoDto.toWKT();
        List<Post> posts = postRepository.findByPointWithinRangeOrderByDistance(userPoint, userGeometryInfoDto.getRange())
                .orElseThrow(() -> new RestaurantNotFoundException());


        return getPostResponseDto(posts);
    }

    private List<PostResponseDto> getPostResponseDto(List<Post> posts) {
        return posts.stream()
                .map(PostResponseDto::getPostResponseDto)
                .collect(Collectors.toList());
    }
}
