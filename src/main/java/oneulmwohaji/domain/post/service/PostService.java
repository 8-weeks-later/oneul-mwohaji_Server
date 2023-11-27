package oneulmwohaji.domain.post.service;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
}
