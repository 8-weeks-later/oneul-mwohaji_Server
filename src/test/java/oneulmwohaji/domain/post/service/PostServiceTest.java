package oneulmwohaji.domain.post.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import oneulmwohaji.domain.post.dto.request.UserGeometryInfoRequest;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostRepository postRepository;
    private PostService postService;
    private UserGeometryInfoRequest userGeometryInfoRequest;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository);
    }

    @DisplayName("사용자의 위치를 기준으로 레스토랑 조회 테스트 : 조회 테스트")
    @Test
    public void getPostsByUserGeometry() throws Exception {
        //given
        userGeometryInfoRequest = new UserGeometryInfoRequest(37.5665, 26.9780, 10); // 10이면 111 * 10 -> 1.1km이다.
        //when
        List<PostResponse> postResponseDtos = postService.getPostsByUserGeometry(userGeometryInfoRequest);
        //then
        assertEquals(1, postResponseDtos.size());
    }

    @DisplayName("사용자의 위치를 기준으로 레스토랑 조회 테스트 : range 안에 레스토랑이 없는 경우 테스트")
    @Test
    public void getPostsByUserGeometryIfThereIsNoRestraurantInRange()throws Exception {
        //given
        userGeometryInfoRequest = new UserGeometryInfoRequest(47.5665, 26.9780, 10); // 10이면 111 * 10 -> 1.1km이다.
        //when
        List<PostResponse> postResponseDtos = postService.getPostsByUserGeometry(userGeometryInfoRequest);
        //then
        assertEquals(0, postResponseDtos.size());
    }
    @DisplayName("사용자의 위치를 기준으로 레스토랑 조회 테스트 : range 안에 1개의 레스토랑이 있는 경우 테스트")
    @Test
    public void getPostsByUserGeometryIfOneRestraurantInRange() throws Exception {
        //given
        userGeometryInfoRequest = new UserGeometryInfoRequest(37.5665, 26.9780, 30);
        //when
        List<PostResponse> postResponseDtos = postService.getPostsByUserGeometry(userGeometryInfoRequest);
        //then
        assertEquals(3, postResponseDtos.size());
    }

    @DisplayName("사용자의 위치를 기준으로 레스토랑 조회 테스트 : range 안에 여러 레스토랑이 있는 경우 테스트")
    @Test
    public void getPostsByUserGeometryIfMultipleRestaurantsInRange() throws Exception {
        //given
        userGeometryInfoRequest = new UserGeometryInfoRequest(37.5785, 26.9780, 200000); // 해당 좌표 주변에 여러 개의 레스토랑이 있다는 가정
        //when
        List<PostResponse> postResponseDtos = postService.getPostsByUserGeometry(userGeometryInfoRequest);
        //then
        assertTrue(postResponseDtos.size() > 1);
    }

    @DisplayName("사용자의 위치를 기준으로 레스토랑 조회 테스트 : 잘못된 좌표로 인한 예외 테스트")
    @Test
    public void getPostsByUserGeometryIfInvalidCoordinates() {
        //given
        userGeometryInfoRequest = new UserGeometryInfoRequest(200.0, 200.0, 100); // 유효하지 않은 좌표를 전달하는 가정
        //when & then
        assertThrows(Exception.class, () -> postService.getPostsByUserGeometry(userGeometryInfoRequest));
    }
}