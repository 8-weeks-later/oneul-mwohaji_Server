package oneulmwohaji.global.auth.oauth.service;

import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.exception.MemberExistException;
import oneulmwohaji.domain.member.service.MemberService;
import oneulmwohaji.global.auth.oauth.entity.OAuthAttributes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        try {
            // OAuth2User 정보를 이용하여 Member 객체 생성
            Member member = processOAuthUser(userRequest, oAuth2User);
            return member;
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    // OAuth2User 정보를 이용하여 Member 객체 생성하는 메서드
    private Member processOAuthUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Map<String, Object> originAttributes = oAuth2User.getAttributes();

        // 사용자가 OAuth2로부터 인증된 공급자(Provider)의 이름을 가져옴
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        // OAuth2User의 속성과 공급자 이름을 이용하여 OAuthAttributes 객체 생성
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);

        // 이메일을 통해 이미 가입된 회원인지 확인
        Optional<Member> memberOptional = memberService.findMemberByEmail(attributes.getEmail());
        if (memberOptional.isPresent()) {
            throw new MemberExistException();
        }
        // OAuthAttributes를 이용하여 Member 객체 생성
        return registMember(attributes);
    }

    // Member 객체를 저장하는 메서드
    private Member registMember(OAuthAttributes attributes) {
        return memberService.saveMember(attributes.toEntity());
    }
}
