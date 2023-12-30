package oneulmwohaji.domain.post.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.domain.member.exception.MemberExistException;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoDto;
import oneulmwohaji.domain.post.dto.response.PostResponseDto;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.exception.RestaurantNotFoundException;
import oneulmwohaji.domain.post.repository.PostRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public List<PostResponseDto> getPostsByUserGeometry(UserGeometryInfoDto userGeometryInfoDto) throws ParseException {
        String userPoint = userGeometryInfoDto.toWKT();
        List<Post> posts = postRepository.findByPointWithinRangeOrderByDistance(userPoint,
                userGeometryInfoDto.getRange());
        if (posts.size() == 0) {
            return Collections.emptyList();
        } else {
            return getPostResponseDto(posts);
        }
    }

    private List<PostResponseDto> getPostResponseDto(List<Post> posts) {
        return posts.stream()
                .map(PostResponseDto::getPostResponseDto)
                .collect(Collectors.toList());
    }
}
