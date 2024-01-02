package oneulmwohaji.domain.member.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneulmwohaji.domain.member.entity.AccountType;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.global.oauth.entity.OAuthProvider;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberInfoResponse {
    private String email;
    private String username;
    private String oauthId;
    private OAuthProvider oAuthProvider;
    private boolean isBan;
    private AccountType accountType;

    public static MemberInfoResponse of(Member member) {
        return MemberInfoResponse.builder()
                .email(member.getEmail())
                .username(member.getUsername())
                .oauthId(member.getOauthId())
                .oAuthProvider(member.getOAuthProvider())
                .isBan(member.isBan())
                .accountType(member.getAccountType())
                .build();
    }
}
