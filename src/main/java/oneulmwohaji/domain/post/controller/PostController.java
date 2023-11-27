package oneulmwohaji.domain.post.controller;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.post.service.PostService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
}
