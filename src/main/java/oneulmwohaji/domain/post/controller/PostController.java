package oneulmwohaji.domain.post.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoDto;
import oneulmwohaji.domain.post.dto.response.PostResponseDto;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.service.PostService;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/get/restaurants")
    public ResponseEntity<?> findRestaurantsByGeometry(@RequestParam("longitude") double longitude,
                                                       @RequestParam("latitude") double latitude,
                                                       @RequestParam("range") double range) throws ParseException {

        UserGeometryInfoDto userGeometryInfoDto = getUserGeometryInfoDtoFromParam(longitude, latitude, range);

        List<PostResponseDto> posts = postService.getPostsByUserGeometry(userGeometryInfoDto);
        return ResponseEntity.ok(posts);
    }

    private UserGeometryInfoDto getUserGeometryInfoDtoFromParam(double x, double y, double range) {
        return UserGeometryInfoDto.builder()
                .x(x)
                .y(y)
                .range(range)
                .build();
    }
}
