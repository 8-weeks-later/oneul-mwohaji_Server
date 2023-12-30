package oneulmwohaji.domain.post.repository;

import java.util.Optional;
import oneulmwohaji.domain.post.entity.Post;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p " +
            "FROM Post p " +
            "WHERE ST_Distance_Sphere(p.point, ST_GeomFromText(:userPoint, 4326)) <= :range " +
            "ORDER BY ST_Distance_Sphere(p.point, ST_GeomFromText(:userPoint, 4326))")
    List<Post> findByPointWithinRangeOrderByDistance(@Param("userPoint") String userPoint, @Param("range") double range);
}
