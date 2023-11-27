package oneulmwohaji.domain.post.repository;

import oneulmwohaji.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
