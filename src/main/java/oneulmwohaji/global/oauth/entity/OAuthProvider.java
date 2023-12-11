package oneulmwohaji.global.oauth.entity;

public enum OAuthProvider {
    NAVER("NAVER"),
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");
    private final String provider;

    OAuthProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
