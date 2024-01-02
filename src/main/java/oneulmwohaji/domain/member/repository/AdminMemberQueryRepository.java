package oneulmwohaji.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.admin.dto.request.AdminRequest;
import oneulmwohaji.domain.member.entity.AccountType;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.entity.QMember;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminMemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Member> findAdminById(AdminRequest adminRequest) {
        Member member = jpaQueryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.email.eq(adminRequest.getEmail())
                        .and(QMember.member.oauthId.eq(adminRequest.getOauthId()))
                        .and(QMember.member.accountType.eq(AccountType.ROLE_ADMIN)))
                .fetchOne();
        return Optional.ofNullable(member);
    }
}
