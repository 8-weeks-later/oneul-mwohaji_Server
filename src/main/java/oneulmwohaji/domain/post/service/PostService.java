package oneulmwohaji.domain.post.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoRequest;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.repository.PostRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public List<PostResponse> getPostsByUserGeometry(UserGeometryInfoRequest userGeometryInfoRequest) throws ParseException {
        String userPoint = userGeometryInfoRequest.toWKT();
        List<Post> posts = postRepository.findByPointWithinRangeOrderByDistance(userPoint,
                userGeometryInfoRequest.getRange());
        if (posts.size() == 0) {
            return Collections.emptyList();
        } else {
            return getPostResponseDto(posts);
        }
    }

    private List<PostResponse> getPostResponseDto(List<Post> posts) {
        return posts.stream()
                .map(PostResponse::getPostResponseDto)
                .collect(Collectors.toList());
    }
}
