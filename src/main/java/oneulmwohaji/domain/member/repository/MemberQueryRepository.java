package oneulmwohaji.domain.member.repository;

import static oneulmwohaji.domain.member.entity.QMember.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.entity.AccountType;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.entity.QMember;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.domain.post.entity.QPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Member> findMemberById(Long id) {
        Member member = jpaQueryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.id.eq(id)
                        .and(QMember.member.accountType.eq(AccountType.ROLE_USER)))
                .fetchOne();
        return Optional.ofNullable(member);
    }
}
