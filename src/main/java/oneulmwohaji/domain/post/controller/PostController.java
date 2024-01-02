package oneulmwohaji.domain.post.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoRequest;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.service.PostService;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

        UserGeometryInfoRequest userGeometryInfoRequest = getUserGeometryInfoDtoFromParam(longitude, latitude, range);

        List<PostResponse> posts = postService.getPostsByUserGeometry(userGeometryInfoRequest);
        return ResponseEntity.ok(posts);
    }

    private UserGeometryInfoRequest getUserGeometryInfoDtoFromParam(double x, double y, double range) {
        return UserGeometryInfoRequest.builder()
                .x(x)
                .y(y)
                .range(range)
                .build();
    }
}
