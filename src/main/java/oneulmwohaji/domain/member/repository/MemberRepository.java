package oneulmwohaji.domain.member.repository;

import java.util.Optional;
import oneulmwohaji.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByOauthId(String oauthId);

    Optional<Member> findMemberById(Long id);
}
