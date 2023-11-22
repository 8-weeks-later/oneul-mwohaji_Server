package oneulmwohaji.global.auth.oauth.entity;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import oneulmwohaji.domain.member.entity.Member;

@Getter
@ToString
public class OAuthAttributes {
    private Map<String, Object> attributes;     // OAuth2 반환하는 유저 정보
    private String oauthId;
    private String nameAttributesKey;
    private String name;
    private String email;
    private String oAuthProvider;
    private String gender;
    private String ageRange;
    private String profileImageUrl;

    @Builder
    public OAuthAttributes(String oauthId,Map<String, Object> attributes, String nameAttributesKey,
                           String name, String email,String oAuthProvider, String gender, String ageRange, String profileImageUrl) {
        this.oauthId = oauthId;
//        this.attributes = attributes;
//        this.nameAttributesKey = nameAttributesKey;
        this.name = name;
        this.email = email;
        this.oAuthProvider = oAuthProvider;
//        this.gender = gender;
//        this.ageRange = ageRange;
//        this.profileImageUrl = profileImageUrl;
    }

    public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
        if ("kakao".equals(socialName)) {
            return ofKakao("id", attributes);
        }
        else if ("google".equals(socialName)) {
            return ofGoogle("sub", attributes);
        }
        else if ("naver".equals(socialName)) {
            return ofNaver("id", attributes);
        }
        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .oauthId(String.valueOf(attributes.get("sub")))
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .oAuthProvider(OAuthProvider.Google.getProvider())
                .attributes(attributes)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        String oauthId = String.valueOf(attributes.get("id"));

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .oauthId(oauthId)
                .name(String.valueOf(kakaoProfile.get("nickname")))
                .email(String.valueOf(kakaoAccount.get("email")))
                .oAuthProvider(OAuthProvider.Kakao.getProvider())
                .nameAttributesKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .oauthId(String.valueOf(response.get("id")))
                .name(String.valueOf(response.get("nickname")))
                .email(String.valueOf(response.get("email")))
                .oAuthProvider(OAuthProvider.Naver.getProvider())
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
                .build();
    }
}