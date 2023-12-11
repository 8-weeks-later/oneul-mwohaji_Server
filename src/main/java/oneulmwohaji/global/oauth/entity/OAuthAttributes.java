package oneulmwohaji.global.oauth.entity;

import static oneulmwohaji.global.oauth.ConstantValue.*;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import oneulmwohaji.domain.member.entity.AccountType;
import oneulmwohaji.domain.member.entity.Member;

@Getter
@ToString
public class OAuthAttributes {
    private Map<String, Object> attributes;     // OAuth2 반환하는 유저 정보
    private String oauthId;
    private String nameAttributesKey;
    private String name;
    private String email;
    private OAuthProvider oAuthProvider;

    @Builder
    public OAuthAttributes(String oauthId, Map<String, Object> attributes, String nameAttributesKey,
                           String name, String email, OAuthProvider oAuthProvider) {
        this.oauthId = oauthId;
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.name = name;
        this.email = email;
        this.oAuthProvider = oAuthProvider;
    }

    public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
        if ("kakao".equals(socialName)) {
            return ofKakao("id", attributes);
        } else if ("google".equals(socialName)) {
            return ofGoogle("sub", attributes);
        } else if ("naver".equals(socialName)) {
            return ofNaver("id", attributes);
        }
        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .oauthId(String.valueOf(attributes.get(GOOGLE_OAUTH_ID)))
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get(EMAIL)))
                .oAuthProvider(OAuthProvider.GOOGLE)
                .attributes(attributes)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        String oauthId = String.valueOf(attributes.get(KAKAO_NAVER_OAUTH_ID));

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .oauthId(oauthId)
                .name(String.valueOf(kakaoProfile.get(NICK_NAME)))
                .email(String.valueOf(kakaoAccount.get(EMAIL)))
                .oAuthProvider(OAuthProvider.KAKAO)
                .nameAttributesKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .oauthId(String.valueOf(response.get(KAKAO_NAVER_OAUTH_ID)))
                .name(String.valueOf(response.get(NICK_NAME)))
                .email(String.valueOf(response.get(EMAIL)))
                .oAuthProvider(OAuthProvider.NAVER)
                .attributes(response)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .username(name)
                .email(email)
                .oauthId(oauthId)
                .oAuthProvider(oAuthProvider)
                .isBan(false)
                .accountType(AccountType.ROLE_USER)
                .build();
    }
}