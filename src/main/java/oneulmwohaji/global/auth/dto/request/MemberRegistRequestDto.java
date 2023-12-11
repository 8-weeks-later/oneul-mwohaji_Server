package oneulmwohaji.global.auth.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oneulmwohaji.domain.member.entity.AccountType;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.global.auth.exception.UnknownOAuthPlatformException;
import oneulmwohaji.global.oauth.entity.OAuthProvider;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistRequestDto {
    @NotBlank
    private String oauthId;
    @NotBlank
    private String oauthPlatform;
    @NotBlank
    private String username;
    @NotBlank
    private String email;

    public Member of() {
        return Member.builder()
                .oauthId(oauthId)
                .oAuthProvider(getOAuthProviderFromString(oauthPlatform))
                .username(username)
                .email(email)
                .accountType(AccountType.ROLE_USER)
                .build();
    }

    private OAuthProvider getOAuthProviderFromString(String oauthPlatform) {
        switch (oauthPlatform.toLowerCase()) {
            case "kakao":
                return OAuthProvider.KAKAO;
            case "google":
                return OAuthProvider.GOOGLE;
            default:
                throw new UnknownOAuthPlatformException();
        }
    }

}
